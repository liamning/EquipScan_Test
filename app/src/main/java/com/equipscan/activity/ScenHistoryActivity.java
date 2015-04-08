package com.equipscan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.equipscan.info.EquipmentInfo;
import com.equipscan.info.ScenInfo;
import com.equipscan.model.Equipment;
import com.equipscan.model.Scen;
import com.example.ning.myapplicationsdfsdf.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScenHistoryActivity extends ActionBarActivity {

    ArrayList<ScenInfo> scenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scen_history);
        getSupportActionBar().setTitle("现场记录");
//
//        prepareScen();
//        setListView();
    }

    protected void onResume()
    {
        super.onResume();

        prepareScen();
        setListView();
    }


    private void setListView() {


        ListView equipmentListView = (ListView)findViewById(R.id.myList);
        ArrayAdapter<ScenInfo> adapter = new MyListAdapter();
        equipmentListView.setAdapter(adapter);

        equipmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(ScenHistoryActivity.this, ScenDetailsActivity.class);


                intent.putExtra("selectedScen", scenList.get(position));
                startActivity(intent);

            }

        });

    }

    private void prepareScen() {

        Scen handler = new Scen();

        handler.prepareDB(this);
        scenList = handler.getScenList(100);
        if(scenList.size() == 0)
        {
            showMessage("错误", "你没有照相记录");
        }

        handler.closeDB();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scen_history, menu);
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

    private class MyListAdapter extends ArrayAdapter<ScenInfo> {

        public MyListAdapter() {
            super(ScenHistoryActivity.this, R.layout.layout, scenList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null)
            {
                itemView = getLayoutInflater().inflate( R.layout.layout, parent, false);
            }

            ScenInfo info = scenList.get(position);

            TextView nameView = (TextView)itemView.findViewById(R.id.textView);
            TextView dateView = (TextView)itemView.findViewById(R.id.textView2);
            ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView);

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.capturehistory100));

            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = sdf.format(info.getCreateDate());

            nameView.setText(info.getRemarks());
            dateView.setText(strDate);

            return itemView;
        }
    }

}
