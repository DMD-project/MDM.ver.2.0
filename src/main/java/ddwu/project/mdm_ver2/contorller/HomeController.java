package ddwu.project.mdm_ver2.contorller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestController
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {

        System.out.println("in HomeController");

        return "test";
    }
}
