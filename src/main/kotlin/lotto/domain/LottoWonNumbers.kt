package lotto.domain

data class LottoWonNumbers(private val lottoNumber: Set<LottoNumber>, private val bonusNumber: LottoNumber) {
    init {
        require(lottoNumber.size == LottoTicket.LOTTO_NUMBER_COUNT) { "당첨 로또 번호는 ${LottoTicket.LOTTO_NUMBER_COUNT} 개여야 합니다" }
        require(!lottoNumber.contains(bonusNumber)) { "당첨 로또 번호에는 보너스 당첨번호가 존재해서는 안됩니다." }
    }

    fun match(lottoTickets: LottoTickets): LottoRanks {
        val rankCount: Map<Rank, Int> = lottoTickets
            .map {
                match(it)
            }
            .groupingBy { it }
            .eachCount()

        return LottoRanks(rankCount)
    }

    fun match(lottoTicket: LottoTicket): Rank {
        val wonNumberCount: Int = lottoTicket.count { lottoNumber.contains(it) }
        val matchBonus: Boolean = lottoTicket.contains(bonusNumber)

        return Rank.getRankByCount(wonNumberCount, matchBonus)
    }

    constructor(lottoNumber: Set<Int>, bonusNumber: Int) : this(
        lottoNumber.map { LottoNumber.from(it) }.toSet(),
        LottoNumber.from(bonusNumber)
    )
}
