package com.adminapp.nac_admin.vendor;

import java.util.ArrayList;

public class Apiresponse_vendor {

    private String status;
    private String message;
    ArrayList<list_vendor> result;

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

    public ArrayList<list_vendor> getResult() {
        return result;
    }

    public void setResult(ArrayList<list_vendor> result) {
        this.result = result;
    }

}
