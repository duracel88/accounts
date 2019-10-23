package challange.revolut.ui.account.dto;

import java.util.UUID;

import challange.revolut.domain.valueobject.Currency;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountBalanceDTO {

    private UUID id;
    private Double balance;
    private Currency currency;
}
