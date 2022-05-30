package hac.ex4.admin;

import org.springframework.util.MultiValueMap;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Validator {

    public Validator(){}

    public boolean validateBook(MultiValueMap<String, String> values)
    {
        return (!String.valueOf(values.get("bookName").get(0)).trim().equals("")) &&
                (parseInt(String.valueOf(values.get("bookQuantity").get(0))) >= 0) &&
                (!(parseDouble(String.valueOf(values.get("bookPrice").get(0))) <= 0)) &&
                (!(parseDouble(String.valueOf(values.get("bookDiscount").get(0))) < 0));
    }
}
