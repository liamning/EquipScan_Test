package com.equipscan.model;

import android.content.Context;
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

    public void prepareDB(Context context)
    {
        db= context.openOrCreateDatabase("EquipmentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + "Equipment(ID VARCHAR,"
                + "name VARCHAR,"
                + "staffname VARCHAR,"
                + "approver VARCHAR,"
                + "Remarks VARCHAR,"
                + "checkinDate int,"
                + "inOut int);");
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


    public void insertEquipmentList(ArrayList<EquipmentInfo> myEquipments){

        EquipmentInfo info;
        for(int i=0; i< myEquipments.size();i ++){
            info = myEquipments.get(i);

            db.execSQL("INSERT INTO Equipment VALUES('"
                    + info.getID()
                    + "','"
                    + info.getName()
                    + "','"
                    + info.getStaffName()
                    + "','"
                    + info.getApprover()
                    + "','"
                    + info.getRemarks()
                    + "'," + info.getCheckInDate().getTime()
                    + "," + info.getInOut() +");");

        }
    }

    public void updateEquipment(EquipmentInfo info){

        db.execSQL("update Equipment set Remarks='"+info.getRemarks()
                +"' ,approver='"+info.getApprover()+"' where ID='"+info.getID()+"' and checkInDate="+ info.getCheckInDate().getTime() );


    }

    public void closeDB()
    {db.close();}
}
