package com.example.detapp.view

import android.annotation.SuppressLint
import android.os.Binder
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel
import com.example.detapp.R
import com.example.detapp.databinding.FragmentLoginFragmentBinding
import com.example.detapp.databinding.FragmentSignupFragmentBinding
import com.google.firebase.auth.FirebaseUser


class signup_fragment : Fragment() {


    private var _binding: FragmentSignupFragmentBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null
    var aViewModel : AuthViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aViewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)

        aViewModel?.userData?.observe(this, Observer<FirebaseUser?> { firebaseUser ->
            firebaseUser?.let {
                navController?.navigate(R.id.action_login_fragment_to_signup_fragment)
            }
        })



    }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentSignupFragmentBinding.inflate(inflater, container,false)
        binding.apply {
            kaydol.setOnClickListener {
                var email = mailEditText.text.toString()
                var pass = sifreEditText.text.toString()
                if (!email.isEmpty() && !pass.isEmpty())
                {
                    aViewModel?.register(email, pass)
                }
                else{
                    mailEditText.setError("herhangi bir mail girmediniz")
                    sifreEditText.setError("herhangi bir lifre girmesiniz")
                }

            }
        }
        return binding.root
    }

}