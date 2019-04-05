package ua.yuriikot.countries.dagger

import dagger.Module
import dagger.Provides
import ua.yuriikot.countries.repository.CountryRepository
import ua.yuriikot.countries.viewmodel.MainViewModelFactory
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideMainViewModelFactory(countryRepository: CountryRepository): MainViewModelFactory {
        return MainViewModelFactory(countryRepository)
    }

}