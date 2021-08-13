package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel


class MainActivity : FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) { }
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return CheckListViewModel.Factory(this.application as ForgetCheckApplication)
    }
}