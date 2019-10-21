package challange.revolut.domain

import challange.revolut.domain.valueobject.AccountId
import challange.revolut.domain.valueobject.Amount
import challange.revolut.domain.valueobject.Currency
import spock.lang.Specification

class AccountSpec extends Specification {

    def "Account support currency only if its balance does" () {
        given:
        def accountId = new AccountId(UUID.randomUUID())
        def balance = new Balance(Amount.empty(), Currency.GBP)
        def account = new Account(accountId, balance, 1)

        when:
        def gbpSupport = account.supports(Currency.GBP)
        def plnSupport = account.supports(Currency.PLN)

        then:
        gbpSupport
        !plnSupport
    }
}
