package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.model.ForgetCheckDB
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel

class MainActivity : FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return CheckListViewModel.Factory(this.application as ForgetCheckApplication)
    }
}