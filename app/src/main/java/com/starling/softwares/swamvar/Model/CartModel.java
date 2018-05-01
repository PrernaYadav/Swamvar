package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/8/2018.
 */

public class CartModel {


    /**
     * status : success
     * data : [{"cart_id":"8","mrp":"500","gst":"5","design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","color":"red","color_code":"#ff0000","size":"34","quantity":"3"},{"cart_id":"9","mrp":"500","gst":"5","design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","color":"red","color_code":"#ff0000","size":"34","quantity":"3"},{"cart_id":"10","mrp":"500","gst":"5","design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","color":"red","color_code":"#ff0000","size":"32","quantity":"2"},{"cart_id":"11","mrp":"500","gst":"5","design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","color":"pink","color_code":"#ff0080","size":"32","quantity":"2"},{"cart_id":"12","mrp":"500","gst":"5","design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","color":"red","color_code":"#ff0000","size":"32","quantity":"1"},{"cart_id":"17","mrp":"500","gst":"5","design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","color":"red","color_code":"#ff0000","size":"34","quantity":"5"},{"cart_id":"18","mrp":"500","gst":"5","design_name":"Design1","design_number":"DES001","design_image":"http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg","color":"red","color_code":"#ff0000","size":"34","quantity":"3"}]
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
         * cart_id : 8
         * mrp : 500
         * gst : 5
         * design_name : Design1
         * design_number : DES001
         * design_image : http://swayamvar.starlingsoftwares.in/uploads/design/QQ193840742.jpeg
         * color : red
         * color_code : #ff0000
         * size : 34
         * quantity : 3
         */


        private String cart_id;
        private String mrp;
        private String gst;
        private String design_name;
        private String design_number;
        private String design_image;
        private String color;
        private String color_code;
        private String size;
        private String quantity;

        public String getPricewithoutgst() {
            return pricewithoutgst;
        }

        public void setPricewithoutgst(String pricewithoutgst) {
            this.pricewithoutgst = pricewithoutgst;
        }

        private String pricewithoutgst;

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        private String total_price;

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DataBean)) return false;

            DataBean dataBean = (DataBean) o;

            if (cart_id != null ? !cart_id.equals(dataBean.cart_id) : dataBean.cart_id != null)
                return false;
            if (mrp != null ? !mrp.equals(dataBean.mrp) : dataBean.mrp != null) return false;
            if (gst != null ? !gst.equals(dataBean.gst) : dataBean.gst != null) return false;
            if (design_name != null ? !design_name.equals(dataBean.design_name) : dataBean.design_name != null)
                return false;
            if (design_number != null ? !design_number.equals(dataBean.design_number) : dataBean.design_number != null)
                return false;
            if (design_image != null ? !design_image.equals(dataBean.design_image) : dataBean.design_image != null)
                return false;
            if (color != null ? !color.equals(dataBean.color) : dataBean.color != null)
                return false;
            if (color_code != null ? !color_code.equals(dataBean.color_code) : dataBean.color_code != null)
                return false;
            if (size != null ? !size.equals(dataBean.size) : dataBean.size != null) return false;
            return quantity != null ? quantity.equals(dataBean.quantity) : dataBean.quantity == null;
        }

        @Override
        public int hashCode() {
            int result = cart_id != null ? cart_id.hashCode() : 0;
            result = 31 * result + (mrp != null ? mrp.hashCode() : 0);
            result = 31 * result + (gst != null ? gst.hashCode() : 0);
            result = 31 * result + (design_name != null ? design_name.hashCode() : 0);
            result = 31 * result + (design_number != null ? design_number.hashCode() : 0);
            result = 31 * result + (design_image != null ? design_image.hashCode() : 0);
            result = 31 * result + (color != null ? color.hashCode() : 0);
            result = 31 * result + (color_code != null ? color_code.hashCode() : 0);
            result = 31 * result + (size != null ? size.hashCode() : 0);
            result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
            return result;
        }

    }
}
