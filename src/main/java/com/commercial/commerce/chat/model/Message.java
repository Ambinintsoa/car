package com.commercial.commerce.chat.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message {

    @Id
    private String id;
    private String senderId;
    private String senderName;
    private String receiverEmail;
    private String content;
    private Date date;
    private String picturePath;

    public Message(String id, String senderId, String content, String receiverEmail, Date date) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.receiverEmail = receiverEmail;
        this.date = date;
    }

    public Message(String senderId, String senderName, String receiverEmail, String content, Date date,
            String picturePath) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.date = date;
        this.picturePath = picturePath;
    }

    public String getcontent() {
        return content;
    }

    public String getsenderName() {
        return senderName;
    }

    public void setsenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getreceiverEmail() {
        return receiverEmail;
    }

    public void setreceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsenderId() {
        return senderId;
    }

    public void setsenderId(String senderId) {
        this.senderId = senderId;
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", content='" + content + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                '}';
    }
}
