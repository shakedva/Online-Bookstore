package hac.ex4.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * a controller to implements the ErrorController to represent an 'error' page
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * @return an error page in case of an error
     */
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

}