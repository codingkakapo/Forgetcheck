package jp.codingkakapo.forgetcheck.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditItemViewModel : ViewModel() {
    val hogeText : MutableLiveData<String> by lazy {
        MutableLiveData<String>("HELLO WORLD")
    }
    init{
        hogeText.observeForever({
        })
    }
}