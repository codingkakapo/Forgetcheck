package jp.codingkakapo.forgetcheck.viewModel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.*
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class EditItemViewModel(
    app : ForgetCheckApplication,
    var anxietyList: ObservableArrayList<AnxietyModel>,
    var anxiety : AnxietyModel? = null) : AndroidViewModel(app) {

    val hogeText : MutableLiveData<String> by lazy {
        MutableLiveData<String>("HELLO WORLD")
    }

    // Editになんか入力されて変更反映されるべき時点で呼ばれる
    val updatedStrings : MutableLiveData<String> = MutableLiveData()

    init{
        // 確認用の表示
        hogeText.observeForever{}

        // 変更時イベント、　DBにInsert
        updatedStrings.observeForever {
            // anxietyが空なら、新規作成
            // 入ってれば、そのanxietyを更新
            if(anxiety == null) viewModelScope.launch { insertAnxieties(app,it) }
            else viewModelScope.launch { updateAnxieties(app, anxiety , it) }
        }
    }

    private suspend fun insertAnxieties(app : ForgetCheckApplication, text : String){
        val newAnxiety = AnxietyModel(0, text, LocalDateTime.now(), LocalDateTime.now(), false)

        // 画面に追加
        anxietyList.add(newAnxiety)

        // DBに追加
         withContext(Dispatchers.IO){
             app.DB.AnxietyDao().insert(newAnxiety)
         }
    }

    // ToDo EditTextの文字更新時はもとのやつ出す
    private suspend fun updateAnxieties(app : ForgetCheckApplication, updateTarget : AnxietyModel?, newStr : String){
        if(updateTarget == null) throw Exception()

        updateTarget.name = newStr

        withContext(Dispatchers.IO){
            // DBを更新
            app.DB.AnxietyDao().update(updateTarget)
        }
    }
}