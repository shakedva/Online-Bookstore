package hac.ex4.admin;

import hac.ex4.dao.Book;
//import hac.ex4.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;


@Controller
@RequestMapping("/admin")
public class AdminController {

//    @Autowired
//    public BookRepository bookRepository;

    @GetMapping("/edit")
    public String adminEditStore(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model)
    {
        return "adminEdit";
    }

    @PostMapping("/edit")
    public String adminAddBook(@RequestParam MultiValueMap<String, String> values)
    {
        System.out.println("~~Book name: = " + values.get("bookName"));
        Validator v = new Validator();
        if(!v.validateBook(values)) {
            System.out.println("book is not correct");
            return "adminEdit";
        }
        Book b = new Book(String.valueOf(values.get("bookName").get(0)).trim(),
                    String.valueOf(values.get("bookImage").get(0)),
                    parseInt(String.valueOf(values.get("bookQuantity").get(0))),
                    parseDouble(String.valueOf(values.get("bookPrice").get(0))),
                    parseDouble(String.valueOf(values.get("bookDiscount").get(0))));

//        bookRepository.save(b);
        return "adminEdit";
    }

//
//    @PostMapping("/edit")
//    public String addUser(Book user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "cannot add book";
//        }
//
//        bookRepository.save(user);
//        return "redirect:/admin/edit";
//    }
}
