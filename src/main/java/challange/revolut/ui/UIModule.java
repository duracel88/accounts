package challange.revolut.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.servlet.ServletModule;

import challange.revolut.ui.account.AccountGetHandler;
import challange.revolut.ui.account.AccountServlet;
import challange.revolut.ui.filter.PersistUnitOfWorkFilter;
import challange.revolut.ui.transfer.TransferGetHandler;
import challange.revolut.ui.transfer.TransferPostHandler;
import challange.revolut.ui.transfer.TransferServlet;
import challange.revolut.ui.transfer.TransferRequestFactory;

public class UIModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(PersistUnitOfWorkFilter.class);
        bind(ObjectMapper.class);



        bind(TransferRequestFactory.class);
        bind(TransferGetHandler.class);
        bind(TransferPostHandler.class);

        serve(TransferServlet.PATH  + "/*").with(TransferServlet.class);
        serve(TransferServlet.PATH ).with(TransferServlet.class);

        bind(AccountGetHandler.class);
        serve(AccountServlet.PATH+"/*").with(AccountServlet.class);


        filter("/*").through(PersistUnitOfWorkFilter.class);

    }




}
