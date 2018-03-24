package com.gzcp.photorecyclerview

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by leo on 2018/3/7.
 */
class PhoneConfigBean() : Parcelable {
    var body = ArrayList<BodyBean>()

    constructor(parcel: Parcel) : this() {
        body = parcel.createTypedArrayList(PhoneConfigBean.BodyBean.CREATOR)
    }


    class BodyBean() : Parcelable{
        /**
         * id : 1
         * labelName : 车头45度拍照
         * imgExampleURL : http://xxxx/xxxx.jpg
         * status : 1
         * description :
         * sort : 1
         * isRequire : 1
         */

        var id: String? = null
        var labelName: String? = null
        var photoList = ArrayList<String>()

        constructor(parcel: Parcel) : this() {
            id = parcel.readString()
            labelName = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(labelName)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<BodyBean> {
            override fun createFromParcel(parcel: Parcel): BodyBean {
                return BodyBean(parcel)
            }

            override fun newArray(size: Int): Array<BodyBean?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhoneConfigBean> {
        override fun createFromParcel(parcel: Parcel): PhoneConfigBean {
            return PhoneConfigBean(parcel)
        }

        override fun newArray(size: Int): Array<PhoneConfigBean?> {
            return arrayOfNulls(size)
        }
    }


}