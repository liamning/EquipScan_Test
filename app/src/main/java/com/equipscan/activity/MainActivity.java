package com.equipscan.activity;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
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

    List<EquipmentInfo> myEquipments = new ArrayList<EquipmentInfo>();

    private QRCodeReaderView mydecoderview;
    TransitionDrawable transition;
    private Menu menu;

    @Override
    protected void onResume() {

        super.onResume();
    //    if(transition != null)
       // transition.reverseTransition(250);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getActionBar().setTitle("Hello world App");
        getSupportActionBar().setTitle("工具管理");

        registerCLickEvent();
       // prepareEquipment();
        //setListView();
       // getView();
    }

//
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        menu.add(1, 1, 0, "Open the file");
//
//        menu.add(1, 2, 1, "Save the file");
//
//        menu.add(1, 3, 2, "Close the file");
//
//        return true;
//
//    }


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

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.scanoption, popup.getMenu());


                //PopupMenu popupMenu = new PopupMenu(mContext, v);
                //popupMenu.inflate(R.menu.album_overflow_menu);

                // Force icons to show
                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popup);
                    argTypes = new Class[] { boolean.class };
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                    // Possible exceptions are NoSuchMethodError and NoSuchFieldError
                    //
                    // In either case, an exception indicates something is wrong with the reflection code, or the
                    // structure of the PopupMenu class or its dependencies has changed.
                    //
                    // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
                    // but in the case that they do, we simply can't force icons to display, so log the error and
                    // show the menu normally.

                   // Log.w(TAG, "error forcing menu icons to show", e);
                }


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(
//                                MainActivity.this,
//                                "You Clicked : " + item.getTitle(),
//                                Toast.LENGTH_SHORT
//                        ).show();

                        Intent intent = new Intent(MainActivity.this, MainActivity2Activity.class);

                        intent.putExtra("title", item.getTitle());
                        startActivity(intent);

                        return true;
                    }
                });

                popup.show(); //showing popup menu

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


                EquipmentInfo info = new EquipmentInfo();
                info.setID("A0001");
                info.setName("防电手套");
                info.setInOut(1);
                info.setCheckInDate(new Date());

                Intent intent = new Intent(MainActivity.this, ScanDetails.class);
                intent.putExtra("selectedEquipment", info);
                startActivity(intent);

            }


        });

        myLayout =  findViewById( R.id.tvScenHistory ); // root View id from that link
        // View myView = myLayout.findViewById( R.id.scanView );
        myLayout.setOnClickListener(new View.OnClickListener()
        {

            // private boolean stateChanged;
            // Drawable lastColor;

            public void onClick(View view) {

            }


        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;
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
