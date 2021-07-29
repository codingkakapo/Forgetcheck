package jp.codingkakapo.forgetcheck.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.utils.CheckListItemAdapter
import jp.codingkakapo.forgetcheck.utils.Const
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel

class CheckListFragment : Fragment() {

    val vm : CheckListViewModel by activityViewModels()
    lateinit var binding : FragmentChecklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        //Contextが取れるようになってからVMをいれる
        // ToDo VMのカスタムプロバイダ→Initialize
        //vm = CheckListViewModel(this.context?.applicationContext as ForgetCheckApplication)

        //ListViewのオブジェクト作成 ・・・なんか冗長
        binding = FragmentChecklistBinding.inflate(inflater, container, false)
        binding.vm = this.vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ListViewにAdapterをセット
        binding.itemList.adapter = CheckListItemAdapter(vm.anxietyList, this.context?.applicationContext as ForgetCheckApplication, viewLifecycleOwner)

        //fabがClickされる→バインドされたVMのメソッドはしる→VMのイベントLivedataが変更される→それを観測する処理の登録（これ）
        vm.fabClickEvent.observe(viewLifecycleOwner, {
            val transaction = parentFragmentManager.beginTransaction()
            // ToDo 新規登録処理修正
            //transaction.replace(this.id,EditItemFragment(this.vm.anxietyList)) //IDこれでええんか。。。？
            transaction.replace(this.id, EditItemFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        })

        // リセットボタンの処理
        vm.resetButtonClickEvent.observe(viewLifecycleOwner,{
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.ok
                    ) { dialog, id ->
                        // User clicked OK button
                        Log.d(Const.d,"ok clicked")
                        vm.uncheckAllAnxieties()
                    }
                    setNegativeButton(
                        R.string.cancel
                    ) { dialog, id ->
                        // User cancelled the dialog
                        Log.d(Const.d,"cancel clicked")
                    }
                }
                // Set other dialog properties
                builder.setMessage(R.string.uncheck_message)

                // Create the AlertDialog
                builder.create()
            }
            alertDialog?.show()
        })

        // ViewModel側からデータセット変更されたとき画面を更新
        vm.dataSetChangedEvent.observe(viewLifecycleOwner, {
            (binding.itemList.adapter as CheckListItemAdapter).notifyDataSetChanged()
        })

    }
}

