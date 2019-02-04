package tripleh.lmh.farmerguideadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import tripleh.lmh.farmerguideadmin.activity.ChatActivity;
import tripleh.lmh.farmerguideadmin.model.Chat;
import tripleh.lmh.farmerguideadmin.model.User;

public class ChatItemClickListener implements OnClickListener {
    /* renamed from: c */
    private Chat chat;
    private Context context;

    public ChatItemClickListener(Chat c, Context context) {
        this.chat = c;
        this.context = context;
    }

    public void onClick(View v) {
        User u = new User(this.chat.getId(), this.chat.getUsername(), "", "", v.getContext().getSharedPreferences("adpref", 0).getString("city", ""));
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra("user", u);
        intent.putExtra("city", ChatAdapter.city);
        Log.i("chat item clicked ee", this.chat.getLastmessage());
        v.getContext().startActivity(intent);
    }
}
