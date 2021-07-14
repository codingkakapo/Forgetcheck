package jp.codingkakapo.forgetcheck.view

import android.app.Application
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.ObservableArrayList
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.databinding.FragmentEditItemBinding
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.viewModel.EditItemViewModel

class EditItemFragment(var anxietyList: ObservableArrayList<AnxietyModel>) : Fragment() {

    private lateinit var vm : EditItemViewModel
    private lateinit var binding : FragmentEditItemBinding

    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //VMいれる
        vm = EditItemViewModel(this.context?.applicationContext as ForgetCheckApplication, anxietyList)

        // binding初期化
        binding = FragmentEditItemBinding.inflate(inflater, container, false)

        // テストのためテキストを変えてみる（あとでけす）
        vm.hogeText.observe(viewLifecycleOwner,{
            binding.textView.text = it
        })

        /*
         * フォーカス外れ、保存ボタン、バックなどで保存
         */
        // テキスト入力欄フォーカスイベ登録
        binding.editText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) /* hasFocusがT・・・入力開始時nothing to do */
            else {
                val str = (view as? EditText)?.text.toString()
                vm.hogeText.value = str
                vm.updatedStrings.value = str
            }
        }

        // ボタン保存イベ登録
        binding.button.setOnClickListener {
            val str = binding.editText.text.toString()
            vm.hogeText.value = str
            vm.updatedStrings.value = str
        }
        return binding.root
    }

}