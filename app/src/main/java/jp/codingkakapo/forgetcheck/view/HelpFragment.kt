package jp.codingkakapo.forgetcheck.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.codingkakapo.forgetcheck.databinding.FragmentHelpBinding


class HelpFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHelpBinding.inflate(inflater,container,false)

        binding.helpCloseButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}