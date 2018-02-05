package com.example.noso.myapplication;

import java.util.Date;

/**
 * Created by Amira Tarek on 12/2/2017.
 */

public class ChatMessage {



    private String messagetext;
    private String messageuser;
    private Long messagetime;

    public ChatMessage(String messagetext, String messageuser) {
        this.messagetext = messagetext;
        this.messageuser = messageuser;
        messagetime = new Date().getTime();
    }

    public ChatMessage() {
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

    public Long getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(Long messagetime) {
        this.messagetime = messagetime;
    }
}
