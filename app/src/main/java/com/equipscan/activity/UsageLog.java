package com.equipscan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.ning.myapplicationsdfsdf.R;


public class UsageLog extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_log);

        init();
    }

    private void init(){
        dispatchTakePictureIntent();
        getSupportActionBar().setTitle("现场照相");

    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);

            ImageView image = (ImageView) findViewById(R.id.imgScen);
            image.setImageBitmap(imageBitmap);


            //  Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.checkin40);
            // notBuilder.setLargeIcon(largeIcon);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
            //image.setImageBitmap(largeIcon);


        }
    }


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
