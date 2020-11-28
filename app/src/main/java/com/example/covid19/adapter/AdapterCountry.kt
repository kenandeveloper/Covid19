package com.example.covid19.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covid19.R
import com.example.covid19.model.CountriesItem
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_country.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterCountry (private val country : ArrayList<CountriesItem>, private val clickListener : (CountriesItem) -> Unit) :
    RecyclerView.Adapter<CountryViewHolder>(), Filterable{

    var countryfirstlist = ArrayList<CountriesItem>()

    init {
        countryfirstlist = country
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryfirstlist.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countryfirstlist[position], clickListener)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryfirstlist = if (charSearch.isEmpty()) {
                    country
                } else {
                    val resultList = ArrayList<CountriesItem>()
                    for (row in country) {
                        val search = row.country?.toLowerCase(Locale.ROOT) ?: ""
                        if (search.contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)

                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = countryfirstlist
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, result: FilterResults?) {
                countryfirstlist = result?.values as ArrayList<CountriesItem>
                notifyDataSetChanged()
            }

        }
    }
}


    class CountryViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind (negara : CountriesItem, clickListener : (CountriesItem) -> Unit) {
            val name_countryrv : TextView = itemView.tv_countryName
            val flag_negara : CircleImageView = itemView.img_flag_circle
            val country_totalCase : TextView = itemView.tv_countryTotalCase
            val country_totalRecovered : TextView = itemView.tv_countryRecovered
            val country_totalDeath : TextView = itemView.tv_countryDeaths

            val formatter : NumberFormat = DecimalFormat("#,###")

            name_countryrv.tv_countryName.text = negara.country
            country_totalCase.tv_countryTotalCase.text = formatter.format(negara.totalConfirmed?.toDouble())
            country_totalRecovered.tv_countryRecovered.text = formatter.format(negara.totalRecovered?.toDouble())
            country_totalDeath.tv_countryDeaths.text = formatter.format(negara.totalDeaths?.toDouble())
            Glide.with(itemView).load("https://www.countryflags.io/" + negara.countryCode + "/flat/64.png")
                .into(flag_negara)

            name_countryrv.setOnClickListener { clickListener(negara) }
            flag_negara.setOnClickListener { clickListener(negara) }
            country_totalCase.setOnClickListener { clickListener(negara) }
            country_totalRecovered.setOnClickListener { clickListener(negara) }
            country_totalDeath.setOnClickListener { clickListener(negara) }
        }
    }
