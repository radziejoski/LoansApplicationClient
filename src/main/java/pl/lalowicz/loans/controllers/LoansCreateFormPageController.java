package pl.lalowicz.loans.controllers;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.lalowicz.loans.core.customer.CustomerBean;
import pl.lalowicz.loans.core.customer.LoanType;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by user on 2017-05-04.
 */
@Controller
@SessionAttributes("customer")
public class LoansCreateFormPageController {


    @RequestMapping(value = "/loansCreateForm", method = RequestMethod.GET)
    public String loadPage(Model model) {
        model.addAttribute("loanTypes", LoanType.values());
        model.addAttribute("customer", new CustomerBean());
        return "loansCreateForm";
    }

    @RequestMapping(value = "/loansCreateForm", method = RequestMethod.POST)
    public String createLoan(@ModelAttribute("customer") CustomerBean customer, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "loansCreateForm";
        }
        return "redirect:/step2";
    }

    @ModelAttribute("customer")
    public CustomerBean getCustomerBean(@ModelAttribute("customer") CustomerBean customer) {
        return customer;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
