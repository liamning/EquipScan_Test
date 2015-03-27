package com.equipscan.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.equipscan.info.EquipmentInfo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ning on 3/21/2015.
 */
public class Equipment {

    SQLiteDatabase db;


    private void prepareDB()
    {

        //db= SQLiteDatabase.openOrCreateDatabase("EquipmentDB", Context.MODE_PRIVATE, null);
       // db= SQLiteDatabase.openOrCreateDatabase()
        //db.execSQL("CREATE TABLE IF NOT EXISTS Equipment(ID VARCHAR,name VARCHAR,checkinDate int,inOut int);");
    }


    public ArrayList<EquipmentInfo> getEquipmentList(int top){

        ArrayList<EquipmentInfo> myEquipments = new ArrayList<EquipmentInfo>();

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

        db.close();
        return null;
    }


}
