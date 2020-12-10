package com.koral.webKoral.chat;


public class ChatMessage{

    private String form;
    private String message;

    public ChatMessage(String form, String message){
        this.form = form;
        this.message = message;

    }

    public String getForm() {
        return form;
    }

    public String getMessage() {
        return message;
    }

}