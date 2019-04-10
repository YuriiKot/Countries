package ua.yuriikot.countries.sdk.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.input_fragment.*
import ua.yuriikot.countries.R
import ua.yuriikot.countries.sdk.FlowViewModel
import ua.yuriikot.countries.sdk.model.InputWidget
import ua.yuriikot.countries.sdk.model.Screen
import ua.yuriikot.countries.utils.extension.nonNullObserve

class InputFragment : Fragment() {

    private lateinit var viewModel: FlowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.input_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(FlowViewModel::class.java)
        viewModel.currentScreen.nonNullObserve(this, this::initWithScreen)

        val previousText = viewModel.runStringQuery(datePickerWidget.from)
    }

    fun initWithScreen(screen: Screen) {
        etInput.hint = (screen.widget as InputWidget).hint
    }

}
