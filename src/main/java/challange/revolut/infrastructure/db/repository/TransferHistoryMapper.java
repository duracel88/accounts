package challange.revolut.infrastructure.db.repository;

import java.time.LocalDateTime;

import challange.revolut.domain.CommittedTransfer;
import challange.revolut.domain.TransferHistory;
import challange.revolut.domain.valueobject.AccountId;
import challange.revolut.domain.valueobject.Amount;
import challange.revolut.domain.valueobject.TransferId;
import challange.revolut.infrastructure.db.entity.TransferHistoryEntity;

public class TransferHistoryMapper {

    TransferHistoryEntity toDatabaseEntity(CommittedTransfer committedTransfer){
        return TransferHistoryEntity.builder()
                .sendFrom(committedTransfer.getAccountFrom().getId())
                .sendTo(committedTransfer.getAccountTo().getId())
                .timestamp(LocalDateTime.now())
                .amount(committedTransfer.getAmount().getValue())
                .currency(committedTransfer.getCurrency())
                .build();
    }

    TransferHistory toDomainModel(TransferHistoryEntity transferHistory){
        CommittedTransfer committedTransfer = CommittedTransfer.builder()
                .accountFrom(new AccountId(transferHistory.getFromId()))
                .accountTo(new AccountId(transferHistory.getToId()))
                .amount(Amount.of(transferHistory.getAmount()))
                .currency(transferHistory.getCurrency())
                .build();
        return TransferHistory.builder()
                .transferId(new TransferId(transferHistory.getId()))
                .committedAt(transferHistory.getTimestamp())
                .committedTransfer(committedTransfer)
                .build();
    }
}
