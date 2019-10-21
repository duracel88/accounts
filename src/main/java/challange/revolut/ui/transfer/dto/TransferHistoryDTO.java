package challange.revolut.ui.transfer.dto;

import java.util.UUID;

import challange.revolut.domain.valueobject.Currency;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferHistoryDTO {

    private UUID id;
    private UUID from;
    private UUID to;
    private Double amount;
    private Currency currency;
    private String timestamp;
}
