package jp.codingkakapo.forgetcheck.viewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.*
import androidx.room.Room
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.utils.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class CheckListViewModel(app: ForgetCheckApplication) : AndroidViewModel(app) {

    var anxietyList : ObservableArrayList<AnxietyModel> = ObservableArrayList<AnxietyModel>()

    init{
        //そのままやるとUIをブロックする。。。こるーちん
        viewModelScope.launch {
            anxietyList.addAll(getListContent(app))
        }
    }

    var hoge = ""

    //Boolて。Objectとかでやるべき？
    var fabClickEvent : MutableLiveData<Boolean> = MutableLiveData()

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

    // fragment_checklist　floating action button clicked.
    fun onFABClick(){
        fabClickEvent.value = true
    }
}