package jp.codingkakapo.forgetcheck.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import jp.codingkakapo.forgetcheck.databinding.ViewChecklistItemBinding
import jp.codingkakapo.forgetcheck.model.AnxietyModel

class CheckListItemAdapter(
    private var data: List<AnxietyModel>
) : BaseAdapter()  {
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

    fun replaceData(listData: List<AnxietyModel>) {
        this.data = listData
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}

/*
interface MainEventHandler {
    fun onItemClick(parent : AdapterView<Adapter>, v: View, position: Int, id : Long){
        Log.d("debugg", "onclicktest")
    }
}
 */