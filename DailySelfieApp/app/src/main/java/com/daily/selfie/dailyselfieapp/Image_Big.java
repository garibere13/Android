package com.daily.selfie.dailyselfieapp;import android.graphics.Bitmap;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.widget.ImageView;public class Image_Big extends AppCompatActivity {    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_image_full);        if (getSupportActionBar() != null) {            getSupportActionBar().hide();        }        Bitmap imgBitmap = (Bitmap) getIntent().getParcelableExtra("IDFoto");        ImageView imageView = findViewById(R.id.imageView);        imageView.setImageBitmap(imgBitmap);    }}