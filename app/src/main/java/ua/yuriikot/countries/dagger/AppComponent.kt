package ua.yuriikot.countries.dagger

import dagger.Component
import ua.yuriikot.countries.activitiy.MainActivity
import ua.yuriikot.countries.fragment.CountriesFragment
import ua.yuriikot.countries.fragment.CountryDetailsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DefaultModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(countriesFragment: CountriesFragment)
    fun inject(countryDetailsFragment: CountryDetailsFragment)
}
