package challange.revolut.service.transfer

import challange.revolut.domain.Account
import challange.revolut.domain.Balance
import challange.revolut.domain.CommittedTransfer
import challange.revolut.domain.exception.InsufficientBalanceException
import challange.revolut.domain.repository.AccountBalanceRepository
import challange.revolut.domain.repository.TransferHistoryRepository
import challange.revolut.domain.valueobject.AccountId
import challange.revolut.domain.valueobject.Amount
import challange.revolut.domain.valueobject.Currency
import challange.revolut.domain.valueobject.TransferId
import spock.lang.Specification

import javax.validation.ValidationException

class TransferMoneyServiceSpec extends Specification {

    def accountRepository = Mock(AccountBalanceRepository)
    def transactionRepository = Mock(TransferHistoryRepository)
    def service = new TransferMoneyService(accountRepository, transactionRepository)

    def "Throw exception when sending account does not exists" () {
        given:
        def from = UUID.randomUUID()
        def to = UUID.randomUUID()
        accountRepository.findByAccountId(new AccountId(from)) >> Optional.empty()

        def request = TransferMoneyRequest.builder()
                .currency(Currency.GBP)
                .amount(BigDecimal.valueOf(123.32))
                .accountToId(to)
                .accountFromId(from)
                .build()

        when:
        def result = service.handle(request)

        then:
        thrown(ValidationException)

    }

    def "Throw exception when receiving account does not exists" () {
        given:
        def from = UUID.randomUUID()
        def to = UUID.randomUUID()
        def sendingAccount = Account.builder()
                .accountId(new AccountId(from))
                .balance(Balance.emptyBalance(Currency.GBP))
                .version(1)
                .build()
        accountRepository.findByAccountId(new AccountId(from)) >> Optional.of(sendingAccount)
        accountRepository.findByAccountId(new AccountId(to)) >> Optional.empty()

        def request = TransferMoneyRequest.builder()
                .currency(Currency.GBP)
                .amount(BigDecimal.valueOf(123.32))
                .accountToId(to)
                .accountFromId(from)
                .build()

        when:
        def result = service.handle(request)

        then:
        thrown(ValidationException)

    }

    def "Throw exception when receiving currency account does not match" () {
        given:
        def from = UUID.randomUUID()
        def to = UUID.randomUUID()
        def sendingAccount = Account.builder()
                .accountId(new AccountId(from))
                .balance(Balance.emptyBalance(Currency.GBP))
                .version(1)
                .build()
        def receivingAccount = Account.builder()
                .accountId(new AccountId(to))
                .balance(Balance.emptyBalance(Currency.PLN))
                .version(1)
                .build()
        accountRepository.findByAccountId(new AccountId(from)) >> Optional.of(sendingAccount)
        accountRepository.findByAccountId(new AccountId(to)) >> Optional.of(receivingAccount)

        def request = TransferMoneyRequest.builder()
                .currency(Currency.GBP)
                .amount(BigDecimal.valueOf(123.32))
                .accountToId(to)
                .accountFromId(from)
                .build()

        when:
        def result = service.handle(request)

        then:
        thrown(DifferentCurrenciesException)

    }

    def "Throw exception when sending currency account does not match" () {
        given:
        def from = UUID.randomUUID()
        def to = UUID.randomUUID()
        def sendingAccount = Account.builder()
                .accountId(new AccountId(from))
                .balance(Balance.emptyBalance(Currency.PLN))
                .version(1)
                .build()
        def receivingAccount = Account.builder()
                .accountId(new AccountId(to))
                .balance(Balance.emptyBalance(Currency.GBP))
                .version(1)
                .build()
        accountRepository.findByAccountId(new AccountId(from)) >> Optional.of(sendingAccount)
        accountRepository.findByAccountId(new AccountId(to)) >> Optional.of(receivingAccount)

        def request = TransferMoneyRequest.builder()
                .currency(Currency.GBP)
                .amount(BigDecimal.valueOf(123.32))
                .accountToId(to)
                .accountFromId(from)
                .build()

        when:
        def result = service.handle(request)

        then:
        thrown(DifferentCurrenciesException)

    }

    def "Throw exception when sending account has insufficient balance" () {
        given:
        def from = UUID.randomUUID()
        def to = UUID.randomUUID()
        def sendingAccount = Account.builder()
                .accountId(new AccountId(from))
                .balance(Balance.emptyBalance(Currency.GBP))
                .version(1)
                .build()
        def receivingAccount = Account.builder()
                .accountId(new AccountId(to))
                .balance(Balance.emptyBalance(Currency.GBP))
                .version(1)
                .build()
        accountRepository.findByAccountId(new AccountId(from)) >> Optional.of(sendingAccount)
        accountRepository.findByAccountId(new AccountId(to)) >> Optional.of(receivingAccount)

        def request = TransferMoneyRequest.builder()
                .currency(Currency.GBP)
                .amount(BigDecimal.valueOf(123.32))
                .accountToId(to)
                .accountFromId(from)
                .build()

        when:
        def result = service.handle(request)

        then:
        thrown(InsufficientBalanceException)

    }

}
