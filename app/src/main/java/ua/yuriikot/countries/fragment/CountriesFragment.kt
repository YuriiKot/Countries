package ua.yuriikot.countries.fragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import ua.yuriikot.countries.App
import ua.yuriikot.countries.R
import ua.yuriikot.countries.adapter.CountriesRecyclerViewAdapter
import ua.yuriikot.countries.remote.model.Country
import ua.yuriikot.countries.utils.extension.nonNullObserve
import ua.yuriikot.countries.viewmodel.MainViewModel
import ua.yuriikot.countries.viewmodel.MainViewModelFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CountriesFragment : Fragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var binding: ua.yuriikot.countries.databinding.FragmentCountriesBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var countriesRecyclerViewAdapter: CountriesRecyclerViewAdapter

    private lateinit var onClickDisposable: Disposable
    private val countryClickedPublishSubject: PublishSubject<Country> = PublishSubject.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.appComponent.inject(this)
        countriesRecyclerViewAdapter = CountriesRecyclerViewAdapter(countryClickedPublishSubject::onNext)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_countries, container, false)
        viewModel = ViewModelProviders.of(activity!!, mainViewModelFactory)[MainViewModel::class.java]

        with(binding.root as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesRecyclerViewAdapter
        }

        initFlow()
        return binding.root
    }

    fun initFlow() {
        viewModel.countriesLiveData.nonNullObserve(this, countriesRecyclerViewAdapter::countries::set)

        onClickDisposable = countryClickedPublishSubject
            .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe(viewModel.countryDetailsLiveData::setValue)

    }

    override fun onDetach() {
        super.onDetach()
        onClickDisposable.dispose()
    }

}
