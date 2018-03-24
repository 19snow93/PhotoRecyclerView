package com.gzcp.photorecyclerview

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.support.v7.widget.Toolbar
import android.view.View
import java.io.File
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gzcp.photorecyclerview.FileUtils.decodeFile
import com.gzcp.photorecyclerview.FileUtils.saveBitmapFile
import kotlinx.android.synthetic.main.activity_photo.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by leo on 2018/2/28.
 */
class TakePhotoActivity : AppCompatActivity() {

    private lateinit var mPhotoMenuAdapter: PhotoMenuAdapter
    private var bodyBeanList = ArrayList<PhoneConfigBean.BodyBean>()

    private var mMaxPhotoNum = 5
    private var TAKE_PHOTO_REQUEST = 0

    private lateinit var photoFile:File

    lateinit var phoneConfigBean:PhoneConfigBean


    //删除记住的position
    private var deletePosition1 = 0
    private var deletePosition2 = 0

    companion object {
        var PHOTO_DIR = Environment.getExternalStorageDirectory().path + "/takephoto/"
        private val CAMERA_PERMISSIONS_REQUEST_CODE = 0x03

        fun toTakePhoto(mContent: Context) : Intent {
            var intent = Intent(mContent, TakePhotoActivity::class.java)
            return intent
        }
    }

    private fun handleDeleteSubject() {
        bodyBeanList.get(deletePosition1).photoList.removeAt(deletePosition2)
        if (bodyBeanList.get(deletePosition1).photoList.size == mMaxPhotoNum - 1 && !bodyBeanList.get(deletePosition1).photoList.get(bodyBeanList.get(deletePosition1).photoList.size - 1).equals("")) {
            Collections.reverse(bodyBeanList.get(deletePosition1).photoList)
            bodyBeanList.get(deletePosition1).photoList.add("")
        }
        mPhotoMenuAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        var bundle = getIntent().getExtras()

        phoneConfigBean = bundle.getParcelable("p")

        initEventAndData()
    }

    fun initEventAndData() {

        initAdapter()

        btn_take_photo.setOnClickListener {
            var dataBeanList = ArrayList<AddDataBean>()

            for (i in bodyBeanList.indices) {
                var dataBean = AddDataBean()
                dataBean.id = bodyBeanList.get(i).id
                for (j in bodyBeanList.get(i).photoList.indices) {
                    if (!bodyBeanList.get(i).photoList.get(j).equals("")) {
                        var imageBean = AddDataBean.ImgListBean()
                        imageBean.value = bodyBeanList.get(i).photoList.get(j)
                        dataBean.imgList.add(imageBean)
                    }
                }
                dataBeanList.add(dataBean)
            }

        }
    }

    fun initAdapter(){


        mPhotoMenuAdapter = PhotoMenuAdapter(this,bodyBeanList)
        rlv_photo_menu.layoutManager = LinearLayoutManager(this)
        rlv_photo_menu.adapter = mPhotoMenuAdapter

        mPhotoMenuAdapter.onDeleteListener = object : PhotoMenuAdapter.OnDeleteListener{
            override fun onDeleteResult(position: Int,deletePosition:Int) {
                deletePosition1 = position
                deletePosition2 = deletePosition
                handleDeleteSubject()
            }

        }

        mPhotoMenuAdapter.onPhotoExampleListener = object : PhotoMenuAdapter.OnPhotoExampleListener{
            override fun onPhotoExampleResult(position: Int) {

            }

        }

        configSucceed(phoneConfigBean)

    }

    //排除要删除的照片，只需要提交要保存照片的列表
    private fun createSaveBean(position:Int,photoName: String) {
        var dataBeanList = ArrayList<AddDataBean>()
        var dataBean1 = AddDataBean()
        dataBean1.id = bodyBeanList.get(position).id
        for(i in bodyBeanList.get(position).photoList.indices) {
            if(!bodyBeanList.get(position).photoList.get(i).equals(photoName)) {
                var imageBean = AddDataBean.ImgListBean()
                imageBean.value = bodyBeanList.get(position).photoList.get(i)
                dataBean1.imgList.add(imageBean)
            }
        }
        dataBeanList.add(dataBean1)


    }

    fun delDataSucceed() {
        //    ToastUtil.showToast("删除成功！")
        handleDeleteSubject()
    }

