package br.ufpa.fifo.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author thiago
 */
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*HttpSession session = ((HttpServletRequest) request).getSession();
        if (session != null && session.getAttribute("auth") == null) {
            ((HttpServletResponse) response).sendRedirect("/faces/auth.xhtml");
        }*/
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
    }

}
