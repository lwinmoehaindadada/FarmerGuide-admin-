package tripleh.lmh.farmerguideadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.security.MD5Helper;
import tripleh.lmh.farmerguideadmin.sharepref.SharePrefHelper;
import tripleh.lmh.farmerguideadmin.statics.RefStatic;
import tripleh.lmh.farmerguideadmin.utility.UiUtil;

public class EnterPassword extends AppCompatActivity {
    private static String pass = "";
    EditText editPass;
    MD5Helper md5Helper = new MD5Helper();
    DatabaseReference reference = RefStatic.adminPass;
    SharePrefHelper sharePrefHelper = null;

    /* renamed from: com.admin.farmerguideadmin.activity.EnterPassword$1 */
    class C06901 implements ValueEventListener {
        C06901() {
        }

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            EnterPassword.pass = (String) dataSnapshot.child("pass").getValue();
        }

        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        this.sharePrefHelper = new SharePrefHelper(getApplicationContext());
        if (!this.sharePrefHelper.getAdminPassword().equals("")) {
            startActivity(new Intent(this, AdminActivity.class));
            finish();
        }
        this.editPass = (EditText) findViewById(R.id.edt_password);
        this.reference.addListenerForSingleValueEvent(new C06901());
    }

    public void onOkClick(View view) {
        if (this.editPass.getText().toString().equals("")) {
            UiUtil.showToast(getApplicationContext(), "Invalid password");
        } else if (this.md5Helper.decryptMD(this.editPass.getText().toString()).equals(pass)) {
            UiUtil.showToast(getApplicationContext(), "Logged in as admin");
            this.sharePrefHelper.setAdminPassword(this.editPass.getText().toString());
            startActivity(new Intent(this, AdminActivity.class));
            finish();
        } else {
            UiUtil.showToast(getApplicationContext(), "Invalid password");
        }
    }
}
