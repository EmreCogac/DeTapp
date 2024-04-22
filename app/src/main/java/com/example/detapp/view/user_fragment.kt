package com.example.detapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.detapp.R
import com.example.detapp.databinding.FragmentUserFragmentBinding
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class user_fragment : Fragment() {

    private var _binding:   FragmentUserFragmentBinding? = null
    private val binding get() = _binding !!
    private var aViewModel : AuthViewModel? = null
    private var usermail: String? = null
    private var profileViewModel: ProfileViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aViewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!))[AuthViewModel::class.java]

        profileViewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!))[ProfileViewModel::class.java]

    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserFragmentBinding.inflate(inflater, container, false)

        binding.apply {
            aViewModel?.userData?.observe(viewLifecycleOwner, Observer<FirebaseUser?> { firebaseUser ->
                firebaseUser?.let {
                    geridon.visibility = View.GONE
                    cikis.visibility =View.VISIBLE
                    ad.visibility = View.VISIBLE
                    soyad.visibility = View.VISIBLE
                    mail.visibility = View.VISIBLE

                    usermail = firebaseUser.email.toString()
                    val profileLiveData = profileViewModel?.deneme()
//                    val profileInfoData = profileViewModel?.profileDeneme()
//
//                    viewLifecycleOwner.lifecycleScope.launch {
//                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                            profileInfoData?.collect { data ->
//
//                                hesap.text = data.username
//                                ad.text = data.name
//                                soyad.text = data.surname
//                                mail.text = data.email
//
//
//                            }
//                        }
//
//                    }


                   profileLiveData?.observe(viewLifecycleOwner, Observer { profileDataModel ->

                        val name = profileDataModel.name
                        val surname = profileDataModel.surname
                        val username = profileDataModel.username
                        val email = profileDataModel.email
                       if (name.isNotEmpty() && surname.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty()){
                           hesap.text = "kullanıcı adı: $username"
                           ad.text = "Adı: $name"
                           soyad.text = "Soyado: $surname"
                           mail.text = "Maili: $email"
                       }
                    })


                }

            })
            hesap.text = "Kullanıcı bulunamadı"

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