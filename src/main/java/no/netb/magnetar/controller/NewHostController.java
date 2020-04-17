package no.netb.magnetar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class NewHostController {

    @GetMapping("/new_host")
    public String newHostForm() {
        return "new_host";
    }

    @PostMapping("/new_host")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String submit(@RequestParam("name") String name) {
        return "redirect:/";
    }
}
