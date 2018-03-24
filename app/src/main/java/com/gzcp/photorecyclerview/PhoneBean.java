package com.gzcp.photorecyclerview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by leo on 2018/3/20.
 */

public class PhoneBean implements Parcelable {
    ArrayList<PhoneConfigBean.BodyBean> body = new ArrayList<PhoneConfigBean.BodyBean>();

    protected PhoneBean(Parcel in) {
        body = in.createTypedArrayList(PhoneConfigBean.BodyBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhoneBean> CREATOR = new Creator<PhoneBean>() {
        @Override
        public PhoneBean createFromParcel(Parcel in) {
            return new PhoneBean(in);
        }

        @Override
        public PhoneBean[] newArray(int size) {
            return new PhoneBean[size];
        }
    };
}
