package cordova.plugin.qonversion;

import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.dto.QPermission;

public class QonversionActivePermission {
    String id = "";
    QPermission permission = null;

    public void initQActivePermissions(String id, QPermission qPermission) {
        this.id = id;
        this.permission = qPermission;
    }
}
