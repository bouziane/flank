package ftl.util

import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertNotEquals
import org.junit.Test

internal class ObfuscationTest {

    @Test
    fun `Should obfuscate android like test method string`() {
        // given
        val testString = "com.flank.Super#Test"
        val obfuscationContext: ObfuscationContext = mutableMapOf()

        // when
        val obfuscated = obfuscationContext.obfuscateAndroidTestName(testString)

        // then
        assertNotEquals(testString, obfuscated)
    }

    @Test
    fun `Should obfuscate ios like test method string`() {
        // given
        val obfuscationContext: ObfuscationContext = mutableMapOf()
        val testString = "Flank/SuperTest"

        // when
        val obfuscated = obfuscationContext.obfuscateIosTestName(testString)

        // then
        assertNotEquals(testString, obfuscated)
    }

    @Test
    fun `Should obfuscate android method name and assign unique symbols to package and method`() {
        // given
        val testMethods = listOf(
            "com.example.Class1#method1",
            "com.example.Class1#method2",
            "com.example.Class2#method1",
            "com.example.foo.Class1#method1 ",
            "flank.support.Test#test2",
            "flank.test.Test#test1"
        )
        val expectedObfuscationResult = listOf(
            "a.a.A#a",
            "a.a.A#b",
            "a.a.B#a",
            "a.a.a.A#a",
            "b.a.A#a",
            "b.b.A#a"
        )
        val obfuscationContext: ObfuscationContext = mutableMapOf()

        // when
        val testResults = testMethods.map { obfuscationContext.obfuscateAndroidTestName(it) }

        // then
        testResults.forEachIndexed { index, obfuscatedMethodName ->
            assertThat(obfuscatedMethodName).isEqualTo(expectedObfuscationResult[index])
        }
    }

    @Test
    fun `Should obfuscate ios method name and assign unique symbols to class and method`() {
        // given
        val testMethods = listOf(
            "SampleIOS/test",
            "SampleIOS/test2",
            "SampleIOS/test3",
            "SampleIOS/test4",
            "SampleIOS1/test",
            "SampleIOS1/test2"
        )
        val expectedObfuscationResult = listOf(
            "A/a",
            "A/b",
            "A/c",
            "A/d",
            "B/a",
            "B/b"
        )
        val obfuscationContext: ObfuscationContext = mutableMapOf()

        // when
        val testResults = testMethods.map { obfuscationContext.obfuscateIosTestName(it) }

        // then
        testResults.forEachIndexed { index, obfuscatedMethodName ->
            assertThat(obfuscatedMethodName).isEqualTo(expectedObfuscationResult[index])
        }
    }
}