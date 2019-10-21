package challange.revolut.service.transfer;

import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class TransferMoneyResponse {
    private UUID transactionId;

    public TransferMoneyResponse(@NonNull UUID transactionId) {
        this.transactionId = transactionId;
    }

}
