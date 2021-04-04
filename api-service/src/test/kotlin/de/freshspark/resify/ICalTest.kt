package de.freshspark.resify

import de.freshspark.resify.helper.addCR
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ICalTest {
  @Test
  fun testAddCR(){
    val string = "line1\r\nline2\rline3\r\n"
    val actualString = string.addCR()
    val expectedString = "line1\r\nline2\r\nline3\r\n"
    Assertions.assertEquals(actualString, expectedString)
  }
}