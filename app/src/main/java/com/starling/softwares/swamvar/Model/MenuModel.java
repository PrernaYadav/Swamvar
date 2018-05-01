package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/4/2018.
 */

public class MenuModel {


    /**
     * status : success
     * data : [{"category_id":"1","category_name":"mens","sub_category":[{"subcategory_id":"1","subcategory_name":"shirt"},{"subcategory_id":"2","subcategory_name":"shirt1"},{"subcategory_id":"7","subcategory_name":"shirt2"},{"subcategory_id":"8","subcategory_name":"shirt3"},{"subcategory_id":"9","subcategory_name":"shirt4"},{"subcategory_id":"10","subcategory_name":"shirt5"}]},{"category_id":"2","category_name":"womens","sub_category":[{"subcategory_id":"3","subcategory_name":"sari"},{"subcategory_id":"4","subcategory_name":"tops"},{"subcategory_id":"11","subcategory_name":"tops1"},{"subcategory_id":"12","subcategory_name":"tops2"},{"subcategory_id":"13","subcategory_name":"tops3"}]},{"category_id":"3","category_name":"kids","sub_category":[{"subcategory_id":"5","subcategory_name":"body lotion"},{"subcategory_id":"6","subcategory_name":"sweater"},{"subcategory_id":"14","subcategory_name":"shirt"},{"subcategory_id":"15","subcategory_name":"t-shirt"},{"subcategory_id":"16","subcategory_name":"t-shirt1"}]}]
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
         * category_id : 1
         * category_name : mens
         * sub_category : [{"subcategory_id":"1","subcategory_name":"shirt"},{"subcategory_id":"2","subcategory_name":"shirt1"},{"subcategory_id":"7","subcategory_name":"shirt2"},{"subcategory_id":"8","subcategory_name":"shirt3"},{"subcategory_id":"9","subcategory_name":"shirt4"},{"subcategory_id":"10","subcategory_name":"shirt5"}]
         */

        private String category_id;
        private String category_name;
        private List<SubCategoryBean> sub_category;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public List<SubCategoryBean> getSub_category() {
            return sub_category;
        }

        public void setSub_category(List<SubCategoryBean> sub_category) {
            this.sub_category = sub_category;
        }

        public static class SubCategoryBean {
            /**
             * subcategory_id : 1
             * subcategory_name : shirt
             */

            private String subcategory_id;
            private String subcategory_name;

            public String getSubcategory_id() {
                return subcategory_id;
            }

            public void setSubcategory_id(String subcategory_id) {
                this.subcategory_id = subcategory_id;
            }

            public String getSubcategory_name() {
                return subcategory_name;
            }

            public void setSubcategory_name(String subcategory_name) {
                this.subcategory_name = subcategory_name;
            }
        }
    }
}
