package ua.yuriikot.countries.activitiy

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import ua.yuriikot.countries.App
import ua.yuriikot.countries.R
import ua.yuriikot.countries.base.BaseActivity
import ua.yuriikot.countries.databinding.ActivityMainBinding
import ua.yuriikot.countries.fragment.CountriesFragment
import ua.yuriikot.countries.fragment.CountryDetailsFragment
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
