package com.bragonya.unsplashdemoapp.utils

import junit.framework.TestCase
import org.junit.Test

class DateFormatterTest : TestCase() {

    @Test
    fun testHumanDays(){
        assert(DateFormatter.formatToHumanDays("2021-09-18T04:08:02-04:00") != null)
        assert(DateFormatter.formatToHumanDays("2021-09-adf") == NO_DATE_FALLBACK)
    }
}