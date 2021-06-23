package jp.codingkakapo.forgetcheck.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import java.time.LocalDateTime

class CheckListViewModel() : ViewModel() {
    lateinit var anxietyList : List<AnxietyModel>
        //MutableLiveData<List<AnxietyModel>>()

    init{
        anxietyList = getTestData()
    }

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

    // チェック・解除動作実装
    fun onAnxietyClick(){
    }
}

/*

class ListViewModel : ViewModel() {
    private val _listItems = MutableLiveData<List<ListData>>()
    val listItems: LiveData<List<ListData>>
        get() = _listItems
}
*/