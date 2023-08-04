package com.adminapp.nac_admin.Forgotpassword;

public class Apiresponse_getotp {

    private String status;
    private String message;
    private userdetails data;

    public userdetails getData() {
        return data;
    }

    public void setData(userdetails data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
