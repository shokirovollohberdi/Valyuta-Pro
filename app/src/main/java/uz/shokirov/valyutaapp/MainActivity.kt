package uz.shokirov.valyutaapp

import android.R
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.roughike.bottombar.OnTabSelectListener
import com.roughike.bottombar.TabSelectionInterceptor
import uz.shokirov.adapter.FragmentStateAapter
import uz.shokirov.valyutaapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var adapter: FragmentStateAapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imageMenu.setOnClickListener {
            binding.drawableLayout.openDrawer(Gravity.LEFT)
        }
        adapter = FragmentStateAapter(supportFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter
        binding.viewpager.isUserInputEnabled = false

        binding.bottomNavigationBar.setOnTabSelectListener { tabId ->
            when (tabId) {
                uz.shokirov.valyutaapp.R.id.tab_home -> {
                    binding.viewpager.currentItem = 0
                }
                uz.shokirov.valyutaapp.R.id.tab_all -> {
                    binding.viewpager.currentItem = 1
                }
                uz.shokirov.valyutaapp.R.id.tab_calculate -> {
                    binding.viewpager.currentItem = 2
                }
            }
            checkSearch()
        }

    }

    private fun checkSearch() {
        if (binding.viewpager.currentItem == 1) {
            binding.cardView.visibility = View.VISIBLE
        } else {
            binding.cardView.visibility = View.INVISIBLE
        }
    }
}