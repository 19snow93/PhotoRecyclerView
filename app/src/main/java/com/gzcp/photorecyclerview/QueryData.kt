package com.gzcp.photorecyclerview

/**
 * Created by leo on 2018/3/5.
 */
class QueryData  {

    var body: List<BodyBean> = ArrayList<BodyBean>()

    class BodyBean {
        /**
         * id : 1
         * labelName : 车头45度拍照
         * imgExampleURL : http://xxxx/xxxx.jpg
         * isMotifyFee : 1
         * status : 1
         * description :
         * sort : 1
         * isRequire : 1
         * imgList : [{"value":"xxxxxx"},{"value":"xxxxxx"}]
         */

        var id: String? = null
        var labelName: String? = null
        var imgExampleURL: String? = null
        var isMotifyFee: String? = null
        var status: String? = null
        var description: String? = null
        var sort: String? = null
        var isRequire: String? = null
        var imgList = ArrayList<ImgListBean>()

        class ImgListBean {
            /**
             * value : xxxxxx
             */

            var value: String = ""
        }
    }
}