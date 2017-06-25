package com.gulbalasalamov.cameraappdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class CameraActivity extends AppCompatActivity {

    public static int CAMERA_PHOTO_REQUEST = 101;
    public static int CAMERA_VIDEO_REQUEST = 102;

    Button btn_photo, btn_video;
    ImageView iv;
    VideoView vw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btn_photo = (Button) findViewById(R.id.button_photo);
        btn_video = (Button) findViewById(R.id.button_video);
        iv = (ImageView) findViewById(R.id.imagaview);
        vw = (VideoView) findViewById(R.id.videoview);

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoIntent, CAMERA_PHOTO_REQUEST);
            }
        });

        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); //yuksek kalite video
                startActivityForResult(videoIntent, CAMERA_VIDEO_REQUEST);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            btn_video.setEnabled(false);
            btn_photo.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap bitmapImage = (Bitmap) extras.get("data");
                iv.setImageBitmap(bitmapImage);
            }
        }
        if (requestCode == CAMERA_VIDEO_REQUEST) {
            if (resultCode == RESULT_OK) {
                vw.setVideoURI(data.getData());
                vw.setMediaController(new MediaController(this));
                vw.requestFocus();
                vw.start();
            }
        }
    }
}
