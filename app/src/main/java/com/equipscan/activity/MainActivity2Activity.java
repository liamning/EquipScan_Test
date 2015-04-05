package com.equipscan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
    //private int intOut;

    PopupWindow popupMessage;

    Button cancelButton, insidePopupButton;
    TextView titleText;
    TextView popupText;


    ArrayList<EquipmentInfo> myEquipments;
    ArrayList<String> IDArray;
    EquipmentInfo currentEquipment;
    MenuItem btnSearch;


    CustomSpinnerAdapter type_adapter;
    CustomSpinnerAdapter karant_adapter;
    Spinner spinner;
    Spinner tvType;
    EditText txtComment;


    String publicComment;
    String publicApprover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Bundle b = getIntent().getExtras();
//        TITLE = b.getString("title");
//
//        if(TITLE!= null ) {
//            if(TITLE.equalsIgnoreCase("领用扫描"))
//            {
//                intOut = 1;
//            }
//            else
//            {
//
//                intOut = 0;
//            }
//        }

        initControl();
    }



    private void initControl(){

        getSupportActionBar().setTitle("扫描选项");
        setContentView(R.layout.apprcom);
        tvType = (Spinner) findViewById(R.id.tvType);
        type_adapter = new CustomSpinnerAdapter(this,
                R.layout.spinner_item, new String[]{"领用","返还"}, new String[]{"领用","返还"});
        tvType.setAdapter(type_adapter);
//        if(intOut==1)
//        {
//            tvType.setText("领用");
//        }
//        else
//        {
//            tvType.setText("返还");
//        }

        txtComment = (EditText) findViewById(R.id.txtComment);
        spinner = (Spinner) findViewById(R.id.spinner2);
        karant_adapter = new CustomSpinnerAdapter(this,
                R.layout.spinner_item, new String[]{"仓管员A","仓管员B","仓管员C"}, new String[]{"仓管员A","仓管员B","仓管员C"});
        spinner.setAdapter(karant_adapter);

        Button btnStartScan = (Button) findViewById(R.id.btnStartScan);

        btnStartScan.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                publicComment= txtComment.getText().toString();

                TextView tv = (TextView)spinner.getSelectedView();
                publicApprover= tv.getText().toString();

                callCam();
            }
        });
    }

    private void callCam(){

        setContentView(R.layout.activity_main_activity2);

        myEquipments = new ArrayList<EquipmentInfo>();
        IDArray = new ArrayList<String>();


        isReaded = false;
        line_image = (View) findViewById(R.id.red_line_image);

        initCam();

        initPopup();
    }

    private void initCam() {

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
    }

    public void initPopup() {

        popupText = new TextView(this);
        titleText = new TextView(this);

        insidePopupButton = new Button(this);
        cancelButton = new Button(this);


        LinearLayout layoutOfPopup = new LinearLayout(this);
        LinearLayout btnlayoutOfPopup = new LinearLayout(this);


        cancelButton.setText("取消");
        insidePopupButton.setText("确定");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 100);
        lp.weight = 1;
        insidePopupButton.setLayoutParams(lp);
        cancelButton.setLayoutParams(lp);

        titleText.setText("检测到工具：");

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

                popupMessage.dismiss();
                isReaded = false;
            }
        });

        insidePopupButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                if(currentEquipment != null){

                    currentEquipment.setCheckInDate(new Date());
                    //currentEquipment.setInOut(intOut);
                    currentEquipment.setInOut(tvType.getSelectedItemPosition());

                    myEquipments.add(currentEquipment);

                    if(myEquipments.size() > 0)
                    {
                        btnSearch.setVisible(true);
                    }

                }

                popupMessage.dismiss();
                isReaded = false;
            }
        });


        popupMessage = new PopupWindow(layoutOfPopup,
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.MATCH_PARENT);


        popupMessage.setContentView(layoutOfPopup);


//
//        popupComment = new PopupWindow(layoutOfPopup,
//                RadioGroup.LayoutParams.MATCH_PARENT,
//                RadioGroup.LayoutParams.MATCH_PARENT);
//
//
//        // Inflate the popup_layout.xml
//        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.scanlayout);
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layout = layoutInflater.inflate(R.layout.activity_scan_details, viewGroup);
//
//        popupComment.setContentView(layout);

    }


    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {

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

            result = "编号：" + equipmentInfo[0];
            result += "\n名称：" + equipmentInfo[1];

            if(!IDArray.contains(equipmentInfo[0]))
            {
                currentEquipment = new EquipmentInfo();
                currentEquipment.setID(equipmentInfo[0]);
                currentEquipment.setName(equipmentInfo[1]);
                currentEquipment.setApprover(publicApprover);
                currentEquipment.setRemarks(publicComment);


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
       // popupComment.showAsDropDown(line_image,0,0);

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
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity23, menu);

        btnSearch = menu.findItem(R.id.action_search);
        btnSearch.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
