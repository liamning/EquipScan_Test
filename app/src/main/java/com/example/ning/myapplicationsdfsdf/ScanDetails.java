package com.example.ning.myapplicationsdfsdf;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ning.info.EquipmentInfo;


public class ScanDetails extends ActionBarActivity {

    EquipmentInfo info = new EquipmentInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_details);


        Bundle b = getIntent().getExtras();

        if(b != null && b.containsKey("selectedEquipment")) {
            info = (EquipmentInfo)b.get("selectedEquipment");

            initEquipment();




        }


    }

    private void initEquipment(){
        TextView tvID = (TextView)findViewById(R.id.tvID);
        TextView tvName = (TextView)findViewById(R.id.tvName);
        TextView tvType = (TextView)findViewById(R.id.tvType);

        tvID.setText(info.getID());
        tvName.setText(info.getName());
        tvType.setText("工具返還");


        Spinner spinner = (Spinner) findViewById(R.id.spinner2);


// Create an ArrayAdapter using the string array and a default spinner layout


        ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"倉管員A","倉管員B"});
//
// Specify the layout to use when the list of choices appears
        //karant_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner



        spinner.setAdapter(karant_adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan_details, menu);
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
