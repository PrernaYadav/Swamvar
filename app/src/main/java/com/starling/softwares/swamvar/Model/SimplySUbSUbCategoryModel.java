package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/5/2018.
 */

public class SimplySUbSUbCategoryModel {

    /**
     * status : success
     * data : [{"sub_sub_category_id":"1","sub_category_id":"1","subsubcategory_name":"cotton shirt","subcategory_name":"shirt"}]
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
         * sub_sub_category_id : 1
         * sub_category_id : 1
         * subsubcategory_name : cotton shirt
         * subcategory_name : shirt
         */

        private String sub_sub_category_id;
        private String sub_category_id;
        private String subsubcategory_name;
        private String subcategory_name;

        public String getSub_sub_category_id() {
            return sub_sub_category_id;
        }

        public void setSub_sub_category_id(String sub_sub_category_id) {
            this.sub_sub_category_id = sub_sub_category_id;
        }

        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getSubsubcategory_name() {
            return subsubcategory_name;
        }

        public void setSubsubcategory_name(String subsubcategory_name) {
            this.subsubcategory_name = subsubcategory_name;
        }

        public String getSubcategory_name() {
            return subcategory_name;
        }

        public void setSubcategory_name(String subcategory_name) {
            this.subcategory_name = subcategory_name;
        }
    }
}
