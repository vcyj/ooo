package com.xwh.lib.corelib.ui;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import bodyfast.zerofasting.fastic.corelib.R;

public abstract class BaseActivity extends AppCompatActivity {

    public ImageView baseClose;
    public TextView baseTitle;
    public ImageView baseFinishImage;
    public TextView baseFinishTv;
    public RelativeLayout baseContentView;
    public TextView baseCloseTv;
    public View contentView;
    public RelativeLayout baseTitleLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        super.setContentView(R.layout.common_base_activity);
        baseCloseTv = (TextView) findViewById(R.id.base_close_tv);
        baseClose = (ImageView) findViewById(R.id.base_close);
        baseTitle = (TextView) findViewById(R.id.base_title);
        baseFinishImage = (ImageView) findViewById(R.id.base_finish_image);
        baseFinishTv = (TextView) findViewById(R.id.base_finish_tv);
        baseContentView = (RelativeLayout) findViewById(R.id.base_contentView);
        baseTitleLayout = (RelativeLayout) findViewById(R.id.baseTitleLayout);

    }

    LoadingDialog loadingDialog;

    protected void hindBackBtn() {
        baseClose.setVisibility(View.INVISIBLE);
    }

    protected LoadingDialog getLoadingDialog() {
        if (this.loadingDialog == null)
            this.loadingDialog = new LoadingDialog.Builder(this).setCancelable(true).create();
        return this.loadingDialog;
    }

    protected void showLoading() {
        getLoadingDialog().show();
    }

    protected void hideLoading() {
        getLoadingDialog().dismiss();
    }

    public void setTitleHide() {
        baseTitleLayout.setVisibility(View.GONE);
        //  ImmersionBar.with(this).titleBar(baseContentView).init();
    }

    public void setDefaultTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            baseTitle.setVisibility(View.INVISIBLE);
        } else {
            baseTitle.setVisibility(View.VISIBLE);
            baseTitle.setText(title);
        }

        baseClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        baseFinishImage.setVisibility(View.INVISIBLE);
    }

    /***
     * 设置内容区域
     *
     * @param resId
     *            资源文件ID
     */
    public void setContentLayout(int resId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT,
                WindowManager.LayoutParams.FILL_PARENT);
        contentView.setLayoutParams(layoutParams);
        contentView.setBackgroundDrawable(null);
        if (null != baseContentView) {
            baseContentView.addView(contentView);
        }
        setDefaultTitle("");
    }

    /**
     * 沉浸效果
     */
    public void windowSteep() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
         super.onDestroy();
    }



    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }

    public ImageView getBaseClose() {
        return baseClose;
    }

    public TextView getBaseTitle() {
        return baseTitle;
    }

    public ImageView getBaseFinishImage() {
        return baseFinishImage;
    }

    public TextView getBaseFinishTv() {
        return baseFinishTv;
    }

    public TextView getBaseCloseTv() {
        return baseCloseTv;
    }

    public SharedPreferences getSharePre() {
        return getSharedPreferences("app", Activity.MODE_PRIVATE);
    }

    public void setPageType() {

    }
}
