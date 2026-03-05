package com.example.ch3.section4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ch3.databinding.FragmentBBinding

class BFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBBinding.inflate(inflater, container, false)

        // 전달된 데이터 획득
        val bundle = arguments
        val arg1 = bundle?.getString("arg1")
        val arg2 = bundle?.getInt("arg2")

        Toast.makeText(activity, "$arg1, $arg2", Toast.LENGTH_SHORT).show()

        binding.button6.setOnClickListener {
            val controller = Navigation.findNavController(it)
            controller.navigateUp()
        }

        return binding.root
    }
}