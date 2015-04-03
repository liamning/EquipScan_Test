package com.equipscan.info;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Ning on 3/7/2015.
 */
public class EquipmentInfo implements Parcelable {

    private String ID;
    private String name;
    private Date checkInDate;
    private int inOut;
    private String StaffName;
    private String TeamName;
    private String Approver;
    private String Remarks;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public int getInOut() {
        return inOut;
    }

    public void setInOut(int inOut) {
        this.inOut = inOut;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getApprover() {
        return Approver;
    }

    public void setApprover(String approver) {
        Approver = approver;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public EquipmentInfo() {

    }


    public EquipmentInfo(String name, Date checkInDate) {
        this.checkInDate = checkInDate;
        this.name = name;
        this.inOut = 0;
    }

    public EquipmentInfo(String ID ,String name, Date checkInDate, int inOut) {
        this.checkInDate = checkInDate;
        this.name = name;
        this.inOut = inOut;
        this.ID = ID;
    }




    /** Used to give additional hints on how to process the received parcel.*/
    @Override
    public int describeContents() {
        // ignore for now
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {


        pc.writeString(ID);
        pc.writeLong(checkInDate.getTime());

        pc.writeString(name);
        pc.writeInt(inOut);
        pc.writeString(StaffName);
        pc.writeString(Remarks);
        pc.writeString(Approver);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<EquipmentInfo> CREATOR = new Parcelable.Creator<EquipmentInfo>() {
        public EquipmentInfo createFromParcel(Parcel pc) {
            return new EquipmentInfo(pc);
        }
        public EquipmentInfo[] newArray(int size) {
            return new EquipmentInfo[size];
        }
    };

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public EquipmentInfo(Parcel pc){
        ID        =  pc.readString();
        checkInDate         = new Date(pc.readLong());
        name        =  pc.readString();
        inOut        =  pc.readInt();
        StaffName        =  pc.readString();
        Remarks        =  pc.readString();
        Approver        =  pc.readString();
    }


}

