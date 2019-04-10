package ua.yuriikot.countries.sdk

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.flow_activity.*
import ua.yuriikot.countries.R
import ua.yuriikot.countries.sdk.model.*
import ua.yuriikot.countries.sdk.ui.fragment.ChooseItemFromListFragment
import ua.yuriikot.countries.sdk.ui.fragment.InputFragment
import ua.yuriikot.countries.utils.extension.nonNullObserve

class FlowActivity : AppCompatActivity() {
    private lateinit var viewModel: FlowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flow_activity)

        viewModel = ViewModelProviders.of(this).get(FlowViewModel::class.java)
        viewModel.onToast = {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        val flow = Gson().fromJson(intent.getStringExtra("flowJson"), Flow::class.java)
        if (flow != null) {
            viewModel.flow = flow
        } else {
            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
        }

        startNewFlowActivity()
        btnNext.setOnClickListener { viewModel.onNextClick() }

        subscribeToScreens()
    }

    private fun startNewFlowActivity() {

        var input1 = InputWidget("input1", "Your name", 20)
        var input2 = InputWidget("input2", "Type custom medication", 20)


        var elementsScreen2 = listOf<ListElement>(
            ListElement("element1", "Medication 1", "finish"),
            ListElement("element2", "Medication 2", "finish"),
            ListElement("element3", "Other", "screen3")
        )

        var listWidget = ChooseListWidget("chooseListWidget", elementsScreen2)


        var screen1 = Screen(
            "screen1", "Your Name", false, false, false,
            Screen.Companion.GoNextCondition.NEXT, "screen2", Screen.Companion.WidgetType.INPUT_WIDGET, input1
        )

        var screen2 = Screen(
            "screen2", "Choose Medication", false, false, false,
            Screen.Companion.GoNextCondition.CUSTOM, "", Screen.Companion.WidgetType.LIST_WIDGET, listWidget
        )

        var screen3 = Screen(
            "screen3", "Custom Medication", false, false, false,
            Screen.Companion.GoNextCondition.FINISH, "finish", Screen.Companion.WidgetType.INPUT_WIDGET, input2
        )


        var flow = Flow(listOf(screen1, screen2, screen3))

        viewModel.setFlow(flow)

    }

    private fun subscribeToScreens() {
        viewModel.currentScreen.nonNullObserve(this) {
            commitFragment(
                when (it.widgetType) {
                    Screen.Companion.WidgetType.INPUT_WIDGET -> InputFragment()
                    Screen.Companion.WidgetType.LIST_WIDGET -> ChooseItemFromListFragment()
                }
            )
            tvTitle.text = it.title
        }

    }

    private fun commitFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

}
