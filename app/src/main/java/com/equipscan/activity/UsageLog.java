package com.equipscan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.ning.myapplicationsdfsdf.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UsageLog extends ActionBarActivity {

    boolean cameraStart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_log);

        getSupportActionBar().setTitle("现场照相");
        init();
    }

    private void init(){
        dispatchTakePictureIntent();

    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.v("", ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));

                try {
                    openFileOutput("Captured.jpg", Context.MODE_WORLD_WRITEABLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // fos.close();
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {

            if(resultCode == RESULT_OK){

                //Bundle extras = data.getExtras();
                Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, null);//(Bitmap) extras.get("data");
                this.finishActivity(REQUEST_TAKE_PHOTO);

                scaleImage(imageBitmap);
            }
            cameraStart = true;
        }
    }

    private void scaleImage(Bitmap bitmap)
    {
        // Get the ImageView and its bitmap
        ImageView view = (ImageView) findViewById(R.id.imgScen);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        params.height =displaymetrics.widthPixels;
        params.width =displaymetrics.widthPixels;
        view.setLayoutParams(params);


       // Create a new bitmap and convert it to a format understood by the ImageView
       Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, view.getWidth(), view.getHeight(), null, true);

        if (bitmap.getWidth() >= bitmap.getHeight()){

            bitmap = Bitmap.createBitmap(
                    bitmap,
                    bitmap.getWidth()/2 - bitmap.getHeight()/2,
                    0,
                    bitmap.getHeight(),
                    bitmap.getHeight()
            );

        }else{

            bitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    bitmap.getHeight()/2 - bitmap.getWidth()/2,
                    bitmap.getWidth(),
                    bitmap.getWidth()
            );
        }

        // Apply the scaled bitmap
        view.setImageBitmap(bitmap);

    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {


        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


//Remove if exists, the file MUST be created using the lines below
        File f = new File(getFilesDir(), "Captured.jpg");
        f.delete();
//Create new file
        FileOutputStream fos = openFileOutput("Captured.jpg", Context.MODE_WORLD_WRITEABLE);
        fos.close();
//Get reference to the file
        //File f = new File(getFilesDir(), "Captured.jpg");



        if (storageDir.exists ())
            storageDir.delete();


        storageDir.mkdirs();
        // getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                //
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + f.getAbsolutePath();
        return f;
    }

    @Override
    protected void onResume(){
        if(cameraStart){
            ImageView image = (ImageView) findViewById(R.id.imgScen);
            if(image.getDrawable() == null)
                finish();
        }
        super.onResume();
    }
//
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        // Surface will be destroyed when we return, so stop the preview.
//        if (mCamera != null) {
//            // Call stopPreview() to stop updating the preview surface.
//            mCamera.stopPreview();
//        }
//    }
//
//    /**
//     * When this function returns, mCamera will be null.
//     */
//    private void stopPreviewAndFreeCamera() {
//
//        if (mCamera != null) {
//            // Call stopPreview() to stop updating the preview surface.
//            mCamera.stopPreview();
//
//            // Important: Call release() to release the camera for use by other
//            // applications. Applications should release the camera immediately
//            // during onPause() and re-open() it during onResume()).
//            mCamera.release();
//
//            mCamera = null;
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usage_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
