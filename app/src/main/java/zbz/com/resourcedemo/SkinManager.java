package zbz.com.resourcedemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

/**
 * @author 张本志
 * @date 2019/1/31 上午11:51
 */
public class SkinManager {

    public static final String MIPMAP = "mipmap";
    public static final String DRAWABLE = "drawable";
    public static final String STRING = "string";
    public static final String COLOR = "color";

    private static Resources mSkinResource;

    public static Resources getSkinResource(Context context, String skinFilePath) {
        if (mSkinResource != null) {
            return mSkinResource;
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager, skinFilePath);
            Resources superRes = context.getResources();
            mSkinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            return mSkinResource;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据原项目中的图片id获取皮肤文件中的图片id
     *
     * @param context
     * @param skinResource
     * @param orginalResourceId
     * @param skinPackageName   皮肤包名
     * @param type              资源类型(mipmap,drawable,string,color)
     * @return
     */
    public static int getSkinResorceId(Context context, Resources skinResource, int orginalResourceId, String skinPackageName, String type) {
        //根据原资源文件id获取资源文件对应的名称
        String resourceEntryName = context.getResources().getResourceEntryName(orginalResourceId);
        return skinResource.getIdentifier(resourceEntryName, type, skinPackageName);
    }

    /**
     * 获取apk包名：
     *
     * @param context
     * @param apkFilePath
     * @return
     */
    public static String getPackageName(Context context, String apkFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkFilePath,
                PackageManager.GET_ACTIVITIES);
        return info.packageName;
    }

}