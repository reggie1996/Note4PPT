package com.reggie.note4ppt.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.reggie.note4ppt.R;

import java.io.File;

/**
 * 单张ppt编辑
 */
public class SinglePPTActivity extends Activity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;//拍照常量
    public static final int CROP_PHOTO = 2;//裁剪常量
    public static final int EDIT_SUCCESS = 3;//编辑成功
    private Uri imageUri;//临时存放图片的Uri
    private String words;

    private ImageView mIvPpt;
    private EditText mEtWords;
    private int pos;

    /**
     * OK
     */
    private Button mBtOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pptedit);
        initView();

    }

    private void initView() {

        Intent intent = getIntent();
        pos = intent.getIntExtra("position",-1);
        mIvPpt = (ImageView) findViewById(R.id.iv_ppt);
        mIvPpt.setOnClickListener(this);
        mEtWords = (EditText) findViewById(R.id.et_words);
        mBtOk = (Button) findViewById(R.id.bt_ok);
        mBtOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_ppt:
                setmIvPpt();
                break;
            case R.id.bt_ok:
                setmBtOk();
                break;
        }
    }


    private void setmIvPpt() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "output_image_" + System.currentTimeMillis() + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();//如果文件存在，则删除
            }
            outputImage.createNewFile();//创建一个新文件
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageUri = Uri.fromFile(outputImage);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    /**
     * 拍照和剪裁的回调函数，如果拍照完成，则启动剪裁程序 将原照片进行剪裁
     * 剪裁完成，则通知列表内容更新，完成照片修改
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {

                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);//启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    Glide.with(this).load(imageUri).placeholder(R.mipmap.aaa).into(mIvPpt);
                }
                break;
            default:
                break;
        }
    }


    private void setmBtOk(){
        words = mEtWords.getText().toString();
        String _imageUri = imageUri.toString();
        Intent intent = new Intent();
        intent.putExtra("imageUri",_imageUri);
        intent.putExtra("words",words);
        intent.putExtra("position",pos);
        setResult(EDIT_SUCCESS,intent);
        finish();
    }

}
