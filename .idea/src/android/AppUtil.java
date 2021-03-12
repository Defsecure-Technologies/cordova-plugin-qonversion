package cordova.plugin.qonversion;

import android.app.ProgressDialog;
import android.content.Context;

public class AppUtil {
    public static Boolean isSubscriped = false;

    static public ProgressDialog onShowProgressDialog(final Context mActivity, final String message, boolean isCancelable) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.show();
        progressDialog.setCancelable(isCancelable);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    static public void onDismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
