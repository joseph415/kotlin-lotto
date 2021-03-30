package domain.winning

import domain.money.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class WinningCategoryTest {
    @Test
    fun `당첨항목은 '꽝', '3개 일치', '4개 일치', '5개 일치', '5개 & 보너스 볼 일치', '6개 일치'가 있다`() {
        assertThat(WinningCategory.values().map { it.name })
            .containsExactly(
                "LOSE",
                "THREE_CORRECT",
                "FOUR_CORRECT",
                "FIVE_CORRECT",
                "FIVE_WITH_BONUS_CORRECT",
                "SIX_CORRECT"
            )
    }

    @ParameterizedTest
    @CsvSource(
        "LOSE, 0",
        "THREE_CORRECT, 5000",
        "FOUR_CORRECT, 50000",
        "FIVE_CORRECT, 1500000",
        "FIVE_WITH_BONUS_CORRECT, 30000000",
        "SIX_CORRECT, 2000000000"
    )
    fun `당첨항목마다 당첨금은 아래와 같다`(category: WinningCategory, prize: Long) {
        assertThat(category.prize).isEqualTo(Money(prize))
    }

    @ParameterizedTest(name = "{0}개가 매칭되면 {1}를 반환해야 한다")
    @CsvSource(
        "0, LOSE",
        "1, LOSE",
        "2, LOSE",
        "3, THREE_CORRECT",
        "4, FOUR_CORRECT",
        "5, FIVE_CORRECT",
        "6, SIX_CORRECT"
    )
    fun matchNumberOfTest(numberOfMatched: Int, expectedCategory: WinningCategory) {
        // when
        val actual = WinningCategory.matchNumberOf(numberOfMatched)

        // then
        assertThat(actual).isEqualTo(expectedCategory)
    }

    @ParameterizedTest(name = "5개가 같고, 보너스 번호 포함여부가 {0}면 {1}를 반환해야 한다")
    @CsvSource(
        "true, FIVE_WITH_BONUS_CORRECT",
        "false, FIVE_CORRECT"
    )
    internal fun matchingWithBonus(bonusMatched: Boolean, expectedCategory: WinningCategory) {
        // when
        val actual = WinningCategory.matchNumberOf(numberOfMatched = 5, bonusMatched = bonusMatched)

        // then
        assertThat(actual).isEqualTo(expectedCategory)
    }
}