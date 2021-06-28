package jp.codingkakapo.forgetcheck.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.utils.Const
import java.time.LocalDateTime

class CheckListViewModel() : ViewModel() {
    lateinit var anxietyList : List<AnxietyModel>
        //MutableLiveData<List<AnxietyModel>>()

    init{
        anxietyList = getTestData()
    }

    var hoge : String = "CheckListViewModel.プロパティほげ"

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
        //do なにか
        Log.d(Const.d,"CheckListViewModel.onClick()だよ")
    }
}


/*

class ListViewModel : ViewModel() {
    private val _listItems = MutableLiveData<List<ListData>>()
    val listItems: LiveData<List<ListData>>
        get() = _listItems
}
*/