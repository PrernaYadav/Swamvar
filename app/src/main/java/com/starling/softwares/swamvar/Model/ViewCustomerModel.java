package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/9/2018.
 */

public class ViewCustomerModel  {


    /**
     * status : success
     * data : [{"user_id":"4","name":"ayushi","mobile":"7878766767","email":"ayushi@gmail.com","address":"address here"},{"user_id":"3","name":"ayu","mobile":"9559352366","email":"ayu78@gmail.com","address":"gurgaon sector 15\r\n"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 4
         * name : ayushi
         * mobile : 7878766767
         * email : ayushi@gmail.com
         * address : address here
         */

        private String user_id;
        private String name;
        private String mobile;
        private String email;
        private String address;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
