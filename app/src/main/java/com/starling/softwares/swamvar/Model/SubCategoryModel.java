package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/5/2018.
 */

public class SubCategoryModel {


    /**
     * status : success
     * data : [{"sub_category_id":"1","sub_category":"Foot Wear","sub_sub_category":[{"sub_sub_category_id":"1","sub_sub_category_name":"Sports Shoes","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/s1457801589.jpeg"},{"sub_sub_category_id":"2","sub_sub_category_name":"Casual Shoes","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/c1489460572.jpeg"},{"sub_sub_category_id":"3","sub_sub_category_name":"Formal Shoes","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/f1709049738.jpeg"}]},{"sub_category_id":"2","sub_category":"Sports Wear","sub_sub_category":[{"sub_sub_category_id":"4","sub_sub_category_name":"Sports T-Shirts","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/s1684856822.jpeg"},{"sub_sub_category_id":"5","sub_sub_category_name":"Track Pants","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/tp190265640.jpeg"},{"sub_sub_category_id":"6","sub_sub_category_name":"Track Suits","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/ts576756626.jpeg"}]},{"sub_category_id":"3","sub_category":"Top Wear","sub_sub_category":[{"sub_sub_category_id":"7","sub_sub_category_name":"T-Shirts","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/t1114345611.jpeg"},{"sub_sub_category_id":"8","sub_sub_category_name":"Shirts","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/download_(1)906866112.jpg"},{"sub_sub_category_id":"9","sub_sub_category_name":"Kurtas","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/k1820043024.jpeg"}]}]
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
         * sub_category_id : 1
         * sub_category : Foot Wear
         * sub_sub_category : [{"sub_sub_category_id":"1","sub_sub_category_name":"Sports Shoes","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/s1457801589.jpeg"},{"sub_sub_category_id":"2","sub_sub_category_name":"Casual Shoes","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/c1489460572.jpeg"},{"sub_sub_category_id":"3","sub_sub_category_name":"Formal Shoes","subsubcategory_image":"http://swayamvar.starlingsoftwares.in/uploads/subsubimages/f1709049738.jpeg"}]
         */

        private String sub_category_id;
        private String sub_category;
        private List<SubSubCategoryBean> sub_sub_category;

        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getSub_category() {
            return sub_category;
        }

        public void setSub_category(String sub_category) {
            this.sub_category = sub_category;
        }

        public List<SubSubCategoryBean> getSub_sub_category() {
            return sub_sub_category;
        }

        public void setSub_sub_category(List<SubSubCategoryBean> sub_sub_category) {
            this.sub_sub_category = sub_sub_category;
        }

        public static class SubSubCategoryBean {
            /**
             * sub_sub_category_id : 1
             * sub_sub_category_name : Sports Shoes
             * subsubcategory_image : http://swayamvar.starlingsoftwares.in/uploads/subsubimages/s1457801589.jpeg
             */

            private String sub_sub_category_id;
            private String sub_sub_category_name;
            private String subsubcategory_image;

            public String getSub_sub_category_id() {
                return sub_sub_category_id;
            }

            public void setSub_sub_category_id(String sub_sub_category_id) {
                this.sub_sub_category_id = sub_sub_category_id;
            }

            public String getSub_sub_category_name() {
                return sub_sub_category_name;
            }

            public void setSub_sub_category_name(String sub_sub_category_name) {
                this.sub_sub_category_name = sub_sub_category_name;
            }

            public String getSubsubcategory_image() {
                return subsubcategory_image;
            }

            public void setSubsubcategory_image(String subsubcategory_image) {
                this.subsubcategory_image = subsubcategory_image;
            }
        }
    }
}
