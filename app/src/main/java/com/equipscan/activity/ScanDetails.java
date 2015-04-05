package com.equipscan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.equipscan.info.EquipmentInfo;
import com.equipscan.model.Equipment;
import com.example.ning.myapplicationsdfsdf.R;


public class ScanDetails extends ActionBarActivity {

    EquipmentInfo info = new EquipmentInfo();
    CustomSpinnerAdapter karant_adapter;
    Spinner spinner;
    EditText txtComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_details);

        getSupportActionBar().setTitle("扫描详情");

        initControl();
        initEquipment();

    }

    private void initControl(){

        TextView tvType = (TextView) findViewById(R.id.tvType);
        txtComment = (EditText) findViewById(R.id.txtComment);
        txtComment.setTextColor(tvType.getTextColors());
    }

    private void initEquipment(){

        Bundle b = getIntent().getExtras();


        if(b != null && b.containsKey("selectedEquipment")) {
            info = (EquipmentInfo)b.get("selectedEquipment");

            TextView tvID = (TextView)findViewById(R.id.tvID);
            TextView tvName = (TextView)findViewById(R.id.tvName);
            TextView tvType = (TextView)findViewById(R.id.tvType);

            tvID.setText(info.getID());
            tvName.setText(info.getName());
            String[] typeDesc = new String[]{ "工具返還","工具领用"};
            tvType.setText(typeDesc[info.getInOut()]);


            spinner = (Spinner) findViewById(R.id.spinner2);
            karant_adapter = new CustomSpinnerAdapter(this,
                    R.layout.spinner_item, new String[]{"仓管员A","仓管员B","仓管员C"}, new String[]{"仓管员A","仓管员B","仓管员C"});
            spinner.setAdapter(karant_adapter);

            int spinnerPostion = karant_adapter.getPosition(info.getApprover());
            spinner.setSelection(spinnerPostion);

            txtComment.setText(info.getRemarks());


            Button btnStartScan = (Button) findViewById(R.id.btnStartScan);

            btnStartScan.setOnClickListener(new Button.OnClickListener() {

                public void onClick(View v) {

                    TextView tv = (TextView)spinner.getSelectedView();
                    info.setApprover(tv.getText().toString());
                    info.setRemarks(txtComment.getText().toString());

                    Equipment equipment = new Equipment();
                    equipment.prepareDB(getApplicationContext());
                    equipment.updateEquipment(info);
                    equipment.closeDB();

                    showMessage("Success", "保存成功");;
                }
            });
        }


    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_scan_details, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if (id == R.id.action_search) {
//
//            TextView tv = (TextView)spinner.getSelectedView();
//            info.setApprover(tv.getText().toString());
//            info.setRemarks(txtComment.getText().toString());
//
//            Equipment equipment = new Equipment();
//            equipment.prepareDB(this);
//            equipment.updateEquipment(info);
//            equipment.closeDB();
//
//            showMessage("Success", "保存成功");
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void showMessage(String title,String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public class CustomSpinnerAdapter extends ArrayAdapter<String> {

        Context mContext;
        int mTextViewResourceId;
        String[] mObjects;
        String[] mShortNameObjects;

        public CustomSpinnerAdapter(Context context, int textViewResourceId,
                                    String[] objects, String[] shortNameObjects) {
            super(context, textViewResourceId, objects);
            mContext = context;
            mTextViewResourceId = textViewResourceId;
            mObjects = objects;
            mShortNameObjects = shortNameObjects;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            TextView row = (TextView) inflater.inflate(mTextViewResourceId, parent, false);
            row.setGravity(1);
            row.setPadding(20,20,20,20);
            row.setText(mObjects[position]);

            return row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           // Spinner spinner = (Spinner) parent.findViewById(R.id.spinner2);
            TextView row = (TextView) inflater.inflate(mTextViewResourceId, parent, false);

            row.setText(mShortNameObjects[position]);

            return row;
        }
    }
}
