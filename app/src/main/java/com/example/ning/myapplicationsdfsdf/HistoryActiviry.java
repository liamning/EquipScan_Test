package com.example.ning.myapplicationsdfsdf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ning.info.EquipmentInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;

public class HistoryActiviry extends ActionBarActivity {

    ArrayList<EquipmentInfo> myEquipments = new ArrayList<EquipmentInfo>();
    Boolean isTmp;
    SQLiteDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_activiry);

        Bundle b = getIntent().getExtras();

        if(b != null && b.containsKey("stock_list")) {
            myEquipments = b.getParcelableArrayList("stock_list");

            getSupportActionBar().setTitle("扫描结果");

            isTmp = true;
        }
        else {
            prepareEquipment();

            getSupportActionBar().setTitle("扫描记录");

            isTmp = false;
        }
        setListView();

       // prepareDB();


    }

    private void prepareDB()
    {

        db=openOrCreateDatabase("EquipmentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Equipment(ID VARCHAR,name VARCHAR,checkinDate int,inOut int);");
    }

    private void setListView() {
          ListView equipmentListView = (ListView)findViewById(R.id.myList);
          ArrayAdapter<EquipmentInfo> adapter = new MyListAdapter();
        equipmentListView.setAdapter(adapter);

          equipmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
          public void onItemClick(AdapterView<?> parent, View view, int position,
             long id) {

              Intent intent = new Intent(HistoryActiviry.this, ScanDetails.class);
//
//test
//
//              String message = "abc";
//                intent.putExtra("title", message);

                 intent.putExtra("selectedEquipment", myEquipments.get(position));
                startActivity(intent);

          }

          });

    }

    private void prepareEquipment() {

        prepareDB();
        EquipmentInfo info;
        Cursor c=db.rawQuery("SELECT * FROM Equipment order by CheckinDate desc", null);
        if(c.moveToFirst())
        {
            do{
                info = new EquipmentInfo();

                info.setID(c.getString(0));
                info.setName(c.getString(1));
                info.setCheckInDate(new Date(c.getLong(2)));
                info.setInOut(c.getInt(3));

                myEquipments.add(info);

            }while (c.moveToNext());
        }
        else
        {
            showMessage("错误", "你没有扫描记录");
        }

        db.close();

    }

    private class MyListAdapter extends ArrayAdapter<EquipmentInfo> {

        public MyListAdapter() {
            super(HistoryActiviry.this, R.layout.layout, myEquipments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null)
            {
                itemView = getLayoutInflater().inflate( R.layout.layout, parent, false);
            }

            EquipmentInfo info = myEquipments.get(position);

            TextView nameView = (TextView)itemView.findViewById(R.id.textView);
            TextView dateView = (TextView)itemView.findViewById(R.id.textView2);
            ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView);

            if(info.getInOut() == 1)

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.out60));
            else

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.in60));

            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = sdf.format(info.getCheckInDate());

            nameView.setText(info.getName() + " (" + info.getID() + ")");
            dateView.setText(strDate); //sdf1.applyPattern("dd/MM/yyyy HH:mm:ss.SS");

            return itemView;
            // return super.getView(position, convertView, parent);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_activiry, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        if(!isTmp)
            item.setVisible(false);

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

            prepareDB();

            EquipmentInfo info;
            for(int i=0; i< this.myEquipments.size();i ++){
                info = this.myEquipments.get(i);

                db.execSQL("INSERT INTO Equipment VALUES('"
                        + info.getID()
                        + "','"
                        + info.getName()
                        + "'," + info.getCheckInDate().getTime()
                        + "," + info.getInOut() +");");

            }

            db.close();

            showMessage("Success", "保存成功");


        }

        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String title,String message)
    {

//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage(message);
//        builder1.setCancelable(true);
//        builder1.setPositiveButton("Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        builder1.setNegativeButton("No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//        return;


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
}