    fun configSucceed(phoneConfigBean: PhoneConfigBean) {
        if(phoneConfigBean.body.size > 0){
            for(i in phoneConfigBean.body.indices){
                if(!phoneConfigBean.body.get(i).id.isNullOrEmpty()){
                    bodyBeanList.add(phoneConfigBean.body.get(i))
                    bodyBeanList.get(i).photoList.add("")
                }
            }
            mPhotoMenuAdapter.notifyDataSetChanged()
            btn_take_photo.visibility = View.VISIBLE

        }else{

        }


    }

    fun queryDataSucceed(queryData: QueryData) {

        for(i in bodyBeanList.indices){
            bodyBeanList.get(i).photoList.clear()
        }

        for(i in queryData.body.indices){
            for(j in bodyBeanList.indices)
                if(queryData.body.get(i).id.equals(bodyBeanList.get(j).id)){
                    if(queryData.body.get(i).imgList.size > 0) {
                        for (z in queryData.body.get(i).imgList.indices) {
                            bodyBeanList.get(j).photoList.add(queryData.body.get(i).imgList.get(z).value)
                        }
                        if(bodyBeanList.get(j).photoList.size < 5){
                            bodyBeanList.get(j).photoList.add("")
                        }
                    }
                }
        }
    }

    fun addDataSucceed() {
        //   ToastUtil.showToast("上传成功")
        handleListSubject()

    }


    //上传成功后不能添加图片，需要吧list最后一个为""的删掉
    private fun handleListSubject() {
        for(i in bodyBeanList.indices) {
            if (bodyBeanList.get(i).photoList.get(bodyBeanList.get(i).photoList.size - 1).equals("")) {
                bodyBeanList.get(i).photoList.removeAt(bodyBeanList.get(i).photoList.size - 1)
            }
        }
        mPhotoMenuAdapter.notifyDataSetChanged()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            TAKE_PHOTO_REQUEST ->{
                if (resultCode == RESULT_CANCELED) {
                    //    ToastUtil.showToast("取消了拍照")
                    return;
                }else if(resultCode == RESULT_OK) {
                    photoFile = PhotoMenuAdapter.photoFileA

                    Luban.with(this)
                            .load(photoFile)
                            .setTargetDir(PHOTO_DIR)       // 设置压缩后文件存储位置
                            .setCompressListener(object : OnCompressListener { //设置回调
                                override fun onStart() {
                                    //  压缩开始前调用，可以在方法内启动 loading UI
                                }

                                override fun onSuccess(file: File) {

                                    if(bodyBeanList.get(PhotoMenuAdapter.positionA).labelName.equals("身份证正面照")
                                            || bodyBeanList.get(PhotoMenuAdapter.positionA).labelName.equals("身份证反面照")){
                                        var bmp = decodeFile(file)
                                        saveBitmapFile(createFrontWaterMark(bmp,"只用于" + title + "认证"),photoFile.path)
                                    }else{
                                        //压缩成功后调用，返回压缩后的图片文件
                                        //重命名照片名，替换之前生成的照片文件
                                        if (file.exists()) {
                                            file.renameTo(photoFile);
                                        }
                                    }

                                    Collections.reverse(bodyBeanList.get(PhotoMenuAdapter.positionA).photoList)
                                    if (bodyBeanList.get(PhotoMenuAdapter.positionA).photoList.size == mMaxPhotoNum && bodyBeanList.get(PhotoMenuAdapter.positionA).photoList.get(0).equals("")) {
                                        bodyBeanList.get(PhotoMenuAdapter.positionA).photoList.removeAt(0)
                                    }
                                    bodyBeanList.get(PhotoMenuAdapter.positionA).photoList.add(photoFile.name)
                                    Collections.reverse(bodyBeanList.get(PhotoMenuAdapter.positionA).photoList)
                                    mPhotoMenuAdapter.notifyDataSetChanged()

                                }

                                override fun onError(e: Throwable) {
                                    //  当压缩过程出现问题时调用
                                    //    ToastUtil.showToast("照片压缩出错")
                                }
                            }).launch()    //启动压缩
                }

            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        var dir = File(PHOTO_DIR)
        deleteDirWihtFile(dir)
    }

    private fun deleteDirWihtFile(dir:File) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (file : File in dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
        //调用系统相机申请拍照权限回调
            CAMERA_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    photoFile = TakePhotoUtils.takePhoto(this@TakePhotoActivity, TAKE_PHOTO_REQUEST)
                } else {
                    //   ToastUtil.showToast("请允许打开相机！！")
                }


            }
        }
    }

}