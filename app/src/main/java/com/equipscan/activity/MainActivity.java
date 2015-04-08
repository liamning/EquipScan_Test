package com.equipscan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.TransitionDrawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.equipscan.info.EquipmentInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.ning.myapplicationsdfsdf.R;

public class MainActivity extends ActionBarActivity {

    TransitionDrawable transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("工具管理");


        registerCLickEvent();

    }

    private void registerCLickEvent(){
        View myLayout =  findViewById( R.id.tvScan  ); // root View id from that link
        // View myView = myLayout.findViewById( R.id.scanView );
        myLayout.setOnClickListener(new View.OnClickListener()
        {
            // private boolean stateChanged;
            // Drawable lastColor;

            public void onClick(View view) {

                transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(250);
                transition.reverseTransition(250);
                transition = null;

                Intent intent = new Intent(MainActivity.this, MainActivity2Activity.class);
                startActivity(intent);


//                //Creating the instance of PopupMenu
//                PopupMenu popup = new PopupMenu(MainActivity.this, view);
//                //Inflating the Popup using xml file
//                popup.getMenuInflater()
//                        .inflate(R.menu.scanoption, popup.getMenu());
//
//
//                //PopupMenu popupMenu = new PopupMenu(mContext, v);
//                //popupMenu.inflate(R.menu.album_overflow_menu);
//
//                // Force icons to show
//                Object menuHelper;
//                Class[] argTypes;
//                try {
//                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
//                    fMenuHelper.setAccessible(true);
//                    menuHelper = fMenuHelper.get(popup);
//                    argTypes = new Class[] { boolean.class };
//                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
//                } catch (Exception e) {
//                    // Possible exceptions are NoSuchMethodError and NoSuchFieldError
//                    //
//                    // In either case, an exception indicates something is wrong with the reflection code, or the
//                    // structure of the PopupMenu class or its dependencies has changed.
//                    //
//                    // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
//                    // but in the case that they do, we simply can't force icons to display, so log the error and
//                    // show the menu normally.
//
//                   // Log.w(TAG, "error forcing menu icons to show", e);
//                }
//
//
//                //registering popup with OnMenuItemClickListener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
////                        Toast.makeText(
////                                MainActivity.this,
////                                "You Clicked : " + item.getTitle(),
////                                Toast.LENGTH_SHORT
////                        ).show();
//
//                        Intent intent = new Intent(MainActivity.this, MainActivity2Activity.class);
//
//                        intent.putExtra("title", item.getTitle());
//                        startActivity(intent);
//
//                        return true;
//                    }
//                });
//
//                popup.show(); //showing popup menu

            }


        });

        myLayout =  findViewById( R.id.tvScanHistory ); // root View id from that link
        // View myView = myLayout.findViewById( R.id.scanView );
        myLayout.setOnClickListener(new View.OnClickListener()
        {

            // private boolean stateChanged;
            // Drawable lastColor;

            public void onClick(View view) {

                transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(250);
                transition.reverseTransition(250);
                transition = null;

                Intent intent = new Intent(MainActivity.this, HistoryActiviry.class);
                startActivity(intent);
            }


        });

        myLayout =  findViewById( R.id.tvScen ); // root View id from that link
        // View myView = myLayout.findViewById( R.id.scanView );
        myLayout.setOnClickListener(new View.OnClickListener()
        {

            // private boolean stateChanged;
            // Drawable lastColor;

            public void onClick(View view) {

                transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(250);
                transition.reverseTransition(250);
                transition = null;


                Intent intent = new Intent(MainActivity.this, UsageLog.class);
                startActivity(intent);

            }


        });

        myLayout =  findViewById( R.id.tvScenHistory );
        myLayout.setOnClickListener(new View.OnClickListener()
        {

            // private boolean stateChanged;
            // Drawable lastColor;

            public void onClick(View view) {
                transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(250);
                transition.reverseTransition(250);
                transition = null;

                Intent intent = new Intent(MainActivity.this, ScenHistoryActivity.class);
                startActivity(intent);
            }


        });



        myLayout =  findViewById( R.id.tvSync );
        myLayout.setOnClickListener(new View.OnClickListener()
        {

            // private boolean stateChanged;
            // Drawable lastColor;

            public void onClick(View view) {
                transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(250);
                transition.reverseTransition(250);
                transition = null;

                Intent intent = new Intent(MainActivity.this, SyncDetailsActivity.class);
                startActivity(intent);
            }


        });


        myLayout =  findViewById( R.id.tvHelper ); // root View id from that link
        // View myView = myLayout.findViewById( R.id.scanView );
        myLayout.setOnClickListener(new View.OnClickListener()
        {

            // private boolean stateChanged;
            // Drawable lastColor;

            public void onClick(View view) {
                transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(250);
                transition.reverseTransition(250);
                transition = null;

                Intent intent = new Intent(MainActivity.this, HelperActivity.class);
                startActivity(intent);
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
      //      return true;
      //  }

        return super.onOptionsItemSelected(item);
    }

}
