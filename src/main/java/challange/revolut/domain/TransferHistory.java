package challange.revolut.domain;

import java.time.LocalDateTime;

import challange.revolut.domain.valueobject.TransferId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferHistory {

    private final TransferId transferId;
    private final CommittedTransfer committedTransfer;
    private final LocalDateTime committedAt;

}
