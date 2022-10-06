package com.alxnns1.tvmazecompose

import com.alxnns1.tvmazecompose.utils.DateUtils
import com.alxnns1.tvmazecompose.utils.DateWrapper
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test
import java.util.*

private const val testDate = 1665010800000

class DateUtilsTest {

    @Test
    fun `daysSinceDateString returns days since date string`() {
        val dateWrapper: DateWrapper = mockk {
            every { getCurrentDate() } returns Date(testDate)
        }

        val subject = DateUtils(dateWrapper)

        val expected = 5L
        val actual = subject.daysSinceDateString("2022-10-1")

        assertEquals(expected, actual)
    }
}