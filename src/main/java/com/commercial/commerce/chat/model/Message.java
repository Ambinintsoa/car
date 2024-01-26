package com.commercial.commerce.chat.model;

import java.util.Date;

@Document(collection = "message")
public class Message {

    @Id
    private String id;
    private String sender_id;
    private String sender_name;
    private String receiver_id;
    private String content;
    private Date date;
    private String picturePath;

    public Message(String id, String sender_id, String content, String receiver_id, Date date) {
        this.id = id;
        this.sender_id = sender_id;
        this.content = content;
        this.receiver_id = receiver_id;
        this.date = date;
    }

    public Message(String sender_id,String sender_name,String receiver_id,String content,Date date,String picturePath){
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.receiver_id = receiver_id;
        this.content = content;
        this.date = date;   
        this.picturePath = picturePath;
    }

    public String getcontent() {
        return content;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public void setcontent(String content) {
        this.content = content;
    }


    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPicturePath(){
        return picturePath;
    }
    public void setPicturePath(String picturePath){
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", sender_id='" + sender_id + '\'' +
                ", content='" + content + '\'' +
                ", receiver_id='" + receiver_id + '\'' +
                '}';
    }
}
