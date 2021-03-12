package cordova.plugin.qonversion;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.laodev.qonversioniapdemo.MainActivity;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionProductsCallback;
import com.qonversion.android.sdk.dto.products.QProduct;

import java.util.Map;


public class QonversionApi extends CordovaPlugin {

    private static Activity activity;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Qonversion.setDebugMode();
        activity = this.cordova.getActivity();
        if (action.equals("purchase")) {

            JSONObject purchase = new JSONObject();

            QonversionUtil.configure((Application) this.cordova.getActivity().getApplicationContext(), result -> {

                  // GG!
                  Qonversion.products(new QonversionProductsCallback() {
                    @Override
                    public void onSuccess(@NotNull Map<String, QProduct> map) {
                      Log.d("PRODUCTS===>", map.keySet().toString());

                      Log.d("test ===>", QonversionUtil.products.get(0).id);

                      QonversionUtil.purchase(QonversionUtil.products.get(0), activity, result -> {
                        if (result) {
                          purchase.put("success", 100);
                        } else {
                          purchase.put("success", 400);
                        }

                        callbackContext.success(purchase);
                      });

                    }

                    @Override
                    public void onError(@NotNull QonversionError qonversionError) {
                      JSONObject purchase = new JSONObject();
                      try {
                        purchase.put("success", -99);
                      } catch (JSONException e) {
                        e.printStackTrace();
                      }
                      callbackContext.success(purchase);
                    }
                  });

            });

            return true;
        }

        // trying to restore..
        if (action.equals("restore")) {

          // GG!
          Qonversion.products(new QonversionProductsCallback() {
            @Override
            public void onSuccess(@NotNull Map<String, QProduct> map) {

              QonversionUtil.restore(QonversionUtil.products.get(0), result -> {
                JSONObject restore = new JSONObject();
                if (result) {
                  restore.put("success", 100);
                  callbackContext.success(restore);
                } else {
                  restore.put("success", 400);
                  callbackContext.success(restore);
                }
              });

            }

            @Override
            public void onError(@NotNull QonversionError qonversionError) {
              JSONObject purchase = new JSONObject();
              try {
                purchase.put("success", -99);
              } catch (JSONException e) {
                e.printStackTrace();
              }
              callbackContext.success(purchase);
            }
          });


          return true;
        }

        return false;

    }

}
