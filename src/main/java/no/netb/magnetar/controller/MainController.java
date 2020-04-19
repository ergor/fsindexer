package no.netb.magnetar.controller;

import no.netb.magnetar.entities.indexing.Host;
import no.netb.magnetar.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private HostRepository hostRepository;

    @GetMapping("/webui/main")
    public String main() {
        return "main";
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String root() {
        return "redirect:/webui/main";
    }

    @ModelAttribute("hosts")
    public List<Host> allHosts() {
        List<Host> hosts = new ArrayList<>();
        hostRepository.findAll().forEach(hosts::add);
        return hosts;
    }
}
