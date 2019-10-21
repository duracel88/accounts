package challange.revolut.ui.transfer.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import challange.revolut.domain.valueobject.Currency;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferDTO {
    private UUID from;
    private UUID to;
    private double value;
    private Currency currency;
}
