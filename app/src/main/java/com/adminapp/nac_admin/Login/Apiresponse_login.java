package com.adminapp.nac_admin.Login;

import java.util.ArrayList;

public class Apiresponse_login {

    private String message;
    private String status;
    private String name;
    private session_class session;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public session_class getSession() {
        return session;
    }

    public void setSession(session_class session) {
        this.session = session;
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
