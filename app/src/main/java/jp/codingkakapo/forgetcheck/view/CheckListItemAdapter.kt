package jp.codingkakapo.forgetcheck.view

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.LifecycleOwner
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.databinding.ViewChecklistItemBinding
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.utils.Const
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel
import kotlinx.coroutines.*

class CheckListItemAdapter(
    private var data : ObservableArrayList<AnxietyModel>,
    private var vm : CheckListViewModel,
    private var app : ForgetCheckApplication,
    private val parentLifecycleOwner: LifecycleOwner
) : BaseAdapter()  {

    init{

        // observablelistのリスナーに変更イベント登録。
        data.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<AnxietyModel>>(){
            override fun onChanged(sender: ObservableArrayList<AnxietyModel>?) {
                Log.d(Const.d,"onChanged")
            }

            override fun onItemRangeChanged(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {
                Log.d(Const.d,"onItemRangeChanged")
            }

            //addだとここしかよばれんわ。
            override fun onItemRangeInserted(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
                Log.d(Const.d,"onItemRangeInserted")
            }

            override fun onItemRangeMoved(sender: ObservableArrayList<AnxietyModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                Log.d(Const.d,"onItemRangeMoved")
            }

            //削除時はこれ
            override fun onItemRangeRemoved(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
                Log.d(Const.d,"onItemRangeRemoved")
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

            binding.lifecycleOwner = parentLifecycleOwner

            item = data[position]

            // 項目長押し時のリスナー　編集画面へ飛ぶ
            val onItemLongClickListener = View.OnLongClickListener {
                // vmに更新対象を知らせる
                vm.editTargetAnxiety = data[position]

                //Update用画面遷移
                val mainActivity = ((binding.root.context as Activity) as MainActivity)
                val tran = mainActivity.supportFragmentManager.beginTransaction()
                // ToDo 編集（更新）実装
                tran.replace(R.id.check_list_fragment, EditItemFragment())
                tran.addToBackStack(null)
                tran.commit()

                //onClickは発火させない
                false
            }

            // 単に項目クリック時は、チェックの切り替えを行うようにする
            val onItemClickListener = View.OnClickListener {
                val targetAnxiety = data[position]
                // 真偽反転、画面に変更反映される
                targetAnxiety.checked = !targetAnxiety.checked
                // DB上でも変更
                GlobalScope.launch {
                    changeAnxietyChecked(targetAnxiety)
                }
                notifyDataSetChanged()
            }

            // テキストクリック時のイベント設定
            checklistItemTextview.setOnClickListener(onItemClickListener)
            // テキスト長押し時のイベント設定
            checklistItemTextview.setOnLongClickListener(onItemLongClickListener)
            // パーツ以外の余白部分クリック時イベント設定
            checklistItemLinearLayout.setOnClickListener(onItemClickListener)
            // パーツ以外の余白部分長押し時イベント設定
            checklistItemLinearLayout.setOnLongClickListener(onItemLongClickListener)
            // CheckBoxクリック時のイベント設定
            checklistItemCheckbox.setOnClickListener(onItemClickListener)

            // 削除ボタンのイベント設定　誤クリック防止のため長押しにしておく
            checklistItemButton.setOnLongClickListener {
                //DBから削除
                val targetAnxiety = data[position]
                GlobalScope.launch {
                    deleteAnxiety(targetAnxiety)
                    Log.d(Const.d, "removebutton clicked!!!!!! $targetAnxiety")
                }
                //画面から削除
                data.removeAt(position)

                // onClickは発火させない
                false
            }
            executePendingBindings()
        }

        return binding.root
    }

    private suspend fun deleteAnxiety(anxiety : AnxietyModel){
        withContext(Dispatchers.IO){
            app.DB.AnxietyDao().delete(anxiety)
            Log.d(Const.d, "DB deleted!!!!!! $anxiety")
        }
    }

    private suspend fun changeAnxietyChecked(anxiety : AnxietyModel){
        withContext(Dispatchers.IO){
            app.DB.AnxietyDao().update(anxiety)
        }
    }

    fun replaceData(listData: ObservableArrayList<AnxietyModel>) {
        Log.d(Const.d,"replaceData")
        this.data = listData
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}