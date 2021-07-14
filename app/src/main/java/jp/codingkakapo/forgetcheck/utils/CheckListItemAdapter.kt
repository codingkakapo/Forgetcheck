package jp.codingkakapo.forgetcheck.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.databinding.ViewChecklistItemBinding
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import kotlinx.coroutines.*

class CheckListItemAdapter(
    private var data: ObservableArrayList<AnxietyModel>,
    var app : ForgetCheckApplication
) : BaseAdapter()  {

    init{

        // observablelistのリスナーに変更イベント登録。
        data.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<AnxietyModel>>(){
            override fun onChanged(sender: ObservableArrayList<AnxietyModel>?) {
            }

            override fun onItemRangeChanged(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {
            }

            //addだとここしかよばれんわ。
            override fun onItemRangeInserted(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeMoved(sender: ObservableArrayList<AnxietyModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            }

            //削除時はこれ
            override fun onItemRangeRemoved(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }
        })
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            // Binding作成
            val inflater = LayoutInflater.from(parent.context)
            ViewChecklistItemBinding.inflate(inflater, parent, false)
        } else {
            DataBindingUtil.getBinding(convertView) ?: throw IllegalStateException()
        }

        with(binding) {

            item = data[position]

            // 削除ボタンのイベント設定
            checklistItemButton.setOnClickListener(View.OnClickListener {
                //DBから削除
                val removeObj = data[position]
                GlobalScope.launch {
                    deleteAnxiety(removeObj)
                }
                //画面から削除
                data.removeAt(position)
            })

            executePendingBindings()
        }

        return binding.root
    }

    fun replaceData(listData: ObservableArrayList<AnxietyModel>) {
        this.data = listData
    }

    private suspend fun deleteAnxiety(removeObj : AnxietyModel){
        withContext(Dispatchers.IO){
            app.DB.AnxietyDao().delete(removeObj)
        }
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}