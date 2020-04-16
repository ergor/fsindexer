package no.netb.magnetar.web.filter;

import no.netb.magnetar.web.app.MagnetarApplication;
import no.netb.magnetar.web.controller.MagnetarController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MagnetarFilter implements Filter {

    private ServletContext servletContext;
    private MagnetarApplication application;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        this.application = new MagnetarApplication();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!process((HttpServletRequest) request, (HttpServletResponse) response)) {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean process(HttpServletRequest request, HttpServletResponse response) {

        if (request.getRequestURI().startsWith("/css")
                || request.getRequestURI().startsWith("/images")
                || request.getRequestURI().startsWith("/favicon")) {
            return false;
        }

        MagnetarController controller = this.application.resolveControllerForRequest(request).orElse(null);
        if (controller == null) {
            return false;
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        controller.applyTemplate()

        return true;
    }
}
