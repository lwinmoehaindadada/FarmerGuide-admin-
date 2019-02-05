package tripleh.lmh.farmerguideadmin.holder;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.adapter.ChatBinder;
import tripleh.lmh.farmerguideadmin.adapter.ChatItemClickListener;
import tripleh.lmh.farmerguideadmin.model.Chat;

import static android.graphics.Color.GRAY;

public class ChatViewHolder extends ViewHolder implements ChatBinder {
    public CardView chatItem;
    private Context context;
    public TextView date;
    private Button delete_button;
    private int lastPosition = -1;
    public TextView lastmsg;
    public TextView name;
    private PrettyTime prettyTime = new PrettyTime();
    public ImageView profile;
    public TextView you;

    public ChatViewHolder(View itemView) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.txt_chat_name);
        this.lastmsg = (TextView) itemView.findViewById(R.id.txt_chat_last_msg);
        this.date = (TextView) itemView.findViewById(R.id.txt_chat_date);
        this.profile = (ImageView) itemView.findViewById(R.id.img_profile);
        this.chatItem = (CardView) itemView.findViewById(R.id.chat_item);
        this.you = (TextView) itemView.findViewById(R.id.txt_lbl_you);
        this.context = itemView.getContext();
    }

    public void onBind(Chat c, int position) {
        this.lastmsg.setTypeface(Typeface.DEFAULT);
        this.you.setVisibility(View.VISIBLE);
        this.chatItem.setBackgroundColor(-1);
        setAnimation(this.lastmsg, position);
        this.name.setText(c.getUsername());
        if (c.isPhoto()) {
            this.lastmsg.setText("Image");
            this.lastmsg.setTextColor(GRAY);
        } else {
            this.lastmsg.setText(c.getLastmessage());
        }
        Log.i("chat", c.toString());
        this.date.setText(this.prettyTime.format(new Date(((Long) c.getDate()).longValue())));
        Glide.with(this.context).load(c.getProfileurl()).apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(50)).placeholder(R.drawable.farmer_logo).error( R.drawable.farmer_logo)).into(this.profile);
        if (!c.isAdmin()) {
            this.you.setVisibility(View.INVISIBLE);
            if (!c.isSeen()) {
                this.chatItem.setBackgroundResource(R.color.chat_unseen);
                this.lastmsg.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }
        this.chatItem.setOnClickListener(new ChatItemClickListener(c, this.context));
        this.chatItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                
                return false;
            }
        });
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > this.lastPosition) {
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_out));
            this.lastPosition = position;
        }
    }
}
