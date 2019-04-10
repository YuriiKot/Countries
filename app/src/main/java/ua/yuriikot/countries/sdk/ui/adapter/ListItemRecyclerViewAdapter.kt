package ua.yuriikot.countries.sdk.ui.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_item.view.*
import ua.yuriikot.countries.R
import ua.yuriikot.countries.sdk.model.ListElement
import ua.yuriikot.countries.sdk.ui.fragment.ChooseItemFromListFragment.OnListFragmentInteractionListener

class ListItemRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener
) : RecyclerView.Adapter<ListItemRecyclerViewAdapter.ViewHolder>() {

    var values: List<ListElement> = ArrayList()
        set (value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listElement = values[position]

        with(holder.mView) {
            tvText.text = listElement.name
            setOnClickListener { mListener.onListItemClicked(listElement) }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvText: TextView = mView.tvText

    }
}
