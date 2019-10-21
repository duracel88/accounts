package challange.revolut.infrastructure.db;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import challange.revolut.domain.repository.AccountBalanceRepository;
import challange.revolut.domain.repository.TransferHistoryRepository;
import challange.revolut.infrastructure.db.repository.AccountBalanceRepositoryImpl;
import challange.revolut.infrastructure.db.repository.AccountEntityMapper;
import challange.revolut.infrastructure.db.repository.TransferHistoryMapper;
import challange.revolut.infrastructure.db.repository.TransferHistoryRepositoryImpl;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("db-manager"));
        bind(JpaInitializer.class).asEagerSingleton();

        bind(AccountBalanceRepository.class).to(AccountBalanceRepositoryImpl.class);
        bind(AccountEntityMapper.class);

        bind(TransferHistoryRepository.class).to(TransferHistoryRepositoryImpl.class);
        bind(TransferHistoryMapper.class);
    }

    public static class JpaInitializer {

        @Inject
        public JpaInitializer(PersistService service){
            service.start();
        }
    }

}
