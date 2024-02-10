package com.example.detapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.R
import com.example.detapp.databinding.FragmentUserFragmentBinding
import com.google.firebase.auth.FirebaseUser


class user_fragment : Fragment() {

    private var _binding:   FragmentUserFragmentBinding? = null
    private val binding get() = _binding !!
    var aViewModel : AuthViewModel? = null
    var usermail: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aViewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)

        aViewModel?.userData?.observe(this, Observer<FirebaseUser?> { firebaseUser ->

        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            aViewModel?.userData?.observe(viewLifecycleOwner, Observer<FirebaseUser?> { firebaseUser ->
                firebaseUser?.let {
                    usermail = firebaseUser.email.toString()
                    hesap.text = usermail
                    geridon.visibility = View.GONE
                    cikis.visibility =View.VISIBLE
                }


            })

            geridon.setOnClickListener {
                findNavController().navigate(R.id.action_user_fragment_to_login_fragment)
            }
            cikis.setOnClickListener {
                aViewModel?.signOut()
                findNavController().navigate(R.id.action_user_fragment_to_login_fragment)
            }
        }
        return binding.root
    }
}