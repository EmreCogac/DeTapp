package com.example.detapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.R
import com.example.detapp.databinding.FragmentLoginFragmentBinding
import com.google.firebase.auth.FirebaseUser


class login_fragment : Fragment() {

    private var _binding: FragmentLoginFragmentBinding? = null
    private val binding get() = _binding!!
    var aViewModel : AuthViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aViewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)

        aViewModel?.userData?.observe(this, Observer<FirebaseUser?> { firebaseUser ->
            firebaseUser?.let {
                findNavController().navigate(R.id.action_login_fragment_to_user_fragment)
            }
        })

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginFragmentBinding.inflate(inflater,container, false)

        binding.apply {

            girisyapmadan.setOnClickListener {
                findNavController().navigate(R.id.action_login_fragment_to_user_fragment)
            }

            girisyap.setOnClickListener {
                var email = mailEditText.text.toString()
                var pass = passsEditText.text.toString()
                if (!email.isEmpty() && !pass.isEmpty()){
                    aViewModel?.login(email, pass)
                }else{
                    mailEditText.setError("herhangi bir mail girmediniz")
                    passsEditText.setError("herhangi bir lifre girmesiniz")
                }
            }
            kaydol.setOnClickListener {
                findNavController().navigate(R.id.action_login_fragment_to_signup_fragment)
            }
        }



        return binding.root
    }
}