package com.example.louyulin.day15ninepoint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), LockPatternView.FinishUpListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lpv.finishUpListener = this;
    }

    override fun onFinishUp(list: ArrayList<LockPatternView.Point>) {
        var stringBuffer = StringBuffer();

        for (point in list) {
            stringBuffer.append(point.index.toString())
        }

        val string = String(stringBuffer)


        if (string.equals("03678")){
            Toast.makeText(this, "解锁成功", Toast.LENGTH_SHORT).show()
            lpv.postDelayed(Runnable(){
                lpv.onSucess()
            } , 500);
        }else{
            lpv.onFailue()
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
            lpv.postDelayed(Runnable(){
                lpv.onSucess()
            } , 500);
        }
    }
}
