package challange.revolut.domain

import challange.revolut.domain.exception.InsufficientBalanceException
import challange.revolut.domain.valueobject.Amount
import spock.lang.Specification

import static challange.revolut.domain.valueobject.Currency.GBP

class BalanceSpec extends Specification {

    def "Addition to balance"() {
        given:
        def balance = Balance.emptyBalance(GBP)

        when:
        balance.add(Amount.of(5))

        then:
        balance.getAmount() == Amount.of(5.0)
    }

    def "Subtraction"() {
        given:
        def balance = Balance.emptyBalance(GBP)
        balance.add(Amount.of(5))

        when:
        balance.take(Amount.of(5))

        then:
        balance.getAmount() == Amount.of(0)
    }


    def "Subtraction should throw exception when balance is insufficient"() {
        given:
        def balance = Balance.emptyBalance(GBP)

        when:
        balance.take(Amount.of(5))

        then:
        thrown(InsufficientBalanceException)
    }
}
