package tripleh.lmh.farmerguideadmin.model;

import java.io.Serializable;

public class Chat implements Comparable<Chat>, Serializable {
    private boolean admin;
    private Object date;
    private String id;
    private String lastmessage;
    private boolean photo;
    private String profileurl;
    private boolean seen;
    private String username;

    public Chat(){

    }
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nid=");
        stringBuilder.append(this.id);
        stringBuilder.append("\n username=");
        stringBuilder.append(this.username);
        stringBuilder.append("\n last msg=");
        stringBuilder.append(this.lastmessage);
        stringBuilder.append("\n date=");
        stringBuilder.append(this.date);
        stringBuilder.append("\n=======\n");
        return stringBuilder.toString();
    }

    public Chat(String id, String username, String profileurl, String lastmessage, boolean seen, boolean admin, boolean photo, Object date) {
        this.id = id;
        this.username = username;
        this.profileurl = profileurl;
        this.lastmessage = lastmessage;
        this.seen = seen;
        this.admin = admin;
        this.photo = photo;
        this.date = date;
    }

    public boolean isPhoto() {
        return this.photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileurl() {
        return this.profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getLastmessage() {
        return this.lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public boolean isSeen() {
        return this.seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Object getDate() {
        return this.date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public int compareTo(Chat o) {
        return (int) (((Long) o.getDate()).longValue() - ((Long) getDate()).longValue());
    }
}
