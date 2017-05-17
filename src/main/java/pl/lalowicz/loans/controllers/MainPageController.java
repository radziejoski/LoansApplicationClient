package pl.lalowicz.loans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lalowicz.loans.core.customer.CustomerQuery;
import pl.lalowicz.loans.core.customer.CustomerRemoteService;
import pl.lalowicz.loans.core.customer.CustomerSimpleDTO;
import pl.lalowicz.loans.core.customer.LoanType;

import java.util.List;

/**
 * Created by radoslaw.lalowicz on 2017-05-05.
 */
@Controller
public class MainPageController {

    @Autowired
    private CustomerRemoteService customerRemoteService;

    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String loadPage(Model model) {
        List<CustomerSimpleDTO> customersList = customerRemoteService.search(new CustomerQuery());
        model.addAttribute("customersList", customersList);
        model.addAttribute("loanTypes", LoanType.values());
        model.addAttribute("query", new SearchBean());

        return "mainPage";
    }

    @RequestMapping(value = "/mainPage", method = RequestMethod.POST)
    public String searchResult(@ModelAttribute("query") SearchBean queryBean, Model model) {

        List<CustomerSimpleDTO> customersList = customerRemoteService.search(queryBean.createQuery());
        model.addAttribute("loanTypes", LoanType.values());
        model.addAttribute("customersList", customersList);
        return "mainPage";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
