package no.netb.magnetar.web.app;

import no.netb.magnetar.web.controller.FaultController;
import no.netb.magnetar.web.controller.MainController;
import no.netb.magnetar.web.controller.MagnetarController;
import no.netb.magnetar.web.controller.NewHostController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MagnetarApplication {

    private final TemplateEngine templateEngine;
    private final Map<String, MagnetarController> controllersByURL;
    private final MagnetarController faultController;

    public MagnetarApplication() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(this.getClass().getClassLoader());
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(true);
        templateResolver.setCacheTTLMs(60L * 60L * 1000L); // 1 hour

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);

        this.controllersByURL = new HashMap<>();
        controllersByURL.put(Template.MAIN.requestPath, new MainController(templateEngine));
        controllersByURL.put(Template.NEW_HOST.requestPath, new NewHostController(templateEngine));

        this.faultController = new FaultController(templateEngine);
    }

    public Optional<MagnetarController> resolveControllerForRequest(HttpServletRequest request) {
        String path = getRequestPath(request);
        return Optional.ofNullable(controllersByURL.get(path));
    }

    public MagnetarController getFaultController() {
        return faultController;
    }

    private static String getRequestPath(final HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        final String contextPath = request.getContextPath();

        final int fragmentIndex = requestURI.indexOf(';');
        if (fragmentIndex != -1) {
            requestURI = requestURI.substring(0, fragmentIndex);
        }

        if (requestURI.startsWith(contextPath)) {
            return requestURI.substring(contextPath.length());
        }
        return requestURI;
    }
}
