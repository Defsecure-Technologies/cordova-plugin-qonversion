package cordova.plugin.qonversion;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionLaunchCallback;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QLaunchResult;
import com.qonversion.android.sdk.dto.QPermission;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class QonversionUtil {
    static public String projectKey = "Xc_HalXEKT0El9OK7SxAAobBdBodF26F";
    static public String perDeviceUserId = "";
    static public List<String> permissionIds = Arrays.asList("premium_qonversion_demo");
    static public List<QonversionProduct> products = Arrays.asList(new QonversionProduct("weekly_premium_qonversion_demo", permissionIds.get(0)));

    static void launch(Application application, LaunchCallback callback) {
        Qonversion.setDebugMode();
        Qonversion.launch(application, projectKey, false, new QonversionLaunchCallback() {
            @Override
            public void onSuccess(@NotNull QLaunchResult qLaunchResult) {
                perDeviceUserId = qLaunchResult.getUid();
                Qonversion.setUserID(perDeviceUserId);
                callback.onLauchCallback(true);
            }

            @Override
            public void onError(@NotNull QonversionError qonversionError) {
               callback.onLauchCallback(false);
            }
        });
    }

    public interface LaunchCallback {
        void onLauchCallback(Boolean result);
    }

    static void checkPermission(CheckPermissionCallback callback) {
        Qonversion.checkPermissions(new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NotNull Map<String, QPermission> permissions) {
                List<QonversionActivePermission> activePermissions = new ArrayList<>();
                int index = 0;
                for (Map.Entry<String, QPermission> entry: permissions.entrySet()) {
                    String permissionId = permissionIds.get(index);
                    if (entry.getKey().equals(permissionId) && entry.getValue().isActive()) {
                        QonversionActivePermission activePermission = new QonversionActivePermission();
                        activePermission.initQActivePermissions(permissionId, entry.getValue());
                        activePermissions.add(activePermission);
                    }
                    index ++;
                }
                callback.onCheckPermissionCallback(activePermissions);
            }

            @Override
            public void onError(@NotNull QonversionError qonversionError) {
                Log.d("ERROR===>", qonversionError.getDescription());
            }
        });
    }

    public interface CheckPermissionCallback {
        void onCheckPermissionCallback(List<QonversionActivePermission> activePermissions);
    }

    static public void configure(Application application, ConfigureCallback callback) {
        QonversionUtil.launch(application, result -> {
            if (result) {
                QonversionUtil.checkPermission(activePermissions -> {
                    if (activePermissions.size() > 0) {
                        callback.onConfigureCallback(true);
                    } else {
                        callback.onConfigureCallback(false);
                    }
                });
            } else {
                callback.onConfigureCallback(false);
            }
        });
    }

    public interface ConfigureCallback {
        void onConfigureCallback(Boolean result);
    }

    static public void purchase(QonversionProduct product, Activity activity, PurchaseCallback callback) {
        Qonversion.purchase(activity, product.id, new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NotNull Map<String, QPermission> permissions) {
                boolean isSuccess = false;
                for (Map.Entry<String, QPermission> entry: permissions.entrySet()) {
                    if (entry.getKey().equals(product.permissionId) && entry.getValue().isActive()) {
                        isSuccess = true;
                        break;
                    }
                }
              try {
                callback.onPurchaseCallback(isSuccess);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }

            @Override
            public void onError(@NotNull QonversionError qonversionError) {
              try {
                callback.onPurchaseCallback(false);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
        });
    }

    public interface PurchaseCallback {
        void onPurchaseCallback(Boolean result) throws JSONException;
    }

    static public void restore(QonversionProduct product, RestoreCallback callback) {
        Qonversion.restore(new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NotNull Map<String, QPermission> permissions) {
                boolean isSuccess = false;
                for (Map.Entry<String, QPermission> entry: permissions.entrySet()) {
                    if (entry.getKey().equals(product.permissionId) && entry.getValue().isActive()) {
                        isSuccess = true;
                        break;
                    }
                }
              try {
                callback.onRestoreCallback(isSuccess);
              } catch (JSONException e) {
                e.printStackTrace();
              }

            }

            @Override
            public void onError(@NotNull QonversionError qonversionError) {
              try {
                callback.onRestoreCallback(false);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
        });
    }

    public interface RestoreCallback {
        void onRestoreCallback(Boolean result) throws JSONException;
    }
}
