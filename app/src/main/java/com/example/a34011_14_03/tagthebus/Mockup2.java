package com.example.a34011_14_03.tagthebus;

import android.content.Intent;
import android.graphics.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Mockup2 extends AppCompatActivity implements View.OnClickListener{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mockup2);
    }
        @Override
        public void onClick(View v) {
            Intent cameraintent = new Intent("android.media.action.IMAGE_CAPTURE");
            this.startActivity(cameraintent);
        }
    }
