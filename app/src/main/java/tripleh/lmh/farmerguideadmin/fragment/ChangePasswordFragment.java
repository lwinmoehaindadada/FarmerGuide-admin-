package tripleh.lmh.farmerguideadmin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.dialog.AdminDialog;
import tripleh.lmh.farmerguideadmin.security.MD5Helper;
import tripleh.lmh.farmerguideadmin.sharepref.SharePrefHelper;
import tripleh.lmh.farmerguideadmin.statics.RefStatic;
import tripleh.lmh.farmerguideadmin.utility.UiUtil;

public class ChangePasswordFragment extends Fragment {
    public static String confirm;
    public static String newpassword;
    public static String oldpassword;
    public static String refpassword;
    private Button changePasswordBtn;
    EditText confirmPass;

    MD5Helper helper = new MD5Helper();
    EditText newPass;
    EditText oldPass;
    DatabaseReference reference = RefStatic.adminPass;
    SharePrefHelper sharePrefHelper = null;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
        this.sharePrefHelper = new SharePrefHelper(getActivity());
        this.oldPass = (EditText) view.findViewById(R.id.edt_old_password);
        this.newPass = (EditText) view.findViewById(R.id.edt_new_password);
        this.confirmPass = (EditText) view.findViewById(R.id.edt_confirm_password);
        this.changePasswordBtn = (Button) view.findViewById(R.id.changePassBtn);
        this.changePasswordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment.this.onChangePassword();
            }
        });
        this.reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChangePasswordFragment.refpassword = (String) dataSnapshot.child("pass").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void onChangePassword() {
        newpassword = this.newPass.getText().toString();
        oldpassword = this.oldPass.getText().toString();
        confirm = this.confirmPass.getText().toString();
        if (!(newpassword.equals("") || oldpassword.equals(""))) {
            if (!confirm.equals("")) {
                newpassword = this.helper.decryptMD(this.newPass.getText().toString());
                oldpassword = this.helper.decryptMD(this.oldPass.getText().toString());
                confirm = this.helper.decryptMD(this.confirmPass.getText().toString());
                if (!oldpassword.equals(refpassword)) {
                    UiUtil.showToast(getActivity(), "Failed,Incorrect old password");
                    return;
                } else if (newpassword.equals(confirm)) {
                    new AdminDialog.DialogBuilder().Context(getActivity()).message("Are you sure you want to change admin password?").Result(new AdminDialog.OnDialogResult() {
                        @Override
                        public void finish() {
                            ChangePasswordFragment.this.reference.child("pass").setValue(ChangePasswordFragment.newpassword);
                            ChangePasswordFragment.this.sharePrefHelper.setAdminPassword(ChangePasswordFragment.this.newPass.getText().toString());
                            UiUtil.showToast(ChangePasswordFragment.this.getActivity(), "Success,changed password");
                            ChangePasswordFragment.this.newPass.setText("");
                            ChangePasswordFragment.this.confirmPass.setText("");
                            ChangePasswordFragment.this.oldPass.setText("");
                        }
                    }).okBgColor(R.color.yso_color).Build().show();
                    return;
                } else {
                    UiUtil.showToast(getActivity(), "Please confirm new password");
                    return;
                }
            }
        }
        UiUtil.showToast(getActivity(), "Please Enter passwords correctyl");
    }
}
