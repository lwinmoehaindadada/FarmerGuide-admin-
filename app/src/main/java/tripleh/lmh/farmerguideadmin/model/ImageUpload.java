package tripleh.lmh.farmerguideadmin.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

import tripleh.lmh.farmerguideadmin.adapter.ChatAdapter;
import tripleh.lmh.farmerguideadmin.service.ChatHelper;

public class ImageUpload extends AsyncTask<Image, Void, Void> {
    Context context;
    boolean isAdmin;
    private ProgressDialog mProgressDialog;
    private static String successPath;

    public ImageUpload(Context context, boolean isAdmin) {
        this.context = context;
        this.isAdmin = isAdmin;
        this.mProgressDialog = new ProgressDialog(context);
    }

    protected Void doInBackground(Image... images) {
        upload(images[0]);
        return null;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public void showProgressDialog() {
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setProgressStyle(0);
        this.mProgressDialog.setMessage("Uploading...");
        this.mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
        }
    }

    private void upload(final Image image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.getBitmap().compress(CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UUID randromid = UUID.randomUUID();
        StringBuilder path = new StringBuilder();
        path.append("messagesphoto/");
        path.append(randromid);
        path.append(".jpg");
        successPath=path.toString();
        FirebaseStorage.getInstance().getReference(path.toString()).putBytes(data).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {

            /* renamed from: com.admin.farmerguideadmin.model.ImageUpload$1$1 */
            class C07121 implements OnSuccessListener<Uri> {
                C07121() {
                }

                public void onSuccess(Uri uri) {
                    Message message;
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("messages");
                    String downloadURI = uri.toString();
                    if (ImageUpload.this.isAdmin) {
                        message = new Message(image.getUser().getId(), Type.admin, "", ServerValue.TIMESTAMP, downloadURI, MessageType.image, Boolean.valueOf(false));
                    } else {
                        message = new Message(image.getUser().getId(), Type.user, "", ServerValue.TIMESTAMP, downloadURI, MessageType.image, Boolean.valueOf(false));
                    }
                    database = database.child(image.getUser().getId());
                    database.child(database.push().getKey()).setValue(message);
                    if (ImageUpload.this.isAdmin) {
                        ChatHelper.sendChat(ChatAdapter.city, image.getUser().getId(), message, image.getUser().getName(), true);
                    } else {
                        ChatHelper.sendChat(ChatAdapter.city, image.getUser().getId(), message, image.getUser().getName(), false);
                    }
                    ImageUpload.this.hideProgressDialog();
                }
            }

            public void onSuccess(TaskSnapshot taskSnapshot) {
                FirebaseStorage.getInstance().getReference().child(ImageUpload.successPath).getDownloadUrl().addOnSuccessListener(new C07121());
            }
        });
    }
}
