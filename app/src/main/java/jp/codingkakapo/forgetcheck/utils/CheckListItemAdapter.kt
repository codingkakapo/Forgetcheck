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
import jp.codingkakapo.forgetcheck.databinding.ViewChecklistItemBinding
import jp.codingkakapo.forgetcheck.model.AnxietyModel

class CheckListItemAdapter(
    private var data: ObservableArrayList<AnxietyModel>
) : BaseAdapter()  {

    init{

        // observablelistのリスナーに変更イベント登録。
        data.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<AnxietyModel>>(){
            override fun onChanged(sender: ObservableArrayList<AnxietyModel>?) {}

            override fun onItemRangeChanged(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {}

            //addだとここしかよばれんわ。
            override fun onItemRangeInserted(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeMoved(sender: ObservableArrayList<AnxietyModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {}

            override fun onItemRangeRemoved(sender: ObservableArrayList<AnxietyModel>?, positionStart: Int, itemCount: Int) {}
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
            handler = CheckListItemHandler()
            // Bindingオブジェクトに即反映
            executePendingBindings()
        }

        return binding.root
    }

    fun replaceData(listData: ObservableArrayList<AnxietyModel>) {
        this.data = listData
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}