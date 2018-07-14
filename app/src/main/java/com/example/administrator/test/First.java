package com.example.administrator.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import Utils.QrcCodeUtils;

public class First extends Activity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        imageView=findViewById(R.id.iv_first);
        Bitmap mBitmap = QrcCodeUtils.createQRCodeBitmap("https://www.baidu.com", 480, 480);
        imageView.setImageBitmap(mBitmap);
    }
}
