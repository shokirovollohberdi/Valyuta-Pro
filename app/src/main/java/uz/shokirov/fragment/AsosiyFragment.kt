package uz.shokirov.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.item_tab.view.*
import uz.shokirov.adapter.HistoryAdapter
import uz.shokirov.adapter.PagerAdapterExchange
import uz.shokirov.model.Valyuta
import uz.shokirov.transformer.ZoomOutPageTransformer
import uz.shokirov.utils.MySharedPreferences
import uz.shokirov.utils.MyViewModels
import uz.shokirov.valyutaapp.R
import uz.shokirov.valyutaapp.databinding.FragmentAsosiyBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AsosiyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsosiyFragment : Fragment() {
    lateinit var binding: FragmentAsosiyBinding
    lateinit var list: ArrayList<Valyuta>
    lateinit var pagerAdapterExchange: PagerAdapterExchange
    lateinit var myViewModel: MyViewModels
    private val TAG = "AsosiyFragment"

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAsosiyBinding.inflate(layoutInflater)

        list = ArrayList()

        MySharedPreferences.init(binding.root.context)
        var listHistory = MySharedPreferences.obektString

        myViewModel = ViewModelProvider(activity!!).get(MyViewModels::class.java)
        myViewModel.getUsers().observe(viewLifecycleOwner, {
            list.addAll(it)
            list.forEach {
                if (it.nbu_cell_price == "")
                    it.nbu_cell_price = it.cb_price
                if (it.nbu_buy_price == "")
                    it.nbu_buy_price = it.cb_price
            }
            if (listHistory.isEmpty()) {
                listHistory.addAll(list)
            } else if (listHistory.isNotEmpty()) {
                if (listHistory != list) {
                    listHistory.addAll(list)
                }
            }
            binding.rvHistory.adapter = HistoryAdapter(listHistory)
            Log.d(TAG, "onCreateView: $list")
            binding.dotIndicator.visibleDotCount = 9
            binding.dotIndicator.setDotCount(list.size)
            pagerAdapterExchange = PagerAdapterExchange(list, binding.root.context)
            binding.viewpager.adapter = pagerAdapterExchange
            Toast.makeText(context!!, "${list[0].title}", Toast.LENGTH_SHORT).show()



            Log.d(TAG, "onCreateView: $list")

            pagerAdapterExchange = PagerAdapterExchange(list, binding.root.context)
            binding.viewpager.adapter = pagerAdapterExchange
            binding.viewpager.setPageTransformer(true, ZoomOutPageTransformer())
            binding.dotIndicator.setDotCount(list.size)
            binding.dotIndicator.attachToPager(binding.viewpager)
            binding.tabLayout.setupWithViewPager(binding.viewpager)
            binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER)
            binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE)

            setTab()

            binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                /**
                 * Called when a tab enters the selected state.
                 *
                 * @param tab The tab that was selected
                 */
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.customView?.tvExchangeCode?.setTextColor(Color.WHITE)
                    tab?.customView?.root?.setBackgroundResource(R.drawable.tab_back)
                }

                /**
                 * Called when a tab exits the selected state.
                 *
                 * @param tab The tab that was unselected
                 */
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.customView?.tvExchangeCode?.setTextColor(Color.GRAY)
                    tab?.customView?.root?.setBackgroundResource(R.color.white)
                }

                /**
                 * Called when a tab that is already selected is chosen again by the user. Some applications may
                 * use this action to return to the top level of a category.
                 *
                 * @param tab The tab that was reselected.
                 */
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })


        })



        return binding.root
    }

    private fun setTab() {
        val tabCount = binding.tabLayout.tabCount
        val tabList = ArrayList<String>()
        for (valyuta in list.indices) {
            tabList.add(list[valyuta].code)
        }

        for (i in 0 until tabCount) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_tab, null, false)
            val tab = binding.tabLayout.getTabAt(i)
            tab?.customView = view
            view.tvExchangeCode.text = tabList[i]
            if (i == 0) {
                view.tvExchangeCode.setTextColor(Color.WHITE)
                view.root.setBackgroundResource(R.drawable.tab_back)
            } else {
                view.tvExchangeCode.setTextColor(Color.GRAY)
                view.root.setBackgroundResource(R.color.white)
            }
        }
    }
}