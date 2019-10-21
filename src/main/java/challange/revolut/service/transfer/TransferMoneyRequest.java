package challange.revolut.service.transfer;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import challange.revolut.domain.valueobject.Amount;
import challange.revolut.domain.valueobject.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class TransferMoneyRequest {

    @NotNull(message = "Sending account id must be specified")
    @Getter
    private UUID accountFromId;

    @NotNull(message = "Receive account id must be specified")
    @Getter
    private UUID accountToId;

    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be positive number")
    private BigDecimal amount;

    @NotNull(message = "Currency must not be null")
    @Getter
    private Currency currency;

    @AssertTrue(message = "Account ids must differ")
    private boolean idsDiffer;

    @Builder
    public TransferMoneyRequest(UUID accountFromId, UUID accountToId, BigDecimal amount, Currency currency) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
        this.currency = currency;
        this.idsDiffer = !accountFromId.equals(accountToId);
    }

    Amount getAmount() {
        return Amount.of(amount);
    }

}
