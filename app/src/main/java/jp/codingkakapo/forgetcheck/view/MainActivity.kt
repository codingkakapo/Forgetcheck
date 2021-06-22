package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import jp.codingkakapo.forgetcheck.R

class MainActivity : FragmentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}