package tripleh.lmh.farmerguideadmin.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.model.Message;

public class ReceiveViewHolder extends ViewHolder {
    TextView my_message;
    TextView showTime;

    class MessageClickListener implements OnClickListener {
        MessageClickListener() {
        }

        public void onClick(View v) {
            if (ReceiveViewHolder.this.showTime.getVisibility() == View.VISIBLE) {
                ReceiveViewHolder.this.showTime.setVisibility(View.INVISIBLE);
            } else {
                ReceiveViewHolder.this.showTime.setVisibility(View.VISIBLE);
            }
        }
    }

    public ReceiveViewHolder(@NonNull View itemView) {
        super(itemView);
        this.my_message = (TextView) itemView.findViewById(R.id.their_message_body);
        this.showTime = (TextView) itemView.findViewById(R.id.time_show);
        this.my_message.setOnClickListener(new MessageClickListener());
    }

    public void bindToMessage(Message msg) {
        this.showTime.setText(new PrettyTime().format(new Date(((Long) msg.getTime()).longValue())));
        this.my_message.setText(msg.getText());
    }
}
