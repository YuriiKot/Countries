package ua.yuriikot.countries.repository

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ua.yuriikot.countries.remote.api.CountryApi
import ua.yuriikot.countries.remote.model.Country

class CountryRepository(private val countryApi: CountryApi) {

    @SuppressLint("CheckResult")
    fun getCountries(
        countryLiveData: MutableLiveData<List<Country>>,
        countryLoadingStateLiveData: MutableLiveData<Boolean>
    ) {
        countryApi.getCountries()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally { countryLoadingStateLiveData.value = false }
            .subscribe({
                countryLiveData.value = it
            }, {})
        countryLoadingStateLiveData.value = true
    }

}
