package com.koral.webKoral.chat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;

@CssImport("./chat.css")
public
class MessageList extends Div {

    public MessageList(){
        addClassName("message-list");
    }

    public void add(Component... components){
        super.add(components);

        components[components.length - 1].getElement().callJsFunction("scrollIntoView");
    }
}
