package challange.revolut.ui.transfer.dto;

import java.util.UUID;

import lombok.Getter;

@Getter
public class TransferAcceptedDTO {
    private UUID transactionId;

    public TransferAcceptedDTO(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
