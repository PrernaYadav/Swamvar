package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/5/2018.
 */

public class ProductModel {


    /**
     * status : success
     * data : [{"design_id":"1","design_name":"design1","design_number":"DES001","design_picture":"http://192.168.0.73:8081/swayamvar/uploads/design/img755033908.jpg","mrp":"100"},{"design_id":"2","design_name":"design2","design_number":"DES002","design_picture":"http://192.168.0.73:8081/swayamvar/uploads/design/images568095007.jpg","mrp":"100"},{"design_id":"3","design_name":"design3","design_number":"DES003","design_picture":"http://192.168.0.73:8081/swayamvar/uploads/design/GWnfakd516780654.png","mrp":"100"}]
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
         * design_id : 1
         * design_name : design1
         * design_number : DES001
         * design_picture : http://192.168.0.73:8081/swayamvar/uploads/design/img755033908.jpg
         * mrp : 100
         */

        private String design_id;
        private String design_name;
        private String design_number;
        private String design_picture;
        private String mrp;

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        private String gst;

        public String getDesign_id() {
            return design_id;
        }

        public void setDesign_id(String design_id) {
            this.design_id = design_id;
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

        public String getDesign_picture() {
            return design_picture;
        }

        public void setDesign_picture(String design_picture) {
            this.design_picture = design_picture;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }
    }
}
