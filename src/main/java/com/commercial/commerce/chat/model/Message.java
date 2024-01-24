package com.commercial.commerce.chat.model;

public class Message {

    private User sender;
    private String content;
    private String receiver_id;

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    @Override
    public String toString() {
        return "Message [sender=" + sender + ", content=" + content + ", receiver_id=" + receiver_id + "]";
    }

}
