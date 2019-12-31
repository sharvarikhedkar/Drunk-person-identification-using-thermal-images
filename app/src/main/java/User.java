package com.techbuzz.katraj.drunkpersondetection;

/**
 * Created by admin on 11-Sep-17.
 */

public class User {
    int id;
    String fullname;
    String emailid;
    String password;
    String mobileno;
    String gender;

    public User(int id, String fullname, String emailid, String password, String mobileno, String gender) {
        this.id = id;
        this.fullname = fullname;
        this.emailid = emailid;
        this.password = password;
        this.mobileno = mobileno;
        this.gender = gender;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
