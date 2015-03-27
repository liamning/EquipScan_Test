package com.equipscan.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.equipscan.info.EquipmentInfo;
import com.example.ning.myapplicationsdfsdf.R;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity23Activity extends ActionBarActivity {


    ArrayList<EquipmentInfo> myEquipments = new ArrayList<EquipmentInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity23);


        getSupportActionBar().setTitle("test");


       // this.invalidateOptionsMenu();


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity23, menu);

//        MenuItem item = menu.findItem(R.id.action_search);
//        if(item != null)
//            item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

//            Toast.makeText(
//                    MainActivity23Activity.this,
//                    "You Clicked : " + item.getTitle(),
//                    Toast.LENGTH_SHORT
//
//
//
//            ).show();


            prepareEquipment();


            Intent intent = new Intent(MainActivity23Activity.this, HistoryActiviry.class);

           intent.putExtra("user", new EquipmentInfo("", new Date()));
            intent.putParcelableArrayListExtra("stock_list", myEquipments);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void prepareEquipment() {
        EquipmentInfo info = new EquipmentInfo("Cable", new Date());
        myEquipments.add(info);
        info = new EquipmentInfo("Cable2", new Date());
        myEquipments.add(info);
        info = new EquipmentInfo("Cable3", new Date());
        myEquipments.add(info);
        info = new EquipmentInfo("Cable4", new Date());
        myEquipments.add(info);
        info = new EquipmentInfo("Cable5123", new Date());
        myEquipments.add(info);
    }
}
