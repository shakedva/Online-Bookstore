package hac.ex4.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/edit")
    public String mainWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model)
    {
        model.addAttribute("message", "Admin");
        return "adminEdit";
    }
}
