package com.example.a34011_14_03.tagthebus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.format;
import static com.example.a34011_14_03.tagthebus.R.id.imageView;

public class CameraActivity extends AppCompatActivity {

    private Button takePictureButton;
    private ImageView imageview;
    private File file;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePictureButton = (Button) findViewById(R.id.bouttonCam);
        imageview = (ImageView) findViewById(imageView);
        Button takePictureButton = (Button)findViewById(R.id.bouttonCam);
        takePictureButton.setOnClickListener((View.OnClickListener)this);}

        public void onClick(View view){
        dispatchTakePictureIntent();
    }
        /** function to get the Thumbnail*/
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageview.setImageBitmap(imageBitmap);
        }
    }
    String CurrentPhotoPath;
    /** Create an image file name*/
    private File createImageFile()throws IOException {
    DateFormat timeStamp = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /*prefix*/
                ".jpg",        /*suffix*/
                storageDir     /*directory*/
        );
        CurrentPhotoPath = image.getAbsolutePath();
        galleryAddPic();
        return image;

    }
    static final int REQUEST_TAKE_PHOTO = 1;


    /** function to take a picture*/
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            //Create the file where the photo should go
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }
            catch (IOException ex){
                //An Error has come...
            }
            /**continue only if the file was successfully created*/
            if (photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);

            }
        }
    }
    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(CurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}






