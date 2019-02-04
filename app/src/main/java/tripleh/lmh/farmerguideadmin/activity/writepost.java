package tripleh.lmh.farmerguideadmin.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.dialog.AdminDialog;
import tripleh.lmh.farmerguideadmin.model.Post;
import tripleh.lmh.farmerguideadmin.statics.RefStatic;
import tripleh.lmh.farmerguideadmin.utility.FileUtil;
import tripleh.lmh.farmerguideadmin.utility.ImageUtil;

public class writepost extends AppCompatActivity {
    private String UploadImageUrl;
    Activity activity;
    private Uri filePath;
    private DatabaseReference mDatabase = RefStatic.postRef;
    String s1;
    String s2;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = this.storage.getReference();
    EditText t1;
    EditText t2;
    String today;
    Button uploadPost;

    class UploadPostClickListener implements OnClickListener {

        @SuppressLint({"ResourceAsColor"})
        public void onClick(final View v) {
            if (!(writepost.this.filePath == null || writepost.this.t1.getText().toString().equals(""))) {
                if (!writepost.this.t2.getText().toString().equals("")) {
                    new AdminDialog.DialogBuilder().Context(writepost.this).message("Post this article?").Result(new AdminDialog.OnDialogResult() {
                        public void finish() {
                            writepost.this.s1 = writepost.this.t1.getText().toString();
                            writepost.this.s2 = writepost.this.t2.getText().toString();
                            Date todayDate = Calendar.getInstance().getTime();
                            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
                            writepost.this.today = formatter.format(todayDate);
                            SimpleDateFormat formatter2 = new SimpleDateFormat("h:mm a");
                            StringBuilder stringBuilder = new StringBuilder();
                            writepost writepost = writepost.this;
                            stringBuilder.append(writepost.today);
                            stringBuilder.append(" at ");
                            stringBuilder.append(formatter2.format(todayDate));
                            writepost.today = stringBuilder.toString();
                            if (writepost.this.filePath != null) {
                                final ProgressDialog progressDialog = new ProgressDialog(writepost.this);
                                progressDialog.setTitle("Uploading...");
                                progressDialog.show();
                                File actualImage = null;
                                try {
                                    actualImage = FileUtil.from(v.getContext(), writepost.this.filePath);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ImageUtil imageUtil = new ImageUtil(actualImage, v.getContext(), null, true);
                                BitmapFactory.decodeFile(actualImage.getAbsolutePath()).compress(CompressFormat.WEBP, 100, baos);
                                byte[] data = baos.toByteArray();
                                writepost writepost2 = writepost.this;
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("po/");
                                stringBuilder2.append(UUID.randomUUID().toString());
                                writepost2.UploadImageUrl = stringBuilder2.toString();
                                final StorageReference ref = writepost.this.storageReference.child(writepost.this.UploadImageUrl);
                                ref.putBytes(data).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {

                                    /* renamed from: com.admin.farmerguideadmin.activity.writepost$1$2$3$1 */
                                    class C06941 implements OnFailureListener {
                                        C06941() {
                                        }

                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    }

                                    public void onSuccess(TaskSnapshot taskSnapshot) {
                                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String id = writepost.this.mDatabase.push().getKey();
                                                writepost.this.mDatabase.child(id).setValue(new Post(id, writepost.this.s1, writepost.this.s2, writepost.this.today, uri.toString(), 2));

                                            }
                                        }).addOnFailureListener(new C06941());
                                        progressDialog.dismiss();
                                        Toast.makeText(writepost.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                        writepost.this.activity.finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Context context = writepost.this;
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("Failed ");
                                        stringBuilder.append(e.getMessage());
                                        Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<TaskSnapshot>() {
                                    public void onProgress(TaskSnapshot taskSnapshot) {
                                        double bytesTransferred = (double) taskSnapshot.getBytesTransferred();
                                        Double.isNaN(bytesTransferred);
                                        bytesTransferred *= 100.0d;
                                        double totalByteCount = (double) taskSnapshot.getTotalByteCount();
                                        Double.isNaN(totalByteCount);
                                        bytesTransferred /= totalByteCount;
                                        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("Uploaded ");
                                        stringBuilder.append((int) bytesTransferred);
                                        stringBuilder.append("%");
                                        progressDialog.setMessage(stringBuilder.toString());
                                    }
                                });
                            }
                        }
                    }).Build().show();
                    return;
                }
            }
            new AdminDialog.DialogBuilder().Context(writepost.this).Result(new AdminDialog.OnDialogResult() {
                @Override
                public void finish() {

                }
            }).message("Please insert title,body and image correctly").Build().show();
        }
    }

    /* renamed from: com.admin.farmerguideadmin.activity.writepost$2 */
    class C04052 implements OnClickListener {
        C04052() {
        }

        public void onClick(View v) {
            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
            intent.setType("image/*");
            writepost.this.startActivityForResult(intent, 1);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_new_post);
        this.activity = this;
        this.uploadPost = (Button) findViewById(R.id.uploadPost);
        Button selectImage = (Button) findViewById(R.id.selectImage);
        this.t1 = (EditText) findViewById(R.id.titleCreatePost);
        this.t2 = (EditText) findViewById(R.id.infoCreatePost);
        this.uploadPost.setOnClickListener(new UploadPostClickListener());
        selectImage.setOnClickListener(new C04052());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == -1 && data != null) {
            this.filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = Media.getBitmap(getContentResolver(), this.filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((ImageView) findViewById(R.id.img)).setImageBitmap(bitmap);
        }
    }
}
