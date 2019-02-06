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

    void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    private String imageurl = "";

    int getMessageType() {
        return messageType;
    }

    void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageOrigin() {
        return messageOrigin;
    }

    void setMessageOrigin(int messageOrigin) {
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

    void setMessage(String message) {
        this.message = message;
    }

    void buildUserMessageForText(String text) {
        this.setMessage(text);
        this.setMessageType(ChatViewTypes.USER_MESSAGE_TEXT);
        this.setMessageOrigin(ChatMessageOrigin.LOCAL);
    }

    void buildBotMessageForText(String text) {
        this.setMessage(text);
        this.setMessageType(ChatViewTypes.BOT_TEXT_SIMPLE);
        this.setMessageOrigin(ChatMessageOrigin.REMOTE);
    }
}
