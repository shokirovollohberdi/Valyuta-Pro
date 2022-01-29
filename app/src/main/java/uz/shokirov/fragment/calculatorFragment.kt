package uz.shokirov.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import uz.shokirov.adapter.DialogAdapter
import uz.shokirov.adapter.OnClick
import uz.shokirov.model.Valyuta
import uz.shokirov.utils.CalculateObjects.fromPosition
import uz.shokirov.utils.CalculateObjects.toPosition
import uz.shokirov.utils.MyViewModels
import uz.shokirov.valyutaapp.databinding.DialogExchageBinding
import uz.shokirov.valyutaapp.databinding.FragmentCalculatorBinding
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [calculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class calculatorFragment : Fragment() {
    lateinit var binding: FragmentCalculatorBinding
    lateinit var dialogAdapter: DialogAdapter
    lateinit var listExchange: ArrayList<String>
    lateinit var myViewModels: MyViewModels
    lateinit var list: ArrayList<Valyuta>
    private val TAG = "calculatorFragment"

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

    @SuppressLint("SetTextI18n", "UseRequireInsteadOfGet")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCalculatorBinding.inflate(layoutInflater)



        list = ArrayList()

        myViewModels = ViewModelProvider(activity!!).get(MyViewModels::class.java)
        myViewModels.getUsers().observe(viewLifecycleOwner, {
            list.addAll(it)
            listExchange = ArrayList()
            for (valyuta in list.indices) {
                listExchange.add(list[valyuta].title)
            }
            list.add(Valyuta("1", "uzb", "1", "1", "1", "Uzbek so'mi"))

            try {
                binding.fromText.text = getFlag(list[fromPosition].code.subSequence(0, 2).toString()) + " ${list[fromPosition].title}"
                binding.toText.text = getFlag(list[toPosition].code.subSequence(0, 2).toString()) + " ${list[toPosition].title}"
            } catch (E: Exception) {
                binding.fromText.text = "$E UZS"
                binding.fromText.text = "$E UZS"
            }

            binding.fromText.setOnClickListener {

                val alertDialog = AlertDialog.Builder(context).create()
                val dialogView = DialogExchageBinding.inflate(layoutInflater)
                alertDialog.setView(dialogView.root)

                dialogView.dialogRv.adapter = DialogAdapter(list, object : OnClick {
                    @SuppressLint("SetTextI18n")
                    override fun click(position: Int) {
                        fromPosition = position
                        alertDialog.cancel()
                        var flag = list[fromPosition].code.subSequence(0, 2)
                        binding.fromText.text = getFlag(flag.toString()) + " ${list[fromPosition].title}"
                        binding.tvOlinish.text = list[position].nbu_cell_price
                        binding.tvSotilish.text = list[position].nbu_buy_price
                    }
                })
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                alertDialog.show()

            }

            binding.toText.setOnClickListener {
                val alertDialog = AlertDialog.Builder(context).create()
                val dialogView = DialogExchageBinding.inflate(layoutInflater)
                alertDialog.setView(dialogView.root)

                dialogView.dialogRv.adapter = DialogAdapter(list, object : OnClick {
                    @SuppressLint("SetTextI18n")
                    override fun click(position: Int) {
                        toPosition = position
                        alertDialog.cancel()
                        var flag = list[toPosition].code.subSequence(0, 2)
                        binding.toText.text = getFlag(flag.toString()) + " ${list[toPosition].title}"
                    }
                })
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                alertDialog.show()
            }

            var textFrom = list[fromPosition].cb_price.toString().toDouble()
            var textTo = list[toPosition].cb_price.toString().toDouble()


            binding.edtText.addTextChangedListener {
                if (it.toString().trim().isNotEmpty()) {
                    Log.d(TAG, "onCreateView: $fromPosition  $toPosition")
                    var input = binding.edtText.text.toString().toDouble()
                    var resultCell = textFrom * input / textTo
                    binding.tvResult.text = resultCell.toString()
                }

            }

        })




        return binding.root
    }

    fun getFlag(countryCode: String): String {
        return countryCode
                .toUpperCase(Locale.US).map { char ->
                    Character.codePointAt("$char", 0) - 0x41 + 0x1F1E6
                }
                .map { codePoint ->
                    Character.toChars(codePoint)
                }
                .joinToString(separator = "") { charArray ->
                    String(charArray)
                }
    }
}