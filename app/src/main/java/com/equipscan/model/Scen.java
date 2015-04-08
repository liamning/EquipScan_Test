package com.equipscan.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;

import com.equipscan.utility.Utility;
import com.equipscan.info.EquipmentInfo;
import com.equipscan.info.ScenInfo;

import java.io.File;
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
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_NAME+" (ID INTEGER primary key autoincrement,"
                    + "ScenImage VARCHAR,"
                    + "Remarks VARCHAR,"
                    + "CreateDate VARCHAR);");
        }
    }

    public ArrayList<ScenInfo> getScenList(int top){

        ArrayList<ScenInfo> list = new ArrayList<ScenInfo>();

        ScenInfo info;
        Cursor c=db.rawQuery("SELECT ID, Remarks, CreateDate  "
                +"FROM "+TABLE_NAME+" order by ID desc", null);


        if(c.moveToFirst())
        {
            int count = 0;
            do{
                info = new ScenInfo();
                info.setID(c.getInt(0));
                info.setRemarks(c.getString(1));
                info.setCreateDate(new Date(c.getLong(2)));

                list.add(info);

                count++;
            }while (c.moveToNext() && (top == 0 || count < top));

        }

        return list;
    }

    public void insertScen(ScenInfo info){

        SQLiteDatabase myDb = db;

        ContentValues newValues = new ContentValues();
        newValues.put("ScenImage", info.getScenImage());
        newValues.put("Remarks", info.getRemarks());
        newValues.put("CreateDate", info.getCreateDate().getTime());

        myDb.insert(TABLE_NAME, null, newValues);

    }

    public void updateScen(ScenInfo info){

        db.execSQL("update "+TABLE_NAME+" set Remarks='"+info.getRemarks()
                +"' where ID="+info.getID());

    }
//
//    public Employee retriveEmpDetails() throws SQLException {
//        Cursor cur = mDb.query(true, EMPLOYEES_TABLE, new String[] { EMP_PHOTO,
//                EMP_NAME, EMP_AGE }, null, null, null, null, null, null);
//        if (cur.moveToFirst()) {
//            byte[] blob = cur.getBlob(cur.getColumnIndex(EMP_PHOTO));
//            String name = cur.getString(cur.getColumnIndex(EMP_NAME));
//            int age = cur.getInt(cur.getColumnIndex(EMP_AGE));
//            cur.close();
//            return new Employee(Utility.getPhoto(blob), name, age);
//        }
//        cur.close();
//        return null;
//    }
//
    public Bitmap getImage(Integer ID, Context context){

        Cursor c = db.rawQuery("SELECT ScenImage, ID "
                + " FROM "+ TABLE_NAME
                + " where ID=" + ID , null);

        if(c.moveToFirst())
        {
            do{

                String image = c.getString(0);
                Uri imageUri = Uri.fromFile(new File(image));
                return Utility.getBitmapFromPath(imageUri ,context);

            }while (c.moveToNext());

        }


        return null;

    }
    public void closeDB()
    {
        if(db != null)
            db.close();
    }

}
