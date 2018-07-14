package com.example.administrator.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import Utils.QrcCodeUtils;

public class TestALL extends Activity implements View.OnClickListener{
    private Button btn_creat_code,btn_readcode,btn_todraw;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmain);
        imageView=findViewById(R.id.iv_code);
        btn_creat_code=findViewById(R.id.btn_creatcode);
        btn_readcode=findViewById(R.id.btn_readcode);
        btn_todraw=findViewById(R.id.btn_todraw);
        btn_creat_code.setOnClickListener(this);
        btn_readcode.setOnClickListener(this);
        btn_todraw.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_creatcode:
                Bitmap bitmap= QrcCodeUtils.createQRCodeBitmap("看片请加微信18327646670",160,160);
                imageView.setImageBitmap(bitmap);
                break;

            case R.id.btn_readcode:
                Intent intent=new Intent();
                intent.setClass(TestALL.this,ReadCodeActivity.class);
                startActivityForResult(intent,0);
                break;


            case R.id.btn_todraw:
                Intent intent1=new Intent(TestALL.this,TestChart.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==0){
            if(!TextUtils.isEmpty(data.getStringExtra("res"))){
                Toast.makeText(this, data.getStringExtra("res"), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "识别失败", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "识别失败", Toast.LENGTH_LONG).show();
        }

    }
}
