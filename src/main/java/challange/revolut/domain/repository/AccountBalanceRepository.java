package challange.revolut.domain.repository;

import java.util.Optional;

import challange.revolut.domain.Account;
import challange.revolut.domain.valueobject.AccountId;

public interface AccountBalanceRepository {

    Optional<Account> findByAccountId(AccountId accountId);
    void update(Account account);

}
