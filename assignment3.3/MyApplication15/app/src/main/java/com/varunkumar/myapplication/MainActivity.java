package com.varunkumar.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File imgFile = new File("/sdcard/Images/test_image.jpg");

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }

    }

    public void loadImagefromGallery(View view){

        Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(myIntent,RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode==RESULT_LOAD_IMAGE && resultCode ==RESULT_OK && null!=data){

                Uri image = data.getData();
                String[] filePatchToColumn = {MediaStore.Images.Media.DATA};

                Cursor myCursor = getContentResolver().query(image,filePatchToColumn,null,null,null);
                myCursor.moveToFirst();

                int columnIndex = myCursor.getColumnIndex(filePatchToColumn[0]);
                String ImgDecode = myCursor.getString(columnIndex);
                ImageView imgV = (ImageView)findViewById(R.id.viewImg);
                imgV.setImageBitmap(BitmapFactory.decodeFile(ImgDecode));

            }
            else{
                Toast.makeText(getBaseContext(),"You Haven't Picked an Image",Toast.LENGTH_SHORT).show();

            }

        }
        catch(Exception e){
            Toast.makeText(getBaseContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();

        }
    }

}