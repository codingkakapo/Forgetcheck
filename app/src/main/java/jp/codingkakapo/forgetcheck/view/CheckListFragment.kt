package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.utils.CheckListItemAdapter
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel

class CheckListFragment : Fragment() {

    private val _vm = CheckListViewModel()
    private lateinit var binding : FragmentChecklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChecklistBinding.inflate(inflater, container, false)
        binding.vm = _vm
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ListViewにAdapterをセット
        val lvm = binding.vm

        if (lvm != null) {
            binding.itemList.adapter = CheckListItemAdapter(lvm.anxietyList)
        }

    }
}

