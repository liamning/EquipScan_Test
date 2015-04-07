package com.equipscan.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.equipscan.com.equipscan.utility.Utility;
import com.equipscan.info.EquipmentInfo;
import com.equipscan.info.ScenInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ning on 4/6/2015.
 */
public class Scen {

    SQLiteDatabase db;
    String TABLE_NAME = "Scen";
    public void prepareDB(Context context)
    {
        if(db == null){

            db= context.openOrCreateDatabase("EquipmentDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE "+TABLE_NAME+" IF NOT EXISTS "
                    + TABLE_NAME+"(ID INTEGER primary key autoincrement,"
                    + "ScenImage BLOB,"
                    + "Remarks VARCHAR,"
                    + "CreateDate VARCHAR);");
        }
    }


    public ArrayList<EquipmentInfo> getEquipmentList(int top){

        ArrayList<EquipmentInfo> myEquipments = new ArrayList<EquipmentInfo>();

        EquipmentInfo info;
        Cursor c=db.rawQuery("SELECT ID, name, checkindate, inout, Remarks , approver "
                +"FROM Equipment order by CheckinDate desc", null);


        if(c.moveToFirst())
        {
            int count = 0;
            do{
                info = new EquipmentInfo();

                info.setID(c.getString(0));
                info.setName(c.getString(1));
                info.setCheckInDate(new Date(c.getLong(2)));
                info.setInOut(c.getInt(3));
                info.setRemarks(c.getString(4));
                info.setApprover(c.getString(5));

                myEquipments.add(info);

                count++;
            }while (c.moveToNext() && (top == 0 || count < top));


        }

        db.close();

        return myEquipments;
    }


    public void insertScen(ScenInfo info){

        SQLiteDatabase myDb = db;
        byte[] byteImage1 = null;
        String s = myDb.getPath();

        ContentValues newValues = new ContentValues();
        newValues.put("ScenImage", Utility.getBytes(info.getScenImage()));
        newValues.put("Remarks", info.getRemarks());
        newValues.put("CreateDate", info.getCreateDate().getTime());

        long ret = myDb.insert(TABLE_NAME, null, newValues);

    }

    public void updateEquipment(EquipmentInfo info){

        db.execSQL("update Equipment set Remarks='"+info.getRemarks()
                +"' ,approver='"+info.getApprover()+"' where ID='"+info.getID()+"' and checkInDate="+ info.getCheckInDate().getTime() );


    }

    public void closeDB()
    {
        if(db != null)
            db.close();
    }

}
