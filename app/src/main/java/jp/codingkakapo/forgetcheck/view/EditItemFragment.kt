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
import androidx.fragment.app.activityViewModels
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.databinding.FragmentEditItemBinding
import jp.codingkakapo.forgetcheck.model.AnxietyModel
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel
import jp.codingkakapo.forgetcheck.viewModel.EditItemViewModel

// AnxietyModelを受け取った時のみ更新モードとして動作する。
class EditItemFragment()
    //var anxietyList: ObservableArrayList<AnxietyModel>, var anxiety : AnxietyModel? = null
 : Fragment() {

    // CheckListとactivityScopeでVMを共用する。Frag破棄時にVM作り直せないので。
    private val vm : CheckListViewModel by activityViewModels()
    private lateinit var binding : FragmentEditItemBinding

    init {
        this.retainInstance
        // ToDo イベント処理登録
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //VMいれる anxietyがnullなら新規登録、入ってれば更新
        //if(anxiety == null) vm = EditItemViewModel(this.context?.applicationContext as ForgetCheckApplication, anxietyList)
        //else vm = EditItemViewModel(this.context?.applicationContext as ForgetCheckApplication, anxietyList, anxiety)

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

}