package tripleh.lmh.farmerguideadmin.service;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import tripleh.lmh.farmerguideadmin.model.Chat;
import tripleh.lmh.farmerguideadmin.model.Message;
import tripleh.lmh.farmerguideadmin.model.MessageType;

public class ChatHelper {
    private static String adminId = null;
    public static DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("chat");
    public static boolean userExists = false;

    /* renamed from: com.admin.farmerguideadmin.service.ChatHelper$1 */
    static class C07141 implements ValueEventListener {
        C07141() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                ChatHelper.userExists = true;
            } else {
                ChatHelper.userExists = false;
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    public static DatabaseReference getChatRef(String township) {
        return chatRef.child(township);
    }

    public static void sendChat(String adminid, String userid, Message mg, String username, boolean admin) {
        String str = userid;
        boolean isPhoto = false;
        if (mg.getMessageType() == MessageType.image) {
            isPhoto = true;
        }
        boolean isPhoto2 = isPhoto;
        if (adminId == null) {
            adminId = adminid;
            chatRef = chatRef.child(adminId);
        }
        if (admin) {
            Log.i("trace", "user is admin");
            chatRef.child(userid).child("id").setValue(mg.getUser_id());
            chatRef.child(userid).child("lastmessage").setValue(mg.getText());
            chatRef.child(userid).child("date").setValue(ServerValue.TIMESTAMP);
            chatRef.child(userid).child("seen").setValue(mg.getSeen());
            chatRef.child(userid).child("admin").setValue(Boolean.valueOf(true));
            chatRef.child(userid).child("photo").setValue(Boolean.valueOf(isPhoto2));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("user id is");
        stringBuilder.append(userid);
        Log.i("trace", stringBuilder.toString());
        chatRef.child(userid).addValueEventListener(new C07141());
        if (userExists) {
            Log.i("trace", "id is not null");
            chatRef.child(userid).child("admin").setValue(Boolean.valueOf(false));
            chatRef.child(userid).child("photo").setValue(Boolean.valueOf(isPhoto2));
            chatRef.child(userid).child("id").setValue(mg.getUser_id());
            chatRef.child(userid).child("lastmessage").setValue(mg.getText());
            chatRef.child(userid).child("date").setValue(ServerValue.TIMESTAMP);
            chatRef.child(userid).child("seen").setValue(mg.getSeen());
            return;
        }
        Log.i("trace", "id is null");
        chatRef.child(userid).setValue(new Chat(userid, username, "", mg.getText(), mg.getSeen().booleanValue(), admin, isPhoto2, ServerValue.TIMESTAMP));
    }
}
