package cordova.plugin.qonversion;

public class QonversionProduct {
    String id = "";
    String permissionId = "";

    public void initQProducts(String id, String permissionId) {
        this.id = id;
        this.permissionId = permissionId;
    }

    QonversionProduct(String id, String permissionId) {
        this.id = id;
        this.permissionId = permissionId;
    }
}
