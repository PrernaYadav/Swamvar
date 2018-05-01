package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/4/2018.
 */

public class LandingPageModel {

    /**
     * status : success
     * categories : [{"category_id":"1","title":"mens"},{"category_id":"2","title":"womens"},{"category_id":"3","title":"kids"}]
     * catDesigns : [{"category_id":"1","title":"mens","designs":[{"design_id":"1","design_name":"design1","design_number":"DES001","design_image":"http://192.168.0.73:8081/swayamvar/uploads/design/img755033908.jpg","mrp":"1000"},{"design_id":"2","design_name":"design2","design_number":"DES002","design_image":"http://192.168.0.73:8081/swayamvar/uploads/design/images568095007.jpg","mrp":"3000"},{"design_id":"3","design_name":"design3","design_number":"DES003","design_image":"http://192.168.0.73:8081/swayamvar/uploads/design/GWnfakd516780654.png","mrp":"5000"}]}]
     */

    private String status;
    private List<CategoriesBean> categories;
    private List<CatDesignsBean> catDesigns;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<CatDesignsBean> getCatDesigns() {
        return catDesigns;
    }

    public void setCatDesigns(List<CatDesignsBean> catDesigns) {
        this.catDesigns = catDesigns;
    }

    public static class CategoriesBean {
        /**
         * category_id : 1
         * title : mens
         */

        private String category_id;
        private String title;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class CatDesignsBean {
        /**
         * category_id : 1
         * title : mens
         * designs : [{"design_id":"1","design_name":"design1","design_number":"DES001","design_image":"http://192.168.0.73:8081/swayamvar/uploads/design/img755033908.jpg","mrp":"1000"},{"design_id":"2","design_name":"design2","design_number":"DES002","design_image":"http://192.168.0.73:8081/swayamvar/uploads/design/images568095007.jpg","mrp":"3000"},{"design_id":"3","design_name":"design3","design_number":"DES003","design_image":"http://192.168.0.73:8081/swayamvar/uploads/design/GWnfakd516780654.png","mrp":"5000"}]
         */

        private String category_id;
        private String title;
        private List<DesignsBean> designs;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<DesignsBean> getDesigns() {
            return designs;
        }

        public void setDesigns(List<DesignsBean> designs) {
            this.designs = designs;
        }

        public static class DesignsBean {
            /**
             * design_id : 1
             * design_name : design1
             * design_number : DES001
             * design_image : http://192.168.0.73:8081/swayamvar/uploads/design/img755033908.jpg
             * mrp : 1000
             */

            private String design_id;
            private String design_name;
            private String design_number;
            private String design_image;
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

            public String getDesign_image() {
                return design_image;
            }

            public void setDesign_image(String design_image) {
                this.design_image = design_image;
            }

            public String getMrp() {
                return mrp;
            }

            public void setMrp(String mrp) {
                this.mrp = mrp;
            }
        }
    }
}
