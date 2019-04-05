package ua.yuriikot.countries.adapter


import android.databinding.DataBindingUtil
import android.graphics.drawable.PictureDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ua.yuriikot.countries.databinding.ItemListCountryBinding
import ua.yuriikot.countries.remote.model.Country


class CountriesRecyclerViewAdapter(
    private val onCountryClickListener: (Country) -> Unit
) : RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder>() {

    var countries: List<Country>? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemListCountryBinding>(
            LayoutInflater.from(parent.context),
            ua.yuriikot.countries.R.layout.item_list_country,
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countries!![position]

        with(holder.view) {
            tag = item
            setOnClickListener { onCountryClickListener.invoke(item) }
        }

        holder.binding.tvName.text = "${item.name}"
        holder.binding.tvPopulation.text = "${item.population}"
        Glide.with(holder.view).`as`(PictureDrawable::class.java).load(item.flag).into(holder.binding.ivFlag)

    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.view).clear(holder.binding.ivFlag)
    }

    override fun getItemCount(): Int = countries?.size ?: 0

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var binding: ItemListCountryBinding = DataBindingUtil.bind(view)!!
    }
}
