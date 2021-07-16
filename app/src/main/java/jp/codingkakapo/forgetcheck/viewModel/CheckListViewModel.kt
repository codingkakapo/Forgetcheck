package jp.codingkakapo.forgetcheck.viewModel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.timer

class CheckListViewModel(var app: ForgetCheckApplication) : AndroidViewModel(app) {

    var anxietyList : ObservableArrayList<AnxietyModel> = ObservableArrayList<AnxietyModel>()

    var date : MutableLiveData<String> = MutableLiveData<String>()

    //Boolて。Objectとかでやるべき？
    var fabClickEvent : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var resetButtonClickEvent : MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    init{


        // リスト読み込み
        viewModelScope.launch {
            anxietyList.addAll(getListContent(app))
        }

        // 日付更新処理はしらせとく
        timer(name = "testTimer", period = 10000) {
            viewModelScope.launch {
                updateTime(Calendar.getInstance())
            }
        }
    }

    private suspend fun updateTime(calendar : Calendar){
        val month = calendar.get(Calendar.MONTH) + 1 //Javaだと-1で出るので
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dow = when(calendar.get(Calendar.DAY_OF_WEEK)){
            1 -> "日"
            2 -> "月"
            3 -> "火"
            4 -> "水"
            5 -> "木"
            6 -> "金"
            7 -> "土"
            else -> ""
        }

        withContext(Dispatchers.Main){
            date.value = "${month}月 ${day}日 （${dow}）"
        }
    }

    // リポジトリにするほどではない・・・たぶん　リストの中身を入れる
    private suspend fun getListContent(app : ForgetCheckApplication) : List<AnxietyModel>{
        return withContext(Dispatchers.IO){
            app.DB.AnxietyDao().selectAll()
        }
    }

    // 表示テスト用のリスト
    private fun getTestData() : List<AnxietyModel>{
        val now = LocalDateTime.now()
         return listOf<AnxietyModel>(
            AnxietyModel(1,"hoge1", now, now, false),
            AnxietyModel(2,"hoge2", now, now, false),
            AnxietyModel(3,"hoge3", now, now, false),
            AnxietyModel(4,"hoge4", now, now, false),
            AnxietyModel(5,"hoge5", now, now, false)
        )
    }

    // 全部のチェックを解除する
    fun uncheckAllAnxieties(){
        for(anxiety in anxietyList){
            anxiety.checked = false
            viewModelScope.launch {
                changeAnxietyChecked(anxiety)
            }
        }
    }

    // fragment_checklist　floating action button clicked.
    fun onFABClick(){
        fabClickEvent.value = true
    }

    // リセットボタンクリック
    fun onResetButtonClick(){
        resetButtonClickEvent.value = true
    }

    // Adapterのやつと合併してどうにかしたい、冗長。
    private suspend fun changeAnxietyChecked(anxiety : AnxietyModel){
        withContext(Dispatchers.IO){
            app.DB.AnxietyDao().update(anxiety)
        }
    }

}