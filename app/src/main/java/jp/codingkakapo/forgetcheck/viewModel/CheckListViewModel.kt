package jp.codingkakapo.forgetcheck.viewModel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.model.AppDataModel
import jp.codingkakapo.forgetcheck.utils.Const
import jp.codingkakapo.forgetcheck.utils.observeSingle
import jp.codingkakapo.forgetcheck.utils.singleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.concurrent.timer

class CheckListViewModel(var app: ForgetCheckApplication) : AndroidViewModel(app) {
    class Factory constructor(private val app: ForgetCheckApplication): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CheckListViewModel(this.app) as T
        }
    }

    var anxietyList : ObservableArrayList<AnxietyModel> = ObservableArrayList<AnxietyModel>()
    var date : MutableLiveData<String> = MutableLiveData<String>()
    var editTargetAnxiety : AnxietyModel? = null
    var editUpdatedString : MutableLiveData<String> = MutableLiveData<String>()
    var fabClickEvent = singleLiveData<Boolean>()
    var resetButtonClickEvent = singleLiveData<Boolean>()
    var dataSetChangedEvent : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    // 文字列がUIから更新されるのを監視するObserver
    private val onUpdateObserver : Observer<String> = Observer {
        if(editTargetAnxiety != null){
            viewModelScope.launch {
                updateAnxieties(app, editTargetAnxiety, it)
                Log.d(Const.d,"update anxieties!!!!!!!!!!!")
            }
        } else {
            viewModelScope.launch {
                insertAnxieties(app, it)
                Log.d(Const.d,"insert anxieties!!!!!!!!!!!")
            }
        }
    }


    init{

        // 文字列が更新されたときの処理（Observer）を登録
        //editUpdatedString.observeForever(onUpdateObserver)
        editUpdatedString.observeForever(onUpdateObserver)

        // 前回起動から1日経っていたらAnxietyのCheckを全て外す
        viewModelScope.launch {
            if(judgeReset(checkLastLaunched())) uncheckAllAnxieties()
        }

        // リスト読み込み
        viewModelScope.launch {
            anxietyList.addAll(getListContent(app))
        }

        // 日付更新処理はしらせとく
        timer(name = "forgetCheckTimer", period = 10000) {
            viewModelScope.launch {
                updateTime(Calendar.getInstance())
            }
        }
    }

    // 最後の起動を確認、なければ今回を最終起動として保存。
    private suspend fun checkLastLaunched() : LocalDateTime {
        val lastLaunched : LocalDateTime
        val dat : AppDataModel?
        val now = LocalDateTime.now()
        withContext(Dispatchers.IO){
            dat = app.DB.AppDataDao().selectAll().firstOrNull()
        }
        // データがあるか確認
        if(dat == null){
            lastLaunched = now
            // なければDBに作成
            withContext(Dispatchers.IO){
                app.DB.AppDataDao().insert(AppDataModel(0, now))
            }
        } else {
            //　DBの最終起動を更新
            lastLaunched = dat.lastLaunched
            dat.lastLaunched = now
            withContext(Dispatchers.IO){
                app.DB.AppDataDao().update(dat)
            }
        }
        return lastLaunched
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

    // リストの中身を入れる
    private suspend fun getListContent(app : ForgetCheckApplication) : List<AnxietyModel>{
        return withContext(Dispatchers.IO){
            app.DB.AnxietyDao().selectAll()
        }
    }

    // 最終起動から1日以上経っているかの判断 now - last が1d以上ならリセット
    private fun judgeReset(last : LocalDateTime) : Boolean {
        val now = LocalDateTime.now()
        val diffYears = ChronoUnit.YEARS.between(last, now)
        val diffMonths = ChronoUnit.MONTHS.between(last, now)
        val diffDays = ChronoUnit.DAYS.between(last, now)
        return diffYears > 1 || diffMonths > 1 || diffDays > 1
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

    // ToDo 移動したので合わせて修正すること。新規登録処理
    private suspend fun insertAnxieties(app : ForgetCheckApplication, text : String?){
        if(text == null) throw NullPointerException()

        var newId: Long
        val newAnxiety = AnxietyModel(0, text, LocalDateTime.now(), LocalDateTime.now(), false)

        // DBに追加
        withContext(Dispatchers.IO){
            // Insertの戻り値でIDを確定させてから画面に反映
            newId = app.DB.AnxietyDao().insert(newAnxiety)
            newAnxiety.id = newId.toInt()
            // 画面に追加
            anxietyList.add(newAnxiety)
        }


    }

    // ToDo EditTextの文字更新時はもとのやつ出す　こちらも修正必要　編集処理
    private suspend fun updateAnxieties(app : ForgetCheckApplication, updateTarget : AnxietyModel?, newStr : String?){
        if(newStr == null || updateTarget == null) throw NullPointerException()

        updateTarget.name = newStr

        withContext(Dispatchers.IO){
            // DBを更新
            app.DB.AnxietyDao().update(updateTarget)
        }
    }


    // フローティングアクションボタンクリックイベント、View側でObserve
    fun onFABClick(){
        fabClickEvent.value = true
    }

    // リセットボタンクリックイベント、View側でObserve
    fun onResetButtonClick(){
        resetButtonClickEvent.value = true
    }

    // ToDo　Adapterのやつと合併してどうにかしたい、冗長。
    private suspend fun changeAnxietyChecked(anxiety : AnxietyModel){
        withContext(Dispatchers.IO){
            app.DB.AnxietyDao().update(anxiety)
        }
        // 画面側に反映する必要あるので呼ぶ、ObserveしてるFrag上でNotifyが呼ばれて画面更新
        dataSetChangedEvent.value = true
    }

    // VM破棄時に購読解除
    override fun onCleared() {
        super.onCleared()
        editUpdatedString.removeObserver(onUpdateObserver)
    }

}