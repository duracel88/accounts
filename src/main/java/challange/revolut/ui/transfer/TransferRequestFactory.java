package challange.revolut.ui.transfer;

import java.math.BigDecimal;

import challange.revolut.domain.valueobject.Currency;
import challange.revolut.service.transfer.TransferMoneyRequest;
import challange.revolut.ui.transfer.dto.TransferDTO;

public class TransferRequestFactory {

    public TransferMoneyRequest apply(TransferDTO dto) {
        return TransferMoneyRequest.builder()
                .accountFromId(dto.getFrom())
                .accountToId(dto.getTo())
                .amount(BigDecimal.valueOf(dto.getValue()))
                .currency(dto.getCurrency())
                .build();
    }
}
