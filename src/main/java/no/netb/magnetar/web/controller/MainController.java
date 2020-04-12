package no.netb.magnetar.web.controller;


import no.netb.magnetar.models.Host;
import no.netb.magnetar.repository.HostRepository;
import no.netb.magnetar.web.app.Response;
import no.netb.magnetar.web.app.Template;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;


public class MainController extends MagnetarController {

    public MainController(ITemplateEngine templateEngine) {
        super(templateEngine);
    }

    @Override
    protected Response handleGetRequest(Context ctx, ControllerArgs args) throws Exception {

        List<Host> hosts = args.repositories.getRepo(HostRepository.class).getHosts().unwrap();

        ctx.setVariable("hosts", hosts);

        return Response.ok(Template.MAIN, templateEngine, ctx);
    }
}
