package no.netb.magnetar.controller;

import no.netb.magnetar.entities.Host;
import no.netb.magnetar.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class NewHostController {

    @Autowired
    HostRepository hostRepository;

    @GetMapping("/new_host")
    public String newHostForm() {
        return "new_host";
    }

    @PostMapping("/new_host")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String submit(@RequestParam("displayName") String displayName, @RequestParam("fqdn") String fqdn) {
        Host host = new Host();
        host.setDisplayName(displayName);
        host.setFqdn(fqdn);
        hostRepository.save(host);
        return "redirect:/";
    }
}
