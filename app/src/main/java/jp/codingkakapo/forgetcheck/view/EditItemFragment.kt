package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.databinding.FragmentEditItemBinding
import jp.codingkakapo.forgetcheck.viewModel.EditItemViewModel

class EditItemFragment : Fragment() {

    private val vm = EditItemViewModel()
    private lateinit var binding : FragmentEditItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            else vm.hogeText.value = (view as? EditText)?.text.toString()
        }

        // ボタン保存イベ登録
        binding.button.setOnClickListener {
            vm.hogeText.value = binding.editText.text.toString()
        }
        return binding.root
    }

}