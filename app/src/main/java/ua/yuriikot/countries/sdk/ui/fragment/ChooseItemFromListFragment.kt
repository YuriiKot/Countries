package ua.yuriikot.countries.sdk.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_item_list.*
import ua.yuriikot.countries.R
import ua.yuriikot.countries.sdk.FlowViewModel
import ua.yuriikot.countries.sdk.model.ChooseListWidget
import ua.yuriikot.countries.sdk.model.ListElement
import ua.yuriikot.countries.sdk.model.Screen
import ua.yuriikot.countries.sdk.ui.adapter.ListItemRecyclerViewAdapter
import ua.yuriikot.countries.utils.extension.nonNullObserve

class ChooseItemFromListFragment : Fragment() {

    private lateinit var viewModel: FlowViewModel
    lateinit var listItemRecyclerViewAdapter: ListItemRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        listItemRecyclerViewAdapter = ListItemRecyclerViewAdapter(object :
            OnListFragmentInteractionListener {
            override fun onListItemClicked(listElement: ListElement) {
                viewModel.applyListElementAnswer(listElement)
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(FlowViewModel::class.java)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listItemRecyclerViewAdapter
        viewModel.currentScreen.nonNullObserve(this, this::initWithScreen)
    }

    fun initWithScreen(screen: Screen) {
        listItemRecyclerViewAdapter.values = (screen.widget as ChooseListWidget).elements
    }

    interface OnListFragmentInteractionListener {
        fun onListItemClicked(listElement: ListElement)
    }


}
