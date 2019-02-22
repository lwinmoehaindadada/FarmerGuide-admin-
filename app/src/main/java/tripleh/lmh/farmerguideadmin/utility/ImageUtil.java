package tripleh.lmh.farmerguideadmin.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tripleh.lmh.farmerguideadmin.adapter.ChatAdapter;
import tripleh.lmh.farmerguideadmin.model.Message;
import tripleh.lmh.farmerguideadmin.model.MessageType;
import tripleh.lmh.farmerguideadmin.model.Type;
import tripleh.lmh.farmerguideadmin.model.User;
import tripleh.lmh.farmerguideadmin.service.ChatHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

public class ImageUtil {
    private File actualImage;
    private Bitmap bitmap;
    private File compressedImage;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("messages");
    private String downloadURI;
    private StorageReference fb = FirebaseStorage.getInstance().getReference();
    boolean isAdmin;
    private ProgressDialog mProgressDialog;
    private String path;
    private ProgressBar upload_load;
    private User user;

    /* renamed from: com.admin.farmerguideadmin.utility.ImageUtil$1 */
    class C07161 implements OnSuccessListener<TaskSnapshot> {

        /* renamed from: com.admin.farmerguideadmin.utility.ImageUtil$1$1 */
        class C07151 implements OnSuccessListener<Uri> {
            C07151() {
            }

            public void onSuccess(Uri uri) {
                Message message;
                ImageUtil.this.downloadURI = uri.toString();
                if (ImageUtil.this.isAdmin) {
                    message = new Message(ImageUtil.this.user.getId(), Type.admin, "", ServerValue.TIMESTAMP, ImageUtil.this.downloadURI, MessageType.image, Boolean.valueOf(false));
                } else {
                    message = new Message(ImageUtil.this.user.getId(), Type.user, "", ServerValue.TIMESTAMP, ImageUtil.this.downloadURI, MessageType.image, Boolean.valueOf(false));
                }
                ImageUtil.this.database = ImageUtil.this.database.child(ImageUtil.this.user.getId());
                ImageUtil.this.database.child(ImageUtil.this.database.push().getKey()).setValue(message);
                ChatHelper.sendChat(ChatAdapter.city, ImageUtil.this.user.getId(), message, ImageUtil.this.user.getName(), ImageUtil.this.isAdmin);
                ImageUtil.this.hideProgressDialog();
            }
        }

        C07161() {
        }

        public void onSuccess(TaskSnapshot taskSnapshot) {
            ImageUtil.this.fb.child(ImageUtil.this.path).getDownloadUrl().addOnSuccessListener(new C07151());
        }
    }

    /* renamed from: com.admin.farmerguideadmin.utility.ImageUtil$2 */
    class C07172 implements Consumer<File> {
        C07172() {
        }

        public void accept(File file) {
            ImageUtil.this.compressedImage = file;
            ImageUtil.this.setCompressedImage();
            ImageUtil.this.upload();
        }
    }

    /* renamed from: com.admin.farmerguideadmin.utility.ImageUtil$3 */
    class C07183 implements Consumer<Throwable> {
        C07183() {
        }

        public void accept(Throwable throwable) {
            throwable.printStackTrace();
            ImageUtil.this.showError(throwable.getMessage());
        }
    }

    public void showProgressDialog() {
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(this.context);
            this.mProgressDialog.setCancelable(false);
            this.mProgressDialog.setProgressStyle(0);
            this.mProgressDialog.setMessage("Uploading...");
        }
        this.mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
        }
    }

    public void setUpload_load(ProgressBar upload_load) {
        this.upload_load = upload_load;
    }

    public String getPath() {
        return this.path;
    }

    public File getActualImage() {
        return this.actualImage;
    }

    public void setActualImage(File actualImage) {
        this.actualImage = actualImage;
    }

    public File getCompressedImage() {
        return this.compressedImage;
    }

    public String getDownloadURI() {
        return this.downloadURI;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ImageUtil(File actualImage, Context context, User user, boolean admin) {
        this.actualImage = actualImage;
        this.isAdmin = admin;
        this.user = user;
        this.context = context;
    }

    private void upload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.bitmap.compress(CompressFormat.WEBP, 100, baos);
        byte[] data = baos.toByteArray();
        UUID randromid = UUID.randomUUID();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("messagesphoto/");
        stringBuilder.append(randromid);
        stringBuilder.append(".webp");
        this.path = stringBuilder.toString();
        FirebaseStorage.getInstance().getReference(this.path).putBytes(data).addOnSuccessListener(new C07161());
    }

    public void setCompressedImage() {
        this.bitmap = BitmapFactory.decodeFile(this.compressedImage.getAbsolutePath());
    }

    public void showError(String errorMessage) {
        Toast.makeText(this.context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void uploadImage() {
        if (this.actualImage == null) {
            showError("Please choose an image!");
            return;
        }
        showProgressDialog();
        new Compressor(this.context).setMaxWidth(640).setMaxHeight(480).setQuality(75).setCompressFormat(CompressFormat.WEBP).setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()).compressToFileAsFlowable(this.actualImage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C07172(), new C07183());
    }
}
