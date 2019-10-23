package challange.revolut.ui.transfer;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TransferServlet extends HttpServlet {

    public static final String PATH = "/transfer";
    private TransferGetHandler get;
    private TransferPostHandler post;


    @Inject
    public TransferServlet(TransferGetHandler getHandler, TransferPostHandler postHandler) {
        this.get = getHandler;
        this.post = postHandler;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        post.doPost(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        get.doGet(req, resp);
    }
}
