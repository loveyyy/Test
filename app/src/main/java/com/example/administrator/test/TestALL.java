package com.example.administrator.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import Utils.QrcCodeUtils;

public class TestALL extends Activity implements View.OnClickListener{
    private Button btn_creat_code,btn_readcode,btn_todraw;
    private ImageView imageView,iv_big;
    private Bitmap bitmap;
    private RelativeLayout rl;
    private TextView tv_result;
    private EditText et_needcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmain);
        imageView=findViewById(R.id.iv_code);
        imageView.setOnClickListener(this);
        iv_big=findViewById(R.id.iv_Big);
        rl=findViewById(R.id.Rl1);
        tv_result=findViewById(R.id.tv_result);
        et_needcode=findViewById(R.id.et_needcode);
        iv_big.setOnClickListener(this);
        iv_big.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Bitmap bm =((BitmapDrawable) ((ImageView) imageView).getDrawable()).getBitmap();
                int width = bm.getWidth();
                int height = bm.getHeight();
                int[] data = new int[width * height];
                bm.getPixels(data, 0, width, 0, 0, width, height);
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
                QRCodeReader reader = new QRCodeReader();
                Result re = null;
                try {
                    re = reader.decode(bitmap1);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (ChecksumException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
                if (re == null) {
                } else {
                iv_big.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                tv_result.setText(re.getText());
                }
                return false;
            }
        });
        btn_creat_code=findViewById(R.id.btn_creatcode);
        btn_readcode=findViewById(R.id.btn_readcode);
        btn_todraw=findViewById(R.id.btn_todraw);
        btn_creat_code.setOnClickListener(this);
        btn_readcode.setOnClickListener(this);
        btn_todraw.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_creatcode:
                if(!TextUtils.isEmpty(et_needcode.getText())){
                    bitmap= QrcCodeUtils.createQRCodeBitmap(et_needcode.getText().toString(),160,160);
                    imageView.setImageBitmap(bitmap);
                }else{
                    Toast.makeText(TestALL.this,"请输入信息",Toast.LENGTH_SHORT).show();
                }

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
            case R.id.iv_code:
                    iv_big.setImageBitmap(bitmap);
                    iv_big.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                break;
            case R.id.iv_Big:
                rl.setVisibility(View.VISIBLE);
                iv_big.setVisibility(View.GONE);
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
