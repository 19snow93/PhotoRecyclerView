package com.gzcp.ssgaffairs.ui.Photo

import android.content.Context
import android.os.Environment
import com.gzcp.photorecyclerview.BaseAdapter
import com.gzcp.photorecyclerview.BaseViewHolder
import com.gzcp.photorecyclerview.R
import com.gzcp.photorecyclerview.TakePhotoActivity.Companion.PHOTO_DIR

/**
 * Created by leo on 2018/2/28.
 */
class PhotoAdapter (layoutResId:Int, context: Context, datas:ArrayList<String>): BaseAdapter<String, BaseViewHolder>(layoutResId,context,datas){


    override fun convert(viewHolder: BaseViewHolder?, position: Int, item: String?) {
        viewHolder?.setGlideAddPhotoImage(R.id.iv_take_photo,PHOTO_DIR + item)
    }

}