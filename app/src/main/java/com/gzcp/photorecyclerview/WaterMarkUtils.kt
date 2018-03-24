package com.gzcp.photorecyclerview

import android.graphics.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



/**
 * Created by leo on 2018/3/13.
 */
fun createFrontWaterMark(bitmap: Bitmap,text:String):Bitmap{

    val width = bitmap.getWidth()
    val height = bitmap.getHeight()

    var bmp = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
    var canvas = Canvas(bmp)


    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var paint1 = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = Color.RED
    paint.textSize = 36f
    paint.alpha = 70
    canvas.drawBitmap(bitmap,0f,0f,paint1)
    canvas.rotate(-30f,width/2f,height/2f)
    canvas.drawText(text,width/5f,height/2f,paint)

    paint.setStyle(Paint.Style.STROKE)
    paint.strokeWidth = 8f

    var textBounds = Rect()
    paint.getTextBounds(text, 0, text.length, textBounds);

    textBounds.left += width/5f.toInt() - 10
    textBounds.top += height/2f.toInt() - 10
    textBounds.right += width/5f.toInt() + 10
    textBounds.bottom += height/2f.toInt() + 10

    canvas.drawRect(textBounds, paint);

    canvas.save(Canvas.ALL_SAVE_FLAG);
    canvas.restore();

    return bmp
}

fun createPersonFrontWaterMark(bitmap: Bitmap,title:String,place:String,time:String):Bitmap{

    val width = bitmap.getWidth()
    val height = bitmap.getHeight()

    var bmp = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
    var canvas = Canvas(bmp)


    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var paint1 = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = Color.RED
    paint.alpha = 70
    canvas.drawBitmap(bitmap,0f,0f,paint1)
    paint.textSize = 24f
    if(width < 230) {
        canvas.drawText(time, width * 4 / 7f - 10f, height - 30f, paint)
    }else{
        canvas.drawText(time, width - 230f, height - 20f, paint)
    }
    canvas.drawText(place,20f, height - 20f,paint)

    canvas.rotate(-30f,width/2f,height/3f)

    paint.setStyle(Paint.Style.FILL)
    paint.textSize = 36f
    canvas.drawText(title,width/5f,height/3f,paint)

    paint.setStyle(Paint.Style.STROKE)
    paint.strokeWidth = 8f

    var textBounds = Rect()
    paint.getTextBounds(title, 0, title.length, textBounds);

    textBounds.left += width/5f.toInt() - 10
    textBounds.top += height/3f.toInt() - 10
    textBounds.right += width/5f.toInt() + 10
    textBounds.bottom += height/3f.toInt() + 10

    canvas.drawRect(textBounds, paint);

    canvas.save(Canvas.ALL_SAVE_FLAG);
    canvas.restore();

    return bmp
}