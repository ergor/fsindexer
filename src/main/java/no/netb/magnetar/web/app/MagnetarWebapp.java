package no.netb.magnetar.web.app;

import no.netb.magnetar.web.controller.FaultController;
import no.netb.magnetar.web.controller.MainController;
import no.netb.magnetar.web.controller.MagnetarController;
import no.netb.magnetar.web.controller.NewHostController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MagnetarWebapp {

    private final TemplateEngine templateEngine;
    private final Map<String, MagnetarController> controllersByURL;
    private final MagnetarController faultController;

    public MagnetarWebapp() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(this.getClass().getClassLoader());
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(true);
        templateResolver.setCacheTTLMs(60L * 60L * 1000L); // 1 hour

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);

        this.controllersByURL = new HashMap<>();
        controllersByURL.put("/", new MainController(templateEngine));
        controllersByURL.put("/newHost", new NewHostController(templateEngine));

        this.faultController = new FaultController(templateEngine);
    }

    public Optional<MagnetarController> resolveControllerByURL(String url) {
        return Optional.ofNullable(controllersByURL.get(url));
    }

    public MagnetarController getFaultController() {
        return faultController;
    }
}
