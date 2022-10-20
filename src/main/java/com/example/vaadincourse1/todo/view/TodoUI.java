package com.example.vaadincourse1.todo.view;

import com.example.vaadincourse1.todo.model.Todo;
import com.example.vaadincourse1.todo.repo.InMemoryRepository;
import com.example.vaadincourse1.todo.service.Broadcaster;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


@Route("t/:name")
public class TodoUI extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {

    @Autowired
    InMemoryRepository inMemoryRepository;

    String author;

    Grid<Todo> view;

    Registration broadcasterRegistration;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        var title = new H2("TodoList Application: " + author.toUpperCase());
        add(title);

        var btnAdd = new Button("Add new item");
        btnAdd.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
        btnAdd.addClickListener(buttonClickEvent -> {
            var dialog = createAddDialogue();
            dialog.open();
        });

        var btnRemove = new Button("Remove selected items");
        btnRemove.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        btnRemove.addClickListener(buttonClickEvent -> {
            inMemoryRepository.getAllItems().removeAll(view.getSelectedItems());
//            refreshItems();
            Broadcaster.broadcast("Item(s) removed by " + author);
        });



        add(new HorizontalLayout(btnAdd, btnRemove));

        view = new Grid();
        view.setSelectionMode(Grid.SelectionMode.MULTI);
        view.addColumn(Todo::getTitle);
        view.addColumn(Todo::getBody);
        view.addColumn(Todo::getAuthor);
        view.addColumn(Todo::getCreatedAt);
        refreshItems();
        add(view);


        var btnSelectAll = new Button("select all");
        btnSelectAll.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        btnSelectAll.addClickListener(buttonClickEvent -> {
            view.asMultiSelect().select(inMemoryRepository.getAllItems());
        });
        var btnDeselectAll = new Button("deselect all");
        btnDeselectAll.setIcon(VaadinIcon.MINUS_CIRCLE.create());
        btnDeselectAll.addClickListener(buttonClickEvent -> {
            view.asMultiSelect().deselectAll();
        });

        add(new HorizontalLayout(btnSelectAll, btnDeselectAll));

        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(message -> {
            ui.access(() -> {
                refreshItems();
                Notification.show(message);
            });
        });
    }

    private Dialog createAddDialogue() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New ToDo");

        VerticalLayout dialogLayout = new VerticalLayout();
        var title = new TextField("Title");

        dialogLayout.add(title);
        dialog.add(dialogLayout);

        var btnCancel = new Button("Cancel");
        btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        btnCancel.addClickListener(buttonClickEvent -> {
            dialog.close();
        });

        var btnSave = new Button("Save");
        btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
        btnSave.addClickListener(buttonClickEvent -> {
            inMemoryRepository.addToItems(Todo.builder()
                    .title(title.getValue())
                    .body("Body from " + title.getValue())
                    .author(author)
                    .createdAt(LocalDateTime.now()).build());

            dialog.close();
//            refreshItems();
            Broadcaster.broadcast("Item add by " + author);

        });



        dialog.getFooter().add(btnCancel, btnSave);

        return dialog;
    }

    private void refreshItems() {
        view.setItems(inMemoryRepository.getAllItems());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        author = beforeEnterEvent.getRouteParameters().get("name").get();
    }

    @Override
    public String getPageTitle() {
        return "Todo "+author.toUpperCase();
    }


    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        broadcasterRegistration.remove();
        broadcasterRegistration = null;

    }
}
