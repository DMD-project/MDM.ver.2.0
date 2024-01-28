package ddwu.project.mdm_ver2.global.mapping.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MappingController {

    @GetMapping("/")
    public String main() {
        return "main";
    }
}
