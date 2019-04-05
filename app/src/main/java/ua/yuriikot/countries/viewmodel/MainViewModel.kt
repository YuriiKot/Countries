package ua.yuriikot.countries.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ua.yuriikot.countries.remote.model.Country
import ua.yuriikot.countries.repository.CountryRepository


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(val countryRepository: CountryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(countryRepository) as T
    }
}

class MainViewModel(val countryRepository: CountryRepository) : ViewModel() {

    val countriesLiveData = MutableLiveData<List<Country>>()
    val countryDetailsLiveData = MutableLiveData<Country>()
    val countryLoadingStateLiveData = MutableLiveData<Boolean>()

    var connectedOnce: Boolean = false
    fun onConnected() {
        if (!connectedOnce) {
            countryRepository.getCountries(countriesLiveData, countryLoadingStateLiveData)
        }
        connectedOnce = true
    }

}

