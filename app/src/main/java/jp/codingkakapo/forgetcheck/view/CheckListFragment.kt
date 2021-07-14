package jp.codingkakapo.forgetcheck.view

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.model.ForgetCheckDB
import jp.codingkakapo.forgetcheck.utils.CheckListItemAdapter
import jp.codingkakapo.forgetcheck.utils.Const
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel

class CheckListFragment : Fragment() {

    lateinit var vm : CheckListViewModel
    lateinit var binding : FragmentChecklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Contextが取れるようになってからVMをいれる
        vm = CheckListViewModel(this.context?.applicationContext as ForgetCheckApplication)

        //ListViewのオブジェクト作成
        binding = FragmentChecklistBinding.inflate(inflater, container, false)
        binding.vm = vm

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ListViewにAdapterをセット
        binding.itemList.adapter = CheckListItemAdapter(vm.anxietyList, this.context?.applicationContext as ForgetCheckApplication)

        //fabがClickされる→バインドされたVMのメソッドはしる→VMのイベントLivedataが変更される→それを観測する処理の登録（これ）
        vm.fabClickEvent.observe(viewLifecycleOwner, {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(this.id,EditItemFragment(this.vm.anxietyList)) //IDこれでええんか。。。？
            transaction.addToBackStack(null)
            transaction.commit()
        })
    }
}

