package com.equipscan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.equipscan.info.EquipmentInfo;
import com.equipscan.info.ScenInfo;
import com.equipscan.model.Equipment;
import com.equipscan.model.Scen;
import com.example.ning.myapplicationsdfsdf.R;

public class ScenDetailsActivity extends ActionBarActivity {

    EditText txtComment;

    ScenInfo info = new ScenInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scen_details);

        getSupportActionBar().setTitle("现场详情");
        initControl();
        initEquipment();

    }

    private void initControl(){

        txtComment = (EditText) findViewById(R.id.txtRemarks);
    }

    private void initEquipment(){

        Bundle b = getIntent().getExtras();


        if(b != null && b.containsKey("selectedScen")) {
            info = (ScenInfo)b.get("selectedScen");


            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            ImageView imageView = (ImageView) findViewById(R.id.imgScen);
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) imageView.getLayoutParams();
            params.height =displaymetrics.widthPixels * 3/4;
            params.width =displaymetrics.widthPixels;
            imageView.setLayoutParams(params);

            Scen handler = new Scen();
            handler.prepareDB(this);
            Bitmap tmpBitMap = handler.getImage(info.getID(), this);


            if ((float)tmpBitMap.getWidth()/ (float)tmpBitMap.getHeight() > 0.75){

                tmpBitMap = Bitmap.createBitmap(
                        tmpBitMap,
                        tmpBitMap.getWidth()/2 - tmpBitMap.getHeight()/2,
                        0,
                        tmpBitMap.getHeight()*4/3,
                        tmpBitMap.getHeight()
                );

            }else{

                tmpBitMap = Bitmap.createBitmap(
                        tmpBitMap,
                        0,
                        tmpBitMap.getHeight()/2 - tmpBitMap.getWidth()/2,
                        tmpBitMap.getWidth(),
                        tmpBitMap.getWidth()*3/4
                );
            }

            imageView.setImageBitmap(tmpBitMap);
            handler.closeDB();

            txtComment.setText(info.getRemarks());


            Button btnStartScan = (Button) findViewById(R.id.btnSave);

            btnStartScan.setOnClickListener(new Button.OnClickListener() {

                public void onClick(View v) {
//
//                    TextView tv = (TextView)spinner.getSelectedView();
//                    info.setApprover(tv.getText().toString());
//                    info.setRemarks(txtComment.getText().toString());
//
//                    Equipment equipment = new Equipment();
//                    equipment.prepareDB(getApplicationContext());
//                    equipment.updateEquipment(info);
//                    equipment.closeDB();

                    Scen handler = new Scen();
                    info.setRemarks(txtComment.getText().toString());
                    handler.prepareDB(getApplicationContext());
                    handler.updateScen(info);
                    handler.closeDB();

                    showMessage("Success", "保存成功");;
                }
            });
        }


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scen_details, menu);
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
