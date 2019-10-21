package challange.revolut.infrastructure.db.repository

import challange.revolut.domain.Account
import challange.revolut.domain.repository.AccountBalanceRepository
import challange.revolut.domain.valueobject.AccountId
import challange.revolut.domain.valueobject.Currency
import challange.revolut.infrastructure.db.entity.AccountBalanceEntity
import com.google.inject.Provider
import spock.lang.Specification

import javax.persistence.EntityManager

import static challange.revolut.domain.valueobject.Currency.GBP

class AccountBalanceRepositoryImplSpec extends Specification {

    def em = Mock(EntityManager)
    def provider = Mock(Provider)
    def mapper = new AccountEntityMapper()

    def repository = new AccountBalanceRepositoryImpl(provider, mapper)

    def setup(){
        provider.get() >> em
    }

    def "Repository returns, if it presents in entity manager"(){
        given:
        def id = UUID.randomUUID()
        def e = new AccountBalanceEntity(id: id, currency: GBP, balance: 123.321, version: 1)
        em.find(AccountBalanceEntity, id) >> e

        when:
        def account = repository.findByAccountId(new AccountId(id))

        then:
        account.isPresent()

    }

    def "Repository doesn't return, if its not present in entity manager"(){
        given:
        def id = UUID.randomUUID()
        def e = new AccountBalanceEntity(id: id, currency: GBP, balance: 123.321, version: 1)
        em.find(AccountBalanceEntity, id) >> e

        when:
        def account = repository.findByAccountId(new AccountId(UUID.randomUUID()))

        then:
        !account.isPresent()

    }
}
