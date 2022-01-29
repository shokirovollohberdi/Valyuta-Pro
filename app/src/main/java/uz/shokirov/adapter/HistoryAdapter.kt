package uz.shokirov.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.shokirov.model.Valyuta
import uz.shokirov.valyutaapp.databinding.ItemHistoryBinding

class HistoryAdapter(var list: ArrayList<Valyuta>) : RecyclerView.Adapter<HistoryAdapter.Vh>() {
    inner class Vh(var itemRv: ItemHistoryBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(valyuta: Valyuta, position: Int) {
            itemRv.itemDate.text = "${valyuta.date.subSequence(0, 11)}"
            itemRv.itemHour.text = "${valyuta.date.subSequence(11, valyuta.date.length - 3)}"
            itemRv.itemCell.text = valyuta.nbu_cell_price+" UZS"
            itemRv.tvBuy.text = valyuta.nbu_buy_price + " UZS"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemHistoryBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}