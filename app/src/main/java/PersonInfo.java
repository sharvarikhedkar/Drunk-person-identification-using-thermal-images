package com.techbuzz.katraj.drunkpersondetection;

/**
 * Created by admin on 15-Sep-17.
 */

public class PersonInfo {
    String rid;
    String userid;
    String date;
    String remark;

    String RecipeeId;

    public String getRecipeeId() {
        return RecipeeId;
    }

    public void setRecipeeId(String recipeeId) {
        RecipeeId = recipeeId;
    }



    public PersonInfo(String rid, String RecipeeId, String remark, String date, String userid) {

        this.rid=rid;
        this.RecipeeId=RecipeeId;
        this.remark=remark;
        this.date=date;
        this.userid=userid;

    }



    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {

        this.rid = rid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
