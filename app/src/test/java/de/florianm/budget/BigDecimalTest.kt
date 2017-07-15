package de.florianm.budget

import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class BigDecimalTest {

    @Test
    fun test_BigDecimal_toString(){
        val expected = BigDecimal("10.00")
        val actual = BigDecimal(expected.toString())
        Assert.assertEquals(expected, actual)
    }
}