package ddwu.project.mdm_ver2.domain.home.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        System.out.println("in HomeController");

        return "main";
    }
}
