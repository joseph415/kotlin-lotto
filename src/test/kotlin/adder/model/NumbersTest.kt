package adder.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NumbersTest {
    @Test
    fun create() {
        // given
        val input = listOf("2", "0", "23", "1234")

        // when
        val numbers = Numbers(input)

        // then
        assertThat(numbers).isEqualTo(
            Numbers(
                listOf(
                    Number(2),
                    Number(0),
                    Number(23),
                    Number(1234)
                )
            )
        )
    }

    @Test
    fun createWithDefaultDelimiter() {
        // given
        val input = "1,2,3,45,6"

        // when
        val numbers = Numbers(input)

        // then
        assertThat(numbers).isEqualTo(
            Numbers(
                listOf(
                    Number(1),
                    Number(2),
                    Number(3),
                    Number(45),
                    Number(6)
                )
            )
        )
    }

    @Test
    fun createWithCustomDelimiter() {
        // given
        val input = "//-\\n1-2-3-45-6"

        // when
        val numbers = Numbers(input)

        // then
        assertThat(numbers).isEqualTo(
            Numbers(
                listOf(
                    Number(1),
                    Number(2),
                    Number(3),
                    Number(45),
                    Number(6)
                )
            )
        )
    }

    @Test
    fun getSum() {
        // given
        val input = listOf("2", "23", "50", "0", "100")
        val numbers = Numbers(input)

        // when
        val sum = numbers.getSum()

        // then
        assertThat(sum).isEqualTo(175)
    }
}