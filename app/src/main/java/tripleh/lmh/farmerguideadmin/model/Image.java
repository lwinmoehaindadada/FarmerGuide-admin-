package tripleh.lmh.farmerguideadmin.model;

import android.graphics.Bitmap;

public class Image {
    private Bitmap bitmap;
    private User user;

    public Image(Bitmap bitmap, User user) {
        this.bitmap = bitmap;
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
