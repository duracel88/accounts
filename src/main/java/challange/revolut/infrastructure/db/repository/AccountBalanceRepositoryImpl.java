package challange.revolut.infrastructure.db.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

import challange.revolut.domain.Account;
import challange.revolut.domain.repository.AccountBalanceRepository;
import challange.revolut.domain.valueobject.AccountId;
import challange.revolut.infrastructure.db.entity.AccountBalanceEntity;

public class AccountBalanceRepositoryImpl implements AccountBalanceRepository {

    private Provider<EntityManager> emProvider;
    private AccountEntityMapper mapper;

    @Inject
    public AccountBalanceRepositoryImpl(Provider<EntityManager> emProvider, AccountEntityMapper mapper) {
        this.emProvider = emProvider;
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> findByAccountId(AccountId accountId) {
        EntityManager em = emProvider.get();
        AccountBalanceEntity accountEntity = em.find(AccountBalanceEntity.class, accountId.getId());
        return Optional.ofNullable(accountEntity).map(mapper::toDomainModel);
    }

    @Override
    public void update(Account account) {
        EntityManager em = emProvider.get();
        AccountBalanceEntity entity = mapper.toDatabaseEntity(account);
        em.merge(entity);
    }
}
