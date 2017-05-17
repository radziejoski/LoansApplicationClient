package pl.lalowicz.loans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.lalowicz.loans.core.customer.CustomerBean;
import pl.lalowicz.loans.core.customer.CustomerRemoteService;

/**
 * Created by radoslaw.lalowicz on 2017-05-07.
 */
@Controller
@SessionAttributes("customer")
public class Step2Controller {

    @Autowired
    private CustomerRemoteService customerService;

    @RequestMapping(value = "/step2", method = RequestMethod.GET)
    public String loadPage(@ModelAttribute("customer") CustomerBean customerBean, Model model) {
        model.addAttribute("customer", customerBean);
        return "step2";
    }

    @RequestMapping(value = "/step2", method = RequestMethod.POST)
    public String getData(@ModelAttribute("customer") CustomerBean customerBean) {
        return "redirect:/step3";
    }

    @ModelAttribute("customer")
    public CustomerBean getCustomerBean(@ModelAttribute("customer") CustomerBean customer) {
        return customer;
    }
}
