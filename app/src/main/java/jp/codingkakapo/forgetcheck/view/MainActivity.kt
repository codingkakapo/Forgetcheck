package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.model.ForgetCheckDB

class MainActivity : FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}