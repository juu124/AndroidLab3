package com.example.ch3.section4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ch3.R
import com.example.ch3.databinding.FragmentABinding

class AFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentABinding.inflate(inflater, container, false)

        binding.button6.setOnClickListener {
            val controller = Navigation.findNavController(it)
            // nav graph 에 등록되어 있는 프래그먼트의 id로 화면전환 : action의 id로 이동
//            controller.navigate(R.id.BFragment)

            // 화면 전환시 데이터 전달
            val bundle = Bundle()
            bundle.putString("arg1", "hello")
            bundle.putInt("arg2", 10)

            // action의 id로 화면 전환
            controller.navigate(R.id.action_AFragment_to_BFragment, bundle)
        }

        return binding.root
    }
}