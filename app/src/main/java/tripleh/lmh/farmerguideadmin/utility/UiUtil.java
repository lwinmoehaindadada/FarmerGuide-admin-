package tripleh.lmh.farmerguideadmin.utility;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

public class UiUtil {
    public static Toast toast;

    public static void showSnackBar(Window window, String uploaded_new_crop) {
        Snackbar snackbar = Snackbar.make(window.findViewById(16908290), (CharSequence) uploaded_new_crop, 0);
        View view = snackbar.getView();
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.gravity = 80;
        view.setLayoutParams(params);
        snackbar.show();
    }

    public static void showToast(Context context, String text) {
        toast = Toast.makeText(context, text, 0);
        toast.setGravity(17, 0, 0);
        toast.show();
    }
}
