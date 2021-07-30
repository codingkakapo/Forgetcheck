package jp.codingkakapo.forgetcheck.view

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.activityViewModels
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.databinding.FragmentEditItemBinding
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel
import jp.codingkakapo.forgetcheck.viewModel.EditItemViewModel

class EditItemFragment()
 : Fragment() {

    // CheckListとactivityScopeでVMを共用する。Frag破棄時にVM作り直せないので。
    private val vm : CheckListViewModel by activityViewModels()
    private lateinit var binding : FragmentEditItemBinding

    init {
        // ToDo イベント処理登録
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d("-----------------EditItemFragment---------------", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Log.d("-----------------EditItemFragment---------------", "onCreateView")

        // binding初期化
        binding = FragmentEditItemBinding.inflate(inflater, container, false)

        /*
         * フォーカス外れ、保存ボタン、バックなどで保存
         */
        // テキスト入力欄フォーカスイベ登録
        binding.editText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) /* hasFocusがT・・・入力開始時nothing to do */
            else {
                val str = (view as? EditText)?.text.toString()
                vm.editUpdatedString.value = str
            }
        }

        // ボタン保存イベ登録
        binding.button.setOnClickListener {
            val str = binding.editText.text.toString()
            vm.editUpdatedString.value = str
        }
        return binding.root
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("-----------------EditItemFragment---------------", "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("-----------------EditItemFragment---------------", "onDetach")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("-----------------EditItemFragment---------------", "onActivityCreated")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("-----------------EditItemFragment---------------", "onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("-----------------EditItemFragment---------------", "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("-----------------EditItemFragment---------------", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("-----------------EditItemFragment---------------", "onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d("-----------------EditItemFragment---------------", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("-----------------EditItemFragment---------------", "onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("-----------------EditItemFragment---------------", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("-----------------EditItemFragment---------------", "onDestroy")
    }*/
}