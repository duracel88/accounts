package challange.revolut.domain;

import javax.validation.constraints.NotNull;

import challange.revolut.domain.exception.InsufficientBalanceException;
import challange.revolut.domain.valueobject.Amount;
import challange.revolut.domain.valueobject.Currency;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Balance {

    private final Currency currency;
    private Amount amount;

    public Balance(@NonNull Amount amount, @NotNull Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public void take(Amount amount) {
        if(this.amount.compareTo(amount) < 0){
            String msg = String.format("Balance=%s, amount=%s", this.amount.getValue(), amount.getValue());
            throw new InsufficientBalanceException(msg);
        }
        this.amount = this.amount.subtract(amount);
    }

    public void add(Amount amount) {
        this.amount = this.amount.add(amount);
    }


    public static Balance emptyBalance(Currency currency) {
        return new Balance(Amount.empty(), currency);
    }

    public static Balance of(Amount amount, Currency currency) {
        return new Balance(amount, currency);
    }
}
