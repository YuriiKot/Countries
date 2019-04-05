package ua.yuriikot.countries.fragment


import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ua.yuriikot.countries.App
import ua.yuriikot.countries.R
import ua.yuriikot.countries.utils.extension.observeOneTimeNonNull
import ua.yuriikot.countries.viewmodel.MainViewModel
import ua.yuriikot.countries.viewmodel.MainViewModelFactory
import java.util.*
import javax.inject.Inject


class CountryDetailsFragment : Fragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var binding: ua.yuriikot.countries.databinding.FragmentCountryDetailsBinding
    private lateinit var viewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.appComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_details, container, false)
        viewModel = ViewModelProviders.of(activity!!, mainViewModelFactory)[MainViewModel::class.java]

        viewModel.countryDetailsLiveData.observeOneTimeNonNull(this) {
            Glide.with(context).`as`(PictureDrawable::class.java).load(it.flag).into(binding.ivFlag)
            binding.tvName.text = it.name
            binding.tvPopulation.text = getString(ua.yuriikot.countries.R.string.population) + " ${it.population}"
            binding.tvRegionSubregion.text = "${it.region} ${if (it.subregion != "") (" -> " + it.subregion) else ""}"
            binding.tvArea.text = getString(ua.yuriikot.countries.R.string.area) + " " + it.areaFormatted
            binding.tvNumericCode.text = getString(ua.yuriikot.countries.R.string.phone_code) + " ${it.numericCode}"
            binding.btnOpenMap.setOnClickListener { v -> openMap(it.latitude, it.longitude) }
        }

        return binding.root
    }

    private fun openMap(lat: Double, long: Double) {
        val uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=7", lat, long)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }


}
