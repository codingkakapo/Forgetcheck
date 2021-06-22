package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.codingkakapo.forgetcheck.databinding.FragmentChecklistBinding
import jp.codingkakapo.forgetcheck.viewModel.CheckListViewModel

class CheckListFragment : Fragment() {

    //private val _vm = CheckListViewModel()
    private lateinit var binding : FragmentChecklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FragmentChecklistBinding.inflate(inflater, container, false).vm = CheckListViewModel()
        return binding.root
        //return inflater.inflate(R.layout.fragment_checklist, container, false)
    }
}