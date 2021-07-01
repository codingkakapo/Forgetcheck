package jp.codingkakapo.forgetcheck.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.utils.Const
import java.time.LocalDateTime

class CheckListViewModel() : ViewModel() {
    lateinit var anxietyList : ObservableArrayList<AnxietyModel>

    init{
        anxietyList = ObservableArrayList<AnxietyModel>()
    }

    var hoge : String = "CheckListViewModel.プロパティほげ"

    //Boolて。Objectとかでやるべき？
    var fabClickEvent : MutableLiveData<Boolean> = MutableLiveData()

    // 表示テスト用のリスト
    fun getTestData() : List<AnxietyModel>{
        val now = LocalDateTime.now()
         return listOf<AnxietyModel>(
            AnxietyModel("hoge1", now, now, false),
            AnxietyModel("hoge2", now, now, false),
            AnxietyModel("hoge3", now, now, false),
            AnxietyModel("hoge4", now, now, false),
            AnxietyModel("hoge5", now, now, false)
        )
    }

    // fragment_checklist　floating action button clicked.
    fun onFABClick(){
        val anxiety = AnxietyModel("hoge", LocalDateTime.now(), LocalDateTime.now(), false)
        anxietyList.add(anxiety)
        fabClickEvent.value = true
    }
}