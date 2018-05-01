package com.starling.softwares.swamvar.Model;

/**
 * Created by Admin on 1/3/2018.
 */

public class User {

    /**
     * status : success
     * msg : Login Successfully
     * user_id : 1
     * user_name : nidhi dwivedi
     * mobile : 9559352379
     * email : user@gmail.com
     * role : 1
     */

    private String status;
    private String msg;
    private String user_id;
    private String user_name;
    private String mobile;
    private String email;
    private String role;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
