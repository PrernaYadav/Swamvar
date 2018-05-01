package com.starling.softwares.swamvar.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 1/10/2018.
 */

public class OrdersByIdModel implements Serializable {


    /**
     * status : success
     * data : [{"order_id":"ORDER- 1","order_date":"2018-01-11","ttl_amount":"6300","order_status":"","shipping_address":[{"name":"shobhit","mobile":"9990193401","address":"something","state":"HARYANA","city":"Gurgaon","landmark":"something","pincode":"122001"}],"order_list":[{"design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","mrp_product":"500","ttl_amount":"1000","gst":"5","amount_after_gst":"1050","quantity":"2","color_name":"pink","color_code":"#ff0080","size":"34"},{"design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","mrp_product":"500","ttl_amount":"2500","gst":"5","amount_after_gst":"2625","quantity":"5","color_name":"pink","color_code":"#ff0080","size":"36"},{"design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","mrp_product":"500","ttl_amount":"2500","gst":"5","amount_after_gst":"2625","quantity":"5","color_name":"black","color_code":"#000000","size":"42"}]}]
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

    public static class DataBean implements Serializable{
        /**
         * order_id : ORDER- 1
         * order_date : 2018-01-11
         * ttl_amount : 6300
         * order_status :
         * shipping_address : [{"name":"shobhit","mobile":"9990193401","address":"something","state":"HARYANA","city":"Gurgaon","landmark":"something","pincode":"122001"}]
         * order_list : [{"design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","mrp_product":"500","ttl_amount":"1000","gst":"5","amount_after_gst":"1050","quantity":"2","color_name":"pink","color_code":"#ff0080","size":"34"},{"design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","mrp_product":"500","ttl_amount":"2500","gst":"5","amount_after_gst":"2625","quantity":"5","color_name":"pink","color_code":"#ff0080","size":"36"},{"design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","mrp_product":"500","ttl_amount":"2500","gst":"5","amount_after_gst":"2625","quantity":"5","color_name":"black","color_code":"#000000","size":"42"}]
         */

        private String order_id;
        private String order_date;
        private String ttl_amount;
        private String order_status;
        private List<ShippingAddressBean> shipping_address;
        private List<OrderListBean> order_list;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getTtl_amount() {
            return ttl_amount;
        }

        public void setTtl_amount(String ttl_amount) {
            this.ttl_amount = ttl_amount;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public List<ShippingAddressBean> getShipping_address() {
            return shipping_address;
        }

        public void setShipping_address(List<ShippingAddressBean> shipping_address) {
            this.shipping_address = shipping_address;
        }

        public List<OrderListBean> getOrder_list() {
            return order_list;
        }

        public void setOrder_list(List<OrderListBean> order_list) {
            this.order_list = order_list;
        }

        public static class ShippingAddressBean implements Serializable{
            /**
             * name : shobhit
             * mobile : 9990193401
             * address : something
             * state : HARYANA
             * city : Gurgaon
             * landmark : something
             * pincode : 122001
             */

            private String name;
            private String mobile;
            private String address;
            private String state;
            private String city;
            private String landmark;
            private String pincode;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
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

        public static class OrderListBean implements Serializable{
            /**
             * design_name : Design1
             * design_number : DES001
             * design_image : http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg
             * mrp_product : 500
             * ttl_amount : 1000
             * gst : 5
             * amount_after_gst : 1050
             * quantity : 2
             * color_name : pink
             * color_code : #ff0080
             * size : 34
             */

            private String design_name;
            private String design_number;
            private String design_image;
            private String mrp_product;
            private String ttl_amount;
            private String gst;
            private String amount_after_gst;
            private String quantity;
            private String color_name;
            private String color_code;
            private String size;

            public String getDesign_name() {
                return design_name;
            }

            public void setDesign_name(String design_name) {
                this.design_name = design_name;
            }

            public String getDesign_number() {
                return design_number;
            }

            public void setDesign_number(String design_number) {
                this.design_number = design_number;
            }

            public String getDesign_image() {
                return design_image;
            }

            public void setDesign_image(String design_image) {
                this.design_image = design_image;
            }

            public String getMrp_product() {
                return mrp_product;
            }

            public void setMrp_product(String mrp_product) {
                this.mrp_product = mrp_product;
            }

            public String getTtl_amount() {
                return ttl_amount;
            }

            public void setTtl_amount(String ttl_amount) {
                this.ttl_amount = ttl_amount;
            }

            public String getGst() {
                return gst;
            }

            public void setGst(String gst) {
                this.gst = gst;
            }

            public String getAmount_after_gst() {
                return amount_after_gst;
            }

            public void setAmount_after_gst(String amount_after_gst) {
                this.amount_after_gst = amount_after_gst;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getColor_name() {
                return color_name;
            }

            public void setColor_name(String color_name) {
                this.color_name = color_name;
            }

            public String getColor_code() {
                return color_code;
            }

            public void setColor_code(String color_code) {
                this.color_code = color_code;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }
        }
    }
}
