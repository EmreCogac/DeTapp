package com.example.detapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.detapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupWithNavController(binding.bottombar, navController)
        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, id, _ ->
                binding.bottombar.visibility =
                    when (id.id) {
                        R.id.login_fragment, R.id.signup_fragment -> View.GONE
                        else -> View.VISIBLE
                    }

            }
        binding.bottombar.setOnItemSelectedListener  { menuItem ->
            when (menuItem.itemId) {
                R.id.profil -> {
                    changeFragment(R.id.user_fragment)
//                    Toast.makeText(this, "Profile basıldı", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.postolustur -> {
                    changeFragment(R.id.create_post_fragment3)
//                    Toast.makeText(this, "post olustur basıldı", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.kitaps -> {
                    changeFragment(R.id.post_fragment)
//                    Toast.makeText(this, "postlara basıldı", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
    private fun changeFragment(id: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(id)
    }

}