package com.equipscan.info;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Ning on 4/6/2015.
 */
public class ScenInfo {

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Bitmap getScenImage() {
        return ScenImage;
    }

    public void setScenImage(Bitmap scenImage) {
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

    private String ID;
    private Bitmap ScenImage;
    private String Remarks;
    private Date CreateDate;
}
