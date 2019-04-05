package ua.yuriikot.countries.remote.model

data class Country(
    var name: String,
    var population: Int,
    var flag: String?,
    var region: String,
    var subregion: String,
    var area: Double,
    var latlng: List<Double>,
    var numericCode: String

) {
    val latitude: Double
        get() = this.latlng[0]
    val longitude: Double
        get() = this.latlng[1]
    val areaFormatted: String
        get() {
            val areaInt = area.toInt()
            return when (areaInt) {
                in 0..999 -> "$areaInt sq. km."
                in 1000..999999 -> "${"%,d".format(areaInt)} sq. km."
                else -> "${(areaInt / 1000) / 1000.0} million sq. km."
            }
        }
}
