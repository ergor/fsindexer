package no.netb.magnetar.web.controller;


import no.netb.magnetar.models.Host;
import no.netb.magnetar.repository.HostRepository;
import no.netb.magnetar.web.app.Response;
import no.netb.magnetar.web.app.Template;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.SQLException;
import java.util.List;


public class MainController extends MagnetarController {

    public MainController(ITemplateEngine templateEngine) {
        super(templateEngine);
    }

    @Override
    protected Response handleGetRequest(Context ctx, ControllerArgs args) throws IllegalAccessException, SQLException, InstantiationException {

        List<Host> hosts = args.repository.get(HostRepository.class).getHosts();

        ctx.setVariable("hosts", hosts);

        return Response.ok(Template.MAIN, templateEngine, ctx);
    }
}
