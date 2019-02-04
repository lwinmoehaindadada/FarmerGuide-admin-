package tripleh.lmh.farmerguideadmin.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.dialog.AdminDialog;
import tripleh.lmh.farmerguideadmin.holder.ChatViewHolder;
import tripleh.lmh.farmerguideadmin.model.Chat;
import tripleh.lmh.farmerguideadmin.service.ChatHelper;
import tripleh.lmh.farmerguideadmin.service.MessageHelper;
import tripleh.lmh.farmerguideadmin.utility.UiUtil;

public class ChatAdapter extends Adapter<ChatViewHolder> {
    public static String city = "pakokku";
    private List<Chat> chats;
    private Context context;
    SharedPreferences preferences;

    public ChatAdapter(List<Chat> chats, Context context) {
        this.chats = chats;
        this.context = context;

        setHasStableIds(true);
    }

    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_items, parent, false));
    }

    public void onBindViewHolder(@NonNull final ChatViewHolder holder, int position) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(position);
        stringBuilder.append("*****");
        Log.i("Position", stringBuilder.toString());
        holder.onBind((Chat) this.chats.get(position), position);

    }

    public int getItemCount() {
        return this.chats.size();
    }

    public void removeItem(int index) {
        this.chats.remove(index);
        notifyDataSetChanged();
    }

    public void addToTop(Chat c) {
        this.chats.add(0, c);
        notifyDataSetChanged();
        notifyItemInserted(0);
    }

    public void addToBottom(Chat c) {
        this.chats.add(this.chats.size(), c);
        notifyDataSetChanged();
    }

    public void changeIndexData(Chat c, int i) {
        ((Chat) this.chats.get(i)).setAdmin(c.isAdmin());
        ((Chat) this.chats.get(i)).setDate(c.getDate());
        ((Chat) this.chats.get(i)).setProfileurl(c.getProfileurl());
        ((Chat) this.chats.get(i)).setUsername(c.getUsername());
        ((Chat) this.chats.get(i)).setId(c.getId());
        ((Chat) this.chats.get(i)).setSeen(c.isSeen());
        ((Chat) this.chats.get(i)).setPhoto(c.isPhoto());
        notifyDataSetChanged();
    }
}
