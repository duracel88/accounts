package challange.revolut.domain;

import challange.revolut.domain.valueobject.AccountId;
import challange.revolut.domain.valueobject.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Account {

    private final AccountId accountId;
    private final Balance balance;
    private final Integer version;

    @Builder
    public Account(@NonNull AccountId accountId, @NonNull Balance balance, @NonNull Integer version) {
        this.accountId = accountId;
        this.balance = balance;
        this.version = version;
    }

    public boolean supports(Currency currency){
        return this.balance.getCurrency().equals(currency);
    }

}
