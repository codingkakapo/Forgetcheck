package jp.codingkakapo.forgetcheck.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.R
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.utils.Const
import jp.codingkakapo.forgetcheck.utils.observeSingle
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel

class CheckListFragment : Fragment() {

    val vm : CheckListViewModel by activityViewModels()
    lateinit var binding : FragmentChecklistBinding
    private var alertDialog : AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d("*****************CheckListFragment***************", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Log.d("*****************CheckListFragment***************", "onCreateView")

        //ListViewのオブジェクト作成 ・・・なんか冗長
        binding = FragmentChecklistBinding.inflate(inflater, container, false)
        binding.vm = this.vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Log.d("*****************CheckListFragment***************", "onActivityCreated")
        // toolbarをセット
        // binding.checklistToolbar.inflateMenu(R.menu.toolbar_menu)

        // ListViewにAdapterをセット
        binding.itemList.adapter = CheckListItemAdapter(vm.anxietyList, vm, this.context?.applicationContext as ForgetCheckApplication, viewLifecycleOwner)

        // イベントが発火し続けないよう拡張（observeSingle）を使用
        vm.fabClickEvent.observeSingle(viewLifecycleOwner){
            //　新規登録時は更新対象がないのでnull入れる　
            vm.editTargetAnxiety = null

            val id = this.id
            parentFragmentManager.commit {
                replace<EditItemFragment>(id)
                addToBackStack(null)
            }
        }

        // リセットボタンの処理
        vm.resetButtonClickEvent.observeSingle(viewLifecycleOwner){
            alertDialog = activity?.let {
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
        }

        // ViewModel側からデータセット変更されたとき画面を更新
        vm.dataSetChangedEvent.observe(viewLifecycleOwner, {
            (binding.itemList.adapter as CheckListItemAdapter).notifyDataSetChanged()
        })

    }


    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("*****************CheckListFragment***************", "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("*****************CheckListFragment***************", "onDetach")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("*****************CheckListFragment***************", "onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("*****************CheckListFragment***************", "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("*****************CheckListFragment***************", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("*****************CheckListFragment***************", "onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d("*****************CheckListFragment***************", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("*****************CheckListFragment***************", "onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("*****************CheckListFragment***************", "onDestroyView")
    }*/

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.dismiss()
        Log.d("*****************CheckListFragment***************", "onDestroy")
    }
}

