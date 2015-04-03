package com.equipscan.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.equipscan.info.EquipmentInfo;
import com.example.ning.myapplicationsdfsdf.R;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity2Activity extends ActionBarActivity implements OnQRCodeReadListener {
String TITLE;

    //private TextView myTextView;
    private QRCodeReaderView mydecoderview;
    private View line_image;
    private boolean isReaded;
    private int intOut;


    LinearLayout layoutOfPopup; PopupWindow popupMessage;
    Button cancelButton, insidePopupButton;
    TextView titleText;
    TextView popupText;


    ArrayList<EquipmentInfo> myEquipments;
    EquipmentInfo currentEquipment;
    MenuItem btnSearch;


    ArrayList<String> IDArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        myEquipments = new ArrayList<EquipmentInfo>();
        IDArray = new ArrayList<String>();


        Bundle b = getIntent().getExtras();
        TITLE = b.getString("title");

        if(TITLE!= null ) {
            getSupportActionBar().setTitle(TITLE);
            if(TITLE.equalsIgnoreCase("领用扫描"))
            {
                intOut = 1;
            }
            else
            {

                intOut = 0;
            }
        }

        isReaded = false;
        line_image = (View) findViewById(R.id.red_line_image);

       // initCam();

        init();

        popupInit();

    }

    private void initCam() {

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
    }


    public void init() {

        //popupButton = (Button) findViewById(R.id.popupbutton);

        popupText = new TextView(this);
        titleText = new TextView(this);

        insidePopupButton = new Button(this);
        cancelButton = new Button(this);

        layoutOfPopup = new LinearLayout(this);
        LinearLayout btnlayoutOfPopup = new LinearLayout(this);


        cancelButton.setText("取消");
        insidePopupButton.setText("确定");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 100);
        lp.weight = 1;
        insidePopupButton.setLayoutParams(lp);
        cancelButton.setLayoutParams(lp);

        titleText.setText("检测到工具：");
       // popupText.setText("");

        titleText.setPadding(20, 20, 20, 10);
        popupText.setPadding(20, 0, 20, 20);

        layoutOfPopup.setBackgroundColor(0xFFFFFFFF);

        layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
        btnlayoutOfPopup.setOrientation(LinearLayout.HORIZONTAL);

        layoutOfPopup.addView(titleText);
        layoutOfPopup.addView(popupText);
        popupText.setMaxLines(3);
        popupText.setHeight(3* popupText.getLineHeight() + 20);

        btnlayoutOfPopup.addView(cancelButton);
        btnlayoutOfPopup.addView(insidePopupButton);

        layoutOfPopup.addView(btnlayoutOfPopup);

        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here

                popupMessage.dismiss();
                isReaded = false;
               // mydecoderview.getCameraManager().startPreview();
            }
        });

        insidePopupButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                //String[] EquipmentName = popupText.getText().toString().split("\r\n");
                //EquipmentInfo info = new EquipmentInfo(EquipmentName[0],EquipmentName[1], new Date(), intOut);

                if(currentEquipment != null){

                    currentEquipment.setCheckInDate(new Date());
                    currentEquipment.setInOut(intOut);
                    myEquipments.add(currentEquipment);

                    if(myEquipments.size() > 0)
                    {
                        btnSearch.setVisible(true);
                    }

                }

                popupMessage.dismiss();
                isReaded = false;
               // mydecoderview.getCameraManager().startPreview();
            }
        });

    }

    public void popupInit() {

       // popupButton.setOnClickListener(this);

       // insidePopupButton.setOnClickListener(this);

        popupMessage = new PopupWindow(layoutOfPopup,
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.MATCH_PARENT);

       // popupMessage = new PopupWindow()

        popupMessage.setContentView(layoutOfPopup);

    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {

       // mydecoderview.getCameraManager().stopPreview();
        if(isReaded) return;
        isReaded = true;

        String[] equipmentInfo = text.split("\n");
        String result;


        if(equipmentInfo.length < 2)
        {
            result = "找不到工具数据";

            insidePopupButton.setEnabled(false);
        }
        else
        {
            currentEquipment = new EquipmentInfo();
            currentEquipment.setID(equipmentInfo[0]);
            currentEquipment.setName(equipmentInfo[1]);
            result = "编号：" + equipmentInfo[0];
            result += "\n名称：" + equipmentInfo[1];

            if(!IDArray.contains(currentEquipment.getID()))
            {
                IDArray.add(currentEquipment.getID());
                titleText.setTextColor(0xFF000000);
                titleText.setText("检测到工具：");
                insidePopupButton.setEnabled(true);
            }else
            {

                titleText.setText("工具已扫描：");
                titleText.setTextColor(0xFFFF0000);
                insidePopupButton.setEnabled(false);
            }
        }

        popupText.setText(result);
        popupMessage.showAsDropDown(line_image, 0, 0);

    }


    // Called when your device have no camera
    @Override
    public void cameraNotFound() {

    }

    // Called when there's no QR codes in the camera preview image
    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    protected void onResume() {
        isReaded = false;
        super.onResume();
       // mydecoderview.getCameraManager().startPreview();



        titleText.setText("工具已扫描：");
        popupText.setText("12312");
        popupMessage.showAsDropDown(line_image, 0, 0);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity23, menu);

//        MenuItem item = menu.findItem(R.id.action_search);
//        if(item != null)
//            item.setVisible(false);


        btnSearch = menu.findItem(R.id.action_search);
        btnSearch.setVisible(false);

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


            Intent intent = new Intent(MainActivity2Activity.this, HistoryActiviry.class);

            intent.putParcelableArrayListExtra("stock_list", myEquipments);

            startActivity(intent);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
