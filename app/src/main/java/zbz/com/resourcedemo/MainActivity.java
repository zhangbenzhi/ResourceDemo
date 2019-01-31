package zbz.com.resourcedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private SkinManager mSkinManager;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSkinManager = new SkinManager();
        iv = findViewById(R.id.iv);
        checkNewSkin();
        /*LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });*/
    }

    @SuppressLint("ResourceType")
    private void checkNewSkin() {
        String skinDir = getExternalCacheDir().getAbsolutePath();
        File file = new File(skinDir);
        File[] skinFile = file.listFiles();
        if (skinFile == null || skinFile.length == 0) {
            return;
        }
        String skinApkFilePath = skinFile[0].getAbsolutePath();
        Resources skinResource = SkinManager.getSkinResource(this, skinApkFilePath);
        if (skinResource != null) {
            int skinResourceId = SkinManager.getSkinResorceId(this,
                    skinResource,
                    R.mipmap.ic_launcher,
                    SkinManager.getPackageName(this, skinApkFilePath),
                    SkinManager.MIPMAP);
            iv.setImageDrawable(skinResource.getDrawable(skinResourceId));
        }
    }
}
