package tripleh.lmh.farmerguideadmin.model;

import java.io.Serializable;

public class Data implements Serializable {
    String body;
    String image_url;
    String title;
    public Data(){}

    public Data(String title, String body, String image_url) {
        this.title = title;
        this.body = body;
        this.image_url = image_url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public String getImage_url() {
        return this.image_url;
    }
}
