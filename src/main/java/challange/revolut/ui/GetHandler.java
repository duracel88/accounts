package challange.revolut.ui;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GetHandler {

    void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
