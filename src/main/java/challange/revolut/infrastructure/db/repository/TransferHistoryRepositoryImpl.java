package challange.revolut.infrastructure.db.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

import challange.revolut.domain.CommittedTransfer;
import challange.revolut.domain.TransferHistory;
import challange.revolut.domain.repository.TransferHistoryRepository;
import challange.revolut.domain.valueobject.TransferId;
import challange.revolut.infrastructure.db.entity.TransferHistoryEntity;

public class TransferHistoryRepositoryImpl implements TransferHistoryRepository {

    private Provider<EntityManager> emP;
    private TransferHistoryMapper mapper;

    @Inject
    public TransferHistoryRepositoryImpl(Provider<EntityManager> emP, TransferHistoryMapper mapper) {
        this.emP = emP;
        this.mapper = mapper;
    }

    @Override
    public TransferId store(CommittedTransfer committedTransfer) {
        EntityManager em = emP.get();
        TransferHistoryEntity e = em.merge(mapper.toDatabaseEntity(committedTransfer));
        return new TransferId(e.getId());
    }

    @Override
    public List<TransferHistory> findAll() {
        EntityManager em = emP.get();
        List<TransferHistoryEntity> resultList = em.createQuery("SELECT e FROM TransferHistoryEntity e").getResultList();
        return resultList.stream()
                .map(mapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransferHistory> findById(TransferId transferId) {
        EntityManager em = emP.get();
        TransferHistoryEntity e = em.find(TransferHistoryEntity.class, transferId.getId());
        return Optional.ofNullable(e).map(mapper::toDomainModel);
    }
}
