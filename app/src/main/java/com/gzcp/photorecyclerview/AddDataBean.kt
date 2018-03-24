package com.gzcp.photorecyclerview

/**
 * Created by leo on 2018/3/5.
 */
class AddDataBean {

    var id: String? = null
    var imgList: ArrayList<ImgListBean> = ArrayList()

    class ImgListBean {
        /**
         * value : xxxxxx
         */

        var value: String? = null
    }
}