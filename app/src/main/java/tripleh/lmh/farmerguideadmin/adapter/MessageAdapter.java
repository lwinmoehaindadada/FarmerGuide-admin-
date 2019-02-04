package tripleh.lmh.farmerguideadmin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.holder.ReceiveImageViewHolder;
import tripleh.lmh.farmerguideadmin.holder.ReceiveViewHolder;
import tripleh.lmh.farmerguideadmin.holder.SendImageViewHolder;
import tripleh.lmh.farmerguideadmin.holder.SendViewHolder;
import tripleh.lmh.farmerguideadmin.model.Message;
import tripleh.lmh.farmerguideadmin.model.MessageType;
import tripleh.lmh.farmerguideadmin.model.Type;

public class MessageAdapter extends Adapter<ViewHolder> {
    private final int RECEIVE_IMAGE_VIEW = 4;
    private final int RECEIVE_VIEW = 2;
    private final int SEND_IMAGE_VIEW = 3;
    private final int SEND_VIEW = 1;
    private final Context context;
    private final LinkedList<Message> mg;

    public MessageAdapter(Context conext, LinkedList<Message> mg) {
        this.mg = mg;
        this.context = conext;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sendView = LayoutInflater.from(this.context).inflate(R.layout.my_message, parent, false);
        View receiveView = LayoutInflater.from(this.context).inflate(R.layout.their_message, parent, false);
        View sendImage = LayoutInflater.from(this.context).inflate(R.layout.my_image, parent, false);
        View receiveImage = LayoutInflater.from(this.context).inflate(R.layout.their_image_message, parent, false);
        if (viewType == 1) {
            return new SendViewHolder(sendView);
        }
        if (viewType == 3) {
            return new SendImageViewHolder(sendImage, this.context);
        }
        if (viewType == 4) {
            return new ReceiveImageViewHolder(receiveImage, this.context);
        }
        return new ReceiveViewHolder(receiveView);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Message m = (Message) this.mg.get(i);
        switch (viewHolder.getItemViewType()) {
            case 1:
                ((SendViewHolder) viewHolder).bindToMessage(m);
                return;
            case 2:
                ((ReceiveViewHolder) viewHolder).bindToMessage(m);
                return;
            case 3:
                ((SendImageViewHolder) viewHolder).bindToImage(m);
                return;
            case 4:
                ((ReceiveImageViewHolder) viewHolder).bindToImage(m);
                return;
            default:
                return;
        }
    }

    public int getItemViewType(int position) {
        Message m = (Message) this.mg.get(position);
        if (m.getType().equals(Type.user) && m.getMessageType().equals(MessageType.text)) {
            return 1;
        }
        if (m.getType().equals(Type.user) && m.getMessageType().equals(MessageType.image)) {
            return 3;
        }
        if (m.getType().equals(Type.admin) && m.getMessageType().equals(MessageType.image)) {
            return 4;
        }
        return 2;
    }

    public int getItemCount() {
        return this.mg.size();
    }
}
