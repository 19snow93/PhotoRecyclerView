package com.gzcp.photorecyclerview

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autoObtainCameraPermission()


        btn.setOnClickListener {
            if(!et.text.toString().isNullOrEmpty()) {
                var num = et.text.toString().toInt()

                var p = PhoneConfigBean()

                for (i in 0..num) {
                    var b = PhoneConfigBean.BodyBean()
                    b.id = i.toString()
                    b.labelName = "车的45°拍摄"
                    p.body.add(b)
                }
                var intent = Intent(this@MainActivity, TakePhotoActivity::class.java)
                var bundle = Bundle()
                bundle.putParcelable("p", p)
                intent.putExtras(bundle)
                startActivity(intent)
            }else{
                Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 自动获取相机权限
     */
    private fun autoObtainCameraPermission():Boolean {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED
                ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, Manifest.permission.CAMERA)) {
                Toast.makeText(this,"您已经拒绝过一次",Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
            ), PhotoMenuAdapter.CAMERA_PERMISSIONS_REQUEST_CODE)
            return false
        } else {//有权限直接调用系统相机拍照
            return true
        }
    }
}
