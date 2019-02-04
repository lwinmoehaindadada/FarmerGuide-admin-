package tripleh.lmh.farmerguideadmin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tripleh.lmh.farmerguideadmin.R;

public class AdminDialog extends Dialog implements OnClickListener {
    private int bgCancelColor;
    private int bgOkColor;
    private Context context;
    /* renamed from: d */
    private Dialog d;
    private RelativeLayout dialog;
    private int dialogBackgroundColor;
    private LinearLayout no;
    private OnDialogResult onDialogResult;
    private int txtCancelColor;
    private TextView txtMessage;
    private int txtOkColor;
    private String txt_message;
    private LinearLayout yes;

    public static class DialogBuilder {
        private int bgCancelColor;
        private int bgOkColor;
        private Context context;
        private int dialogBackgroundColor;
        private OnDialogResult onDialogResult;
        private int txtCancelColor;
        private int txtOkColor;
        private String txt_message;

        public DialogBuilder Context(Context context) {
            this.context = context;
            return this;
        }

        public DialogBuilder Result(OnDialogResult result) {
            this.onDialogResult = result;
            return this;
        }

        public DialogBuilder txtCancelColor(int color) {
            this.txtCancelColor = color;
            return this;
        }

        public DialogBuilder txtOkColor(int color) {
            this.txtOkColor = color;
            return this;
        }

        public DialogBuilder cancelBgColor(int color) {
            this.bgCancelColor = color;
            return this;
        }

        public DialogBuilder okBgColor(int color) {
            this.bgOkColor = color;
            return this;
        }

        public DialogBuilder background(int color) {
            this.dialogBackgroundColor = color;
            return this;
        }

        public DialogBuilder message(String mess) {
            this.txt_message = mess;
            return this;
        }

        public AdminDialog Build() {
            return new AdminDialog(this);
        }
    }

    public interface OnDialogResult {
        void finish();
    }

    public AdminDialog(DialogBuilder dialogBuilder) {
        super(dialogBuilder.context);
        this.context = dialogBuilder.context;
        this.onDialogResult = dialogBuilder.onDialogResult;
        this.txt_message = dialogBuilder.txt_message;
        this.txtCancelColor = dialogBuilder.txtCancelColor;
        this.txtOkColor = dialogBuilder.txtOkColor;
        this.bgCancelColor = dialogBuilder.bgCancelColor;
        this.bgOkColor = dialogBuilder.bgOkColor;
        this.dialogBackgroundColor = dialogBuilder.dialogBackgroundColor;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.admin_dialog);
        this.dialog = (RelativeLayout) findViewById(R.id.dialog);
        this.yes = (LinearLayout) findViewById(R.id.edt_delete_crop_delete);
        this.no = (LinearLayout) findViewById(R.id.edt_delete_crop_cancel);
        this.txtMessage = (TextView) findViewById(R.id.txt_ask_question);
        this.txtMessage.setText(this.txt_message);
        if (this.bgOkColor != 0) {
            this.yes.setBackgroundColor(this.context.getResources().getColor(this.bgOkColor));
        }
        if (this.bgCancelColor != 0) {
            this.no.setBackgroundColor(this.context.getResources().getColor(this.bgCancelColor));
        }
        if (this.dialogBackgroundColor != 0) {
            this.dialog.setBackgroundColor(this.context.getResources().getColor(this.dialogBackgroundColor));
        }
        this.yes.setOnClickListener(this);
        this.no.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == this.no) {
            cancel();
        }
        if (v == this.yes) {
            this.onDialogResult.finish();
            cancel();
        }
    }
}
