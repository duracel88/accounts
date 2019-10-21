package challange.revolut.service.transfer;

import javax.validation.Valid;
import javax.validation.ValidationException;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import challange.revolut.domain.Account;
import challange.revolut.domain.CommittedTransfer;
import challange.revolut.domain.repository.AccountBalanceRepository;
import challange.revolut.domain.repository.TransferHistoryRepository;
import challange.revolut.domain.valueobject.AccountId;
import challange.revolut.domain.valueobject.Currency;
import challange.revolut.domain.valueobject.TransferId;

public class TransferMoneyService {

    private AccountBalanceRepository accountRepository;
    private TransferHistoryRepository transferHistoryRepository;

    @Inject
    public TransferMoneyService(AccountBalanceRepository accountRepository, TransferHistoryRepository transferHistoryRepository) {
        this.accountRepository = accountRepository;
        this.transferHistoryRepository = transferHistoryRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public TransferMoneyResponse handle(@Valid TransferMoneyRequest request) {
        Account from = accountRepository
                .findByAccountId(new AccountId(request.getAccountFromId()))
                .orElseThrow( () -> new ValidationException("Account not found, id=" + request.getAccountFromId()));
        Account to = accountRepository
                .findByAccountId(new AccountId(request.getAccountToId()))
                .orElseThrow( () -> new ValidationException("Account not found, id=" + request.getAccountToId()));
        checkCurrency(request.getCurrency(), from, to);

        from.getBalance().take( request.getAmount() );
        to.getBalance().add( request.getAmount() );

        accountRepository.update( from );
        accountRepository.update( to );

        CommittedTransfer transfer = CommittedTransfer.builder()
                .accountFrom(from.getAccountId())
                .accountTo(to.getAccountId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .build();
        TransferId transferId = transferHistoryRepository.store(transfer);

        return new TransferMoneyResponse(transferId.getId());
    }

    private void checkCurrency(Currency currency, Account from, Account to){
        if( ! from.supports(currency)){
            throw new DifferentCurrenciesException("Source account not supports currency " + currency);
        }
        if( ! to.supports(currency)){
            throw new DifferentCurrenciesException("Target account not supports currency " + currency);
        }
    }

}
