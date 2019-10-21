package challange.revolut.domain;

import challange.revolut.domain.valueobject.AccountId;
import challange.revolut.domain.valueobject.Amount;
import challange.revolut.domain.valueobject.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class CommittedTransfer {

    private AccountId accountFrom;
    private AccountId accountTo;
    private Amount amount;
    private Currency currency;

    @Builder
    private CommittedTransfer(
            @NonNull AccountId accountFrom,
            @NonNull AccountId accountTo,
            @NonNull Amount amount,
            @NonNull Currency currency) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.currency = currency;
    }

}
