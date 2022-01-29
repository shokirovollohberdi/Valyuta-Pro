package uz.shokirov.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.shokirov.fragment.AsosiyFragment
import uz.shokirov.fragment.BarchaValyutaFragment
import uz.shokirov.fragment.calculatorFragment

class FragmentStateAapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                return AsosiyFragment()
            }
            1 -> {
                return BarchaValyutaFragment()
            }
            2 -> {
                return calculatorFragment()
            }
            else -> AsosiyFragment()
        }
    }
}