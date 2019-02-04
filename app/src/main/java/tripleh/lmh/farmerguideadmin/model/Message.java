package tripleh.lmh.farmerguideadmin.model;

import java.io.Serializable;

public class Message implements Serializable {
    private String id;
    private String image_url;
    private MessageType messageType;
    private String nodeKey;
    private Boolean seen;
    private String text;
    private Object time;
    private Type type;
    private String user_id;

    public Message(){}

    public String getNodeKey() {
        return this.nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Message(String user_id, Type type, String text, Object time, String image_url, MessageType messageType, Boolean seen) {
        this.image_url = image_url;
        this.messageType = messageType;
        this.user_id = user_id;
        this.type = type;
        this.text = text;
        this.time = time;
        this.seen = seen;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getTime() {
        return this.time;
    }

    public Boolean getSeen() {
        return this.seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" messages -> ");
        stringBuilder.append(getText());
        stringBuilder.append("  :-> imageurl ");
        stringBuilder.append(getImage_url());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
