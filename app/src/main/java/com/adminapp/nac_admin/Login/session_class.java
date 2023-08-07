package com.adminapp.nac_admin.Login;

public class session_class {

    private String token;
    private session_mobile user;

    public session_mobile getUser() {
        return user;
    }

    public void setUser(session_mobile user) {
        this.user = user;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
