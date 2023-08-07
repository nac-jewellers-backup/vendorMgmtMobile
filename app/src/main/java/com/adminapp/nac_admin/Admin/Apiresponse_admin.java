package com.adminapp.nac_admin.Admin;

import java.util.ArrayList;

public class Apiresponse_admin {

    private String status;
    private String message;
    private ArrayList<Admin_list> result;

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

    public ArrayList<Admin_list> getResult() {
        return result;
    }

    public void setResult(ArrayList<Admin_list> result) {
        this.result = result;
    }



}
