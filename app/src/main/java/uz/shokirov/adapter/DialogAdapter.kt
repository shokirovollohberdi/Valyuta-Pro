package uz.shokirov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.shokirov.model.Valyuta
import uz.shokirov.valyutaapp.databinding.DialogExchageBinding
import uz.shokirov.valyutaapp.databinding.ItemRvDialogBinding
import java.util.*
import kotlin.collections.ArrayList

class DialogAdapter(var list: ArrayList<Valyuta>, var onClick: OnClick) : RecyclerView.Adapter<DialogAdapter.Vh>() {
    inner class Vh(var itemRv: ItemRvDialogBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(position: Int) {
            var title = getFlag(list[position].code.subSequence(0, 2).toString()) + " ${list[position].title}"
            itemRv.tvExchangeTitle.text = title
            itemRv.root.setOnClickListener {
                onClick.click(position)
            }
        }
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvDialogBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = list.size
}

interface OnClick {
    fun click(position: Int)
}