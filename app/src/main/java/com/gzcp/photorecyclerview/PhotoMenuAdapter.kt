package com.gzcp.photorecyclerview

import android.Manifest
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.R.attr.data
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.gzcp.ssgaffairs.ui.Photo.PhotoAdapter
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by leo on 2018/3/7.
 */
class PhotoMenuAdapter(var context: Context,var datas:ArrayList<PhoneConfigBean.BodyBean>) : RecyclerView.Adapter<PhotoMenuAdapter.MyViewHolder>() {

    private var mMaxPhotoNum = 5
    private var TAKE_PHOTO_REQUEST = 0

    lateinit var onDeleteListener: OnDeleteListener
    lateinit var onPhotoExampleListener: OnPhotoExampleListener

    companion object {
        val CAMERA_PERMISSIONS_REQUEST_CODE = 0x03
        var positionA = 0
        lateinit var photoFileA:File

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_photo_menu, parent, false);
        return PhotoMenuAdapter.MyViewHolder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        var mFrontAdapter: PhotoAdapter
        holder?.tvTitle?.setText(datas.get(position).labelName)
        var mFrontList = datas.get(position).photoList
        holder?.rlv?.setLayoutManager(GridLayoutManager(context, 4))
        mFrontAdapter = PhotoAdapter(R.layout.item_take_photo,context, mFrontList)
        holder?.rlv?.setAdapter(mFrontAdapter)

        mFrontAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener{
            override fun onItemClick(view: View?, p: Int) {
                positionA = position
                if(datas.get(position).photoList.size <= mMaxPhotoNum && datas.get(position).photoList.size > 0
                        && p == datas.get(position).photoList.size - 1 && datas.get(position).photoList.get(datas.get(position).photoList.size - 1).equals("")){
                    photoFileA = TakePhotoUtils.takePhoto(context as Activity, TAKE_PHOTO_REQUEST)
                }else{
                    if(!datas.get(position).photoList.get(p).equals("")) {
                        if(onDeleteListener != null) {
                            onDeleteListener.onDeleteResult(position,p)
                        }
                    }
                }

            }
        })

    }




    interface OnDeleteListener{
        fun onDeleteResult(position:Int,deletePosition:Int)
    }

    interface OnPhotoExampleListener{
        fun onPhotoExampleResult(position:Int)
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val rlv: RecyclerView

        init {
            tvTitle = view.findViewById(R.id.tv_item_menu_title) as TextView
            rlv = view.findViewById(R.id.rlv_front) as RecyclerView
        }
    }
}