package challange.revolut.ui.account;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AccountServlet extends HttpServlet {

    public static final String PATH = "/account";

    private AccountGetHandler get;

    @Inject
    public AccountServlet(AccountGetHandler getHandler) {
        this.get = getHandler;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        get.doGet(req, resp);
    }
}
