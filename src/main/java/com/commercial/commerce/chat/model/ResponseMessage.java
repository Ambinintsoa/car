package com.commercial.commerce.chat.model;

public class ResponseMessage {

    private String content;

    private String sender_id;
    private String sender_name;
    private String picturePath;

    public ResponseMessage() {
    }

    public ResponseMessage(String content) {
        this.content = content;
    }

    public ResponseMessage(String content,String sender_id,String sender_name,String picturePath){
        this.content = content;
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.picturePath = picturePath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
