package com.starling.softwares.swamvar.Model;

import java.util.List;

/**
 * Created by Admin on 1/6/2018.
 */

public class ProductDescriptionModel {


    /**
     * status : success
     * data : [{"color_id":"1","color":"Red","color_code":"#ff0000","design_number":"DES001","sizes":[{"size_id":"1","size":"32","available_quantity":"1"},{"size_id":"2","size":"34","available_quantity":"1"},{"size_id":"3","size":"36","available_quantity":"1"},{"size_id":"4","size":"38","available_quantity":"1"},{"size_id":"5","size":"42","available_quantity":""}],"images":[{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/bulb-s622052597.png"},{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/download579122120.jpg"},{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/presentation395122284.jpg"}]},{"color_id":"2","color":"Blue","color_code":"#0000ff","design_number":"DES001","sizes":[{"size_id":"1","size":"32","available_quantity":"1"},{"size_id":"2","size":"34","available_quantity":"1"},{"size_id":"3","size":"36","available_quantity":"1"},{"size_id":"4","size":"38","available_quantity":"1"},{"size_id":"5","size":"42","available_quantity":""}],"images":[{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/bulb-s151118325.png"},{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/presentation861841412.jpg"},{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/website-design-ireland-martec-web-designers_43706791604100.jpg"}]}]
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
         * color_id : 1
         * color : Red
         * color_code : #ff0000
         * design_number : DES001
         * sizes : [{"size_id":"1","size":"32","available_quantity":"1"},{"size_id":"2","size":"34","available_quantity":"1"},{"size_id":"3","size":"36","available_quantity":"1"},{"size_id":"4","size":"38","available_quantity":"1"},{"size_id":"5","size":"42","available_quantity":""}]
         * images : [{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/bulb-s622052597.png"},{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/download579122120.jpg"},{"design_image":"http://192.168.0.70:8081/swayamvar/uploads/design_color_image/presentation395122284.jpg"}]
         */

        private String color_id;
        private String color;
        private String color_code;
        private String design_number;
        private List<SizesBean> sizes;
        private List<ImagesBean> images;

        public String getColor_id() {
            return color_id;
        }

        public void setColor_id(String color_id) {
            this.color_id = color_id;
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

        public String getDesign_number() {
            return design_number;
        }

        public void setDesign_number(String design_number) {
            this.design_number = design_number;
        }

        public List<SizesBean> getSizes() {
            return sizes;
        }

        public void setSizes(List<SizesBean> sizes) {
            this.sizes = sizes;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class SizesBean {
            /**
             * size_id : 1
             * size : 32
             * available_quantity : 1
             */

            private String size_id;
            private String size;
            private String available_quantity;

            public String getSize_id() {
                return size_id;
            }

            public void setSize_id(String size_id) {
                this.size_id = size_id;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getAvailable_quantity() {
                return available_quantity;
            }

            public void setAvailable_quantity(String available_quantity) {
                this.available_quantity = available_quantity;
            }
        }

        public static class ImagesBean {
            /**
             * design_image : http://192.168.0.70:8081/swayamvar/uploads/design_color_image/bulb-s622052597.png
             */

            private String design_image;

            public String getDesign_image() {
                return design_image;
            }

            public void setDesign_image(String design_image) {
                this.design_image = design_image;
            }
        }
    }
}
