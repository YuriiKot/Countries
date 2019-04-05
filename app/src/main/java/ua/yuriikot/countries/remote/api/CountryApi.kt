package ua.yuriikot.countries.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import ua.yuriikot.countries.remote.model.Country

interface CountryApi {

    @GET("all")
    fun getCountries(): Single<List<Country>>

}