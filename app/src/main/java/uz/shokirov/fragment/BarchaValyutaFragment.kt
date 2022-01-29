package uz.shokirov.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import uz.shokirov.adapter.RvAdapter
import uz.shokirov.model.Valyuta
import uz.shokirov.utils.MyViewModels
import uz.shokirov.valyutaapp.MainActivity
import uz.shokirov.valyutaapp.databinding.FragmentBarchaValyutaBinding
import java.sql.Array

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BarchaValyutaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarchaValyutaFragment : Fragment() {
    lateinit var binding: FragmentBarchaValyutaBinding
    lateinit var rvAdapter: RvAdapter
    lateinit var myViewModel: MyViewModels
    lateinit var list: ArrayList<Valyuta>

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
        binding = FragmentBarchaValyutaBinding.inflate(layoutInflater)

        list = ArrayList()

        myViewModel = ViewModelProvider(activity!!).get(MyViewModels::class.java)
        myViewModel.getUsers().observe(viewLifecycleOwner, {
            list.addAll(it)
            rvAdapter = RvAdapter(list)
            binding.rv.adapter = rvAdapter
        })


        var main = (activity as MainActivity?)?.binding

        var search = main!!.searchView

        search.setQuery("", true);
        search.setFocusable(true);
        search.setIconified(false);
        search.requestFocusFromTouch();

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                for (adin in 0 until list.size) {
                    for (i in list[adin].code.indices) {
                        if (list[adin].code.subSequence(0, i).toString().equals(p0, ignoreCase = true)) {
                            list.add(list[adin])
                        }
                    }
                }

                rvAdapter = RvAdapter(list)
                rvAdapter.notifyDataSetChanged()
                binding.rv.adapter = rvAdapter
                return true
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BarchaValyutaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BarchaValyutaFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}