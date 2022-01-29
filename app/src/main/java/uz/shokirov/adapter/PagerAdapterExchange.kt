package uz.shokirov.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.item_vp_exchange.view.*
import uz.shokirov.model.Valyuta
import uz.shokirov.valyutaapp.R
import java.util.*
import kotlin.collections.ArrayList

class PagerAdapterExchange(var list: ArrayList<Valyuta>, var context: Context) : PagerAdapter() {
    override fun getCount(): Int {
        return list.size
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
                LayoutInflater.from(container.context)
                        .inflate(R.layout.item_vp_exchange, container, false)
        container.addView(layoutInflater)


        try {
            //layoutInflater.tvFlag.text = getFlag(list[position].code.substring(0, 1))
            var flag = list[position].code.subSequence(0, 2)
            layoutInflater.tvFlag.text = getFlag(flag.toString())
        } catch (e: Exception) {

        }
        var date = list[position].date
        layoutInflater.tcData.text = date.subSequence(0, 10)
        layoutInflater.tvOlinish.text = list[position].nbu_cell_price
        layoutInflater.tvSotilish.text = list[position].nbu_buy_price


        return layoutInflater
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