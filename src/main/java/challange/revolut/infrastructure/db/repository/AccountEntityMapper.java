package challange.revolut.infrastructure.db.repository;

import challange.revolut.domain.Account;
import challange.revolut.domain.Balance;
import challange.revolut.domain.valueobject.AccountId;
import challange.revolut.domain.valueobject.Amount;
import challange.revolut.infrastructure.db.entity.AccountBalanceEntity;

public class AccountEntityMapper {

    Account toDomainModel(AccountBalanceEntity balanceEntity){
        return Account.builder()
                .accountId(new AccountId(balanceEntity.getId()))
                .balance(Balance.of(Amount.of(balanceEntity.getBalance()), balanceEntity.getCurrency()))
                .version(balanceEntity.getVersion())
                .build();
    }

    AccountBalanceEntity toDatabaseEntity(Account account){
        Balance balance = account.getBalance();
        return AccountBalanceEntity.builder()
                .id(account.getAccountId().getId())
                .balance(balance.getAmount().getValue())
                .currency(balance.getCurrency())
                .version(account.getVersion())
                .build();
    }
}
