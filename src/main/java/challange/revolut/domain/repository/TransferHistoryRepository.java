package challange.revolut.domain.repository;

import java.util.List;

import challange.revolut.domain.CommittedTransfer;
import challange.revolut.domain.TransferHistory;
import challange.revolut.domain.valueobject.TransferId;

public interface TransferHistoryRepository {

    TransferId store(CommittedTransfer transferHistory);
    List<TransferHistory> findAll();
}
