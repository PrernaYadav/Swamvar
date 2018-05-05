package com.starling.softwares.swamvar.Model;

public class ShowAppointment {

    String id;
    String subject;
    String date;
    String status;
    String status_comment;
    String time;
    String address;
    String email;
    String role_name;

    public ShowAppointment() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_comment() {
        return status_comment;
    }

    public void setStatus_comment(String status_comment) {
        this.status_comment = status_comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public ShowAppointment(String id, String subject, String date, String status, String status_comment, String time, String address, String email, String role_name) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.status = status;
        this.status_comment = status_comment;
        this.time = time;
        this.address = address;
        this.email = email;
        this.role_name = role_name;


    }
}
