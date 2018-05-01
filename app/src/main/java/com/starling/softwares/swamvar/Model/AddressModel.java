package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/10/2018.
 */

public class AddressModel {

    /**
     * status : success
     * data : [{"address_id":"1","name":"nidhi","mobile":"7675543423","state_name":"ASSAM","city_name":"Amadalavalasa","address":"gurgaon patel nagar","landmark":"hotel DDR","pincode":"110008"},{"address_id":"2","name":"abhishek","mobile":"6565656565","state_name":"ANDAMAN AND NICOBAR ISLANDS","city_name":"Kolhapur","address":"address","landmark":"test","pincode":"202202"},{"address_id":"3","name":"abhishek","mobile":"6565656565","state_name":"ANDAMAN AND NICOBAR ISLANDS","city_name":"Kolhapur","address":"address","landmark":"test","pincode":"202202"},{"address_id":"4","name":"shobhit","mobile":"9990193401","state_name":"HARYANA","city_name":"Gurgaon","address":"something","landmark":"something","pincode":"122001"}]
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
         * address_id : 1
         * name : nidhi
         * mobile : 7675543423
         * state_name : ASSAM
         * city_name : Amadalavalasa
         * address : gurgaon patel nagar
         * landmark : hotel DDR
         * pincode : 110008
         */

        private String address_id;
        private String name;
        private String mobile;
        private String state_name;
        private String city_name;
        private String address;
        private String landmark;
        private String pincode;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
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

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }
    }
}
