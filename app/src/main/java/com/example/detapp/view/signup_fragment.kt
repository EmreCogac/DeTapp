package com.example.detapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.R
import com.example.detapp.databinding.FragmentSignupFragmentBinding
import com.example.detapp.model.ProfileDataModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database


class signup_fragment : Fragment() {

    private var _binding: FragmentSignupFragmentBinding? = null
    private val binding get() = _binding!!
    private var aViewModel : AuthViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aViewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)

        aViewModel?.userData?.observe(this, Observer<FirebaseUser?> { firebaseUser ->
            firebaseUser?.let {
                findNavController().navigate(R.id.action_signup_fragment_to_login_fragment)
            }
        })
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentSignupFragmentBinding.inflate(inflater, container,false)

        binding.apply {

            girisyapmadan.setOnClickListener{

                findNavController().navigate(R.id.action_signup_fragment_to_post_fragment)
            }

            kaydol.setOnClickListener {
                var email = mailEditText.text.toString()
                var pass = sifreEditText.text.toString()
                var name = adEditText.text.toString()
                var soyad = soyadEditText.text.toString()
                var username = usernameEditText.text.toString()

                if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty() && soyad.isNotEmpty() && username.isNotEmpty()) {

                    val deneme =  ProfileDataModel(name,soyad,username,email,pass)
                    aViewModel?.register(deneme)

                }
                else
                {
                   Toast.makeText(context,"Boş kutuları doldurunuz!!", Toast.LENGTH_SHORT).show()
                }

            }
            loginedon.setOnClickListener {
                findNavController().navigate(R.id.action_signup_fragment_to_login_fragment)
            }
        }
        return binding.root
    }

}