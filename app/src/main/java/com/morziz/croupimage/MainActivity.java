package com.morziz.croupimage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    CropPicker cropPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        ((Button) findViewById(R.id.pick_image)).setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CropPicker.CHOOSE_PHOTO_INTENT) {
                if (data != null && data.getData() != null) {
                    cropPicker.handleGalleryResult(data);
                } else {
                    cropPicker.handleCameraResult(Uri.fromFile(CropPicker.output));
                }
            } else if (requestCode == CropPicker.SELECTED_IMG_CROP) {
                File imgFile = new File(CropPicker.getPath(MainActivity.this,cropPicker.getCropImageUrl()));
                Glide.with(MainActivity.this).load(imgFile).asBitmap().into(imageView);
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CropPicker.SELECT_PICTURE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                cropPicker.showImagePickerDialog(MainActivity.this);
        }
    }

    @Override
    public void onClick(View view) {
        cropPicker = new CropPicker(this);
    }
}
