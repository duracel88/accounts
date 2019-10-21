package challange.revolut.service;

import com.google.inject.AbstractModule;

import challange.revolut.service.transfer.TransferMoneyService;
import ru.vyarus.guice.validator.ImplicitValidationModule;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ImplicitValidationModule());
        bind(TransferMoneyService.class);
    }

}
