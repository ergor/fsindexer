package no.netb.magnetar.webui.app;

import no.netb.magnetar.webui.controller.MainController;
import no.netb.magnetar.webui.controller.MagnetarController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.HashMap;
import java.util.Map;

public class MagnetarWebapp {

    private final TemplateEngine templateEngine;
    private final Map<String, MagnetarController> controllersByURL;

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
        controllersByURL.put("/", new MainController());
    }

    public Map<String, MagnetarController> getControllersByURL() {
        return controllersByURL;
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
