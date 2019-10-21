package challange.revolut.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.servlet.ServletModule;

import challange.revolut.ui.filter.PersistUnitOfWorkFilter;
import challange.revolut.ui.transfer.TransferServlet;
import challange.revolut.ui.transfer.TransferRequestFactory;

public class UIModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(PersistUnitOfWorkFilter.class);
        bind(TransferRequestFactory.class);
        bind(ObjectMapper.class);

        filter("/*").through(PersistUnitOfWorkFilter.class);
        serve(TransferServlet.PATH ).with(TransferServlet.class);

    }




}
