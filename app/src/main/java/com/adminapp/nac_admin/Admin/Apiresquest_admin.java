package com.adminapp.nac_admin.Admin;

import com.adminapp.nac_admin.Login.session_class;

public class Apiresquest_admin {

    private pass_sessiondata session;
    private String name;

    public pass_sessiondata getSession() {
        return session;
    }

    public void setSession(pass_sessiondata session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
