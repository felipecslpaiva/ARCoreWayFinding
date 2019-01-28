package com.tribalscale.felipepaiva.arway2.chat;

public class ChatMessage {
    private String type;
    private String time;
    private String message;
    private int messageType = 0;
    private int messageOrigin = 0;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    private String imageurl = "";

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageOrigin() {
        return messageOrigin;
    }

    public void setMessageOrigin(int messageOrigin) {
        this.messageOrigin = messageOrigin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void buildUserMessageForText(String text) {
        this.setMessage(text);
        this.setMessageType(3);
        this.setMessageOrigin(1);
    }
}
