package no.netb.magnetar.webui.app;

import no.netb.magnetar.webui.controller.FaultController;
import no.netb.magnetar.webui.controller.MainController;
import no.netb.magnetar.webui.controller.MagnetarController;
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

        this.faultController = new FaultController();

        this.controllersByURL = new HashMap<>();
        controllersByURL.put("/", new MainController());
    }

    public Optional<MagnetarController> resolveControllerByURL(String url) {
        return Optional.ofNullable(controllersByURL.get(url));
    }

    public MagnetarController getFaultController() {
        return faultController;
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
