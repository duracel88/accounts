package challange.revolut.ui.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.UnitOfWork;

@Singleton
public final class PersistUnitOfWorkFilter implements Filter {

    private final UnitOfWork unitOfWork;

    @Inject
    public PersistUnitOfWorkFilter(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void init(FilterConfig filterConfig) { }

    public void destroy() { }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        unitOfWork.begin();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            unitOfWork.end();
        }
    }

}