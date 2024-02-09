package com.example.detapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel
import com.example.detapp.R
import com.example.detapp.databinding.FragmentLoginFragmentBinding
import com.example.detapp.databinding.FragmentSignupFragmentBinding
import com.google.firebase.auth.FirebaseUser


class login_fragment : Fragment() {
    private var _binding: FragmentLoginFragmentBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null
    var aViewModel : AuthViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aViewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)

        aViewModel?.userData?.observe(this, Observer<FirebaseUser?> { firebaseUser ->
            firebaseUser?.let {
                navController?.navigate(R.id.action_login_fragment_to_user_fragment)

            }
        })



    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginFragmentBinding.inflate(inflater,container, false)

        binding.apply {

            girisyap.setOnClickListener {
                var email = mailEditText.text.toString()
                var pass = passsEditText.text.toString()
                    aViewModel?.login(email, pass)
            }
            kaydol.setOnClickListener {
                view?.findNavController()?.navigate(R.id.action_login_fragment_to_signup_fragment)
                Toast.makeText(context, "Giriş yap butonuna tıklandı", Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }
}