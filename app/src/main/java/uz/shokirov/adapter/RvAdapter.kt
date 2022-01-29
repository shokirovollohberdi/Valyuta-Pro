package uz.shokirov.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.shokirov.model.Valyuta
import uz.shokirov.valyutaapp.databinding.ItemRvBinding
import java.util.*
import kotlin.collections.ArrayList

class RvAdapter(var list: ArrayList<Valyuta>) :
    RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(valyuta: Valyuta, position: Int) {
            var flag = valyuta.code.subSequence(0, 2)
            itemRv.tvFlag.text = getFlag(flag.toString())+" ${valyuta.code}"
            itemRv.tvSotilish.text = valyuta.nbu_buy_price
            itemRv.tvOlinish.text = valyuta.nbu_cell_price
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}