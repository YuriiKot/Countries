package ua.yuriikot.countries

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.yuriikot.countries.remote.model.Country


class CountryUnitTest {
    @Test
    fun testArea() {
        val country = Country(
            "0", 0, "0", "0", "0",
            100.0, ArrayList(), "0"
        )
        assertEquals("100 sq. km.", country.areaFormatted)
        country.area = 1230.0
        assertEquals("1,230 sq. km.", country.areaFormatted)
        country.area = 1000.0
        assertEquals("1,000 sq. km.", country.areaFormatted)
        country.area = 123450.0
        assertEquals("123,450 sq. km.", country.areaFormatted)
        country.area = 123000.0
        assertEquals("123,000 sq. km.", country.areaFormatted)
        country.area = 1234000.0
        assertEquals("1.234 million sq. km.", country.areaFormatted)
        country.area = 1200000.0
        assertEquals("1.2 million sq. km.", country.areaFormatted)
    }
}
