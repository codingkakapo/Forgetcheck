package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.codingkakapo.forgetcheck.ForgetCheckApplication
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.utils.CheckListItemAdapter
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

        //ListViewのオブジェクト作成 ・・・なんか冗長
        binding = FragmentChecklistBinding.inflate(inflater, container, false)
        binding.vm = this.vm
        binding.lifecycleOwner = viewLifecycleOwner

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

        //テスト用
        /*binding.testButton.setOnClickListener{
            vm.date.value = "おされました"
        }*/
    }
}

