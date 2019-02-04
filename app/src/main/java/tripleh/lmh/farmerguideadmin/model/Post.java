package tripleh.lmh.farmerguideadmin.model;

import java.io.Serializable;

public class Post implements Serializable {
    private String admin_name;
    private String date;
    private String id;
    private String info = "";
    private String title;
    private int type;
    private String url = "";
    public Post(){}

    public String getAdmin_name() {
        return this.admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public Post(String id, String title, String info, String date, String url, int type, String admin_name) {
        setTitle(title);
        setInfo(info);
        setDate(date);
        setUrl(url);
        this.admin_name = admin_name;
        setType(type);
        setId(id);
    }

    public Post(String id, String title, String info, String date, String url, int type) {
        setTitle(title);
        setInfo(info);
        setDate(date);
        setUrl(url);
        setType(type);
        setId(id);
    }

    public Post(String id, String title, String info, String date, int type) {
        setTitle(title);
        setInfo(info);
        setDate(date);
        setType(type);
        setId(id);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
