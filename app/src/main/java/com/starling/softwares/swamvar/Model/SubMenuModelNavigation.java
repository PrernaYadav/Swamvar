package com.starling.softwares.swamvar.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 1/4/2018.
 */

public class SubMenuModelNavigation implements Parcelable {

    private String sub_category_id;
    private String subcategory_name;

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public SubMenuModelNavigation(String sub_category_id, String subcategory_name) {
        this.sub_category_id = sub_category_id;
        this.subcategory_name = subcategory_name;
    }

    public SubMenuModelNavigation(Parcel in) {
        sub_category_id = in.readString();
        subcategory_name = in.readString();
    }

    public static final Creator<SubMenuModelNavigation> CREATOR = new Creator<SubMenuModelNavigation>() {
        @Override
        public SubMenuModelNavigation createFromParcel(Parcel in) {
            return new SubMenuModelNavigation(in);
        }

        @Override
        public SubMenuModelNavigation[] newArray(int size) {
            return new SubMenuModelNavigation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sub_category_id);
        parcel.writeString(subcategory_name);
    }
}
