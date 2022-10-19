package com.example.vaadincourse1.todo;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("t")
public class TodoUI extends VerticalLayout {

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        var title = new H2("TodoList Application....");
        add(title);
    }
}
