package com.equipscan.info;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Ning on 4/6/2015.
 */
public class ScenInfo  implements Parcelable {

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getScenImage() {
        return ScenImage;
    }

    public void setScenImage(String scenImage) {
        ScenImage = scenImage;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    private Integer ID;
    private String ScenImage;
    private String Remarks;
    private Date CreateDate;

    public ScenInfo() {
    }

    /** Used to give additional hints on how to process the received parcel.*/
    @Override
    public int describeContents() {
        // ignore for now
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {


        pc.writeInt(ID);
        pc.writeLong(getCreateDate().getTime());
        pc.writeString(getRemarks());
        pc.writeString(getScenImage());
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<ScenInfo> CREATOR = new Parcelable.Creator<ScenInfo>() {
        public ScenInfo createFromParcel(Parcel pc) {
            return new ScenInfo(pc);
        }
        public ScenInfo[] newArray(int size) {
            return new ScenInfo[size];
        }
    };

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public ScenInfo(Parcel pc){
        ID        =  pc.readInt();
        CreateDate         = new Date(pc.readLong());
        Remarks        =  pc.readString();
        ScenImage        =  pc.readString();
    }

}
