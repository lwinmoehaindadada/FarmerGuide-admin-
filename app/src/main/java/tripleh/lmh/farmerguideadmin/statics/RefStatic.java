package tripleh.lmh.farmerguideadmin.statics;

import android.app.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RefStatic extends Application {
    public static final DatabaseReference adminPass = FirebaseDatabase.getInstance().getReference("adminpass");
    public static final DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("admins");
    public static final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("chat");
    public static final DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("messages");
    public static final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("post");
    public static final DatabaseReference userREf = FirebaseDatabase.getInstance().getReference("user");
}
