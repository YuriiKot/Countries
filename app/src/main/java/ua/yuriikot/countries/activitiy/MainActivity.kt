package ua.yuriikot.countries.activitiy

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import ua.yuriikot.countries.App
import ua.yuriikot.countries.R
import ua.yuriikot.countries.base.BaseActivity
import ua.yuriikot.countries.databinding.ActivityMainBinding
import ua.yuriikot.countries.fragment.CountriesFragment
import ua.yuriikot.countries.fragment.CountryDetailsFragment
import ua.yuriikot.countries.sdk.FlowActivity
import ua.yuriikot.countries.sdk.model.*
import ua.yuriikot.countries.utils.extension.addFragment
import ua.yuriikot.countries.utils.extension.nonNullObserve
import ua.yuriikot.countries.viewmodel.MainViewModel
import ua.yuriikot.countries.viewmodel.MainViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory)[MainViewModel::class.java]
        supportFragmentManager.addFragment(R.id.fragmentContainer, CountriesFragment())
        mainViewModel.countryDetailsLiveData.nonNullObserve(this) {
            supportFragmentManager.addFragment(R.id.fragmentContainer, CountryDetailsFragment(), "root")
            processFragmentChanges()
        }
        networkStatusLiveData.nonNullObserve(this, observer = {
            if (it) mainViewModel.onConnected()
            else Toast.makeText(this, "Connection is lost", Toast.LENGTH_SHORT).show()
        })

        mainViewModel.countryLoadingStateLiveData.nonNullObserve(this) {
            binding.progressContainer.frameRoot.visibility = if (it) View.VISIBLE else View.GONE
        }


        startNewFlowActivity()
    }

    private fun startNewFlowActivity() {

        var input1 = InputWidget("input1", "Your name", 20)


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
            Screen.Companion.GoNextCondition.CUSTOM, "", Screen.Companion.WidgetType.INPUT_WIDGET, listWidget
        )

        var screen3 = Screen(
            "screen3", "Custom Medication", false, false, false,
            Screen.Companion.GoNextCondition.FINISH, "finish", Screen.Companion.WidgetType.INPUT_WIDGET, listWidget
        )


        var flow = Flow(listOf(screen1, screen2, screen3))

        val intent = Intent(this, FlowActivity::class.java)
        intent.putExtra("flowJson", Gson().toJson(flow))
        startActivity(intent)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        processFragmentChanges(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun processFragmentChanges(pendingBackPress: Boolean = false) {
        supportActionBar?.setDisplayHomeAsUpEnabled(!pendingBackPress)
    }

}
