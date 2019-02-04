package tripleh.lmh.farmerguideadmin.model;

public class News {
    String news_body;
    String news_date;
    String news_image;
    String news_title;

    public News(){}

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    public void setNews_body(String news_body) {
        this.news_body = news_body;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getNews_title() {
        return this.news_title;
    }

    public String getNews_date() {
        return this.news_date;
    }

    public String getNews_body() {
        return this.news_body;
    }

    public String getNews_image() {
        return this.news_image;
    }
}
