package pl.lalowicz.loans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import pl.lalowicz.loans.core.customer.CustomerBean;
import pl.lalowicz.loans.core.customer.CustomerRemoteService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by radoslaw.lalowicz on 2017-05-07.
 */
@Controller
@SessionAttributes("customer")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Step3Controller {

    @Autowired
    private CustomerRemoteService customerService;

    private List<File> files = new ArrayList<>();

    @RequestMapping(value = "/step3")
    public String loadPage(@ModelAttribute("customer") CustomerBean customerBean, Model model) {
        model.addAttribute("customer", customerBean);
        model.addAttribute("files", files);
        return "step3";
    }

    @RequestMapping(value = "/proceed")
    public String saveCustomerLoan(@ModelAttribute("customer") CustomerBean customerBean, Model model, SessionStatus status) throws Exception {
        if (!files.isEmpty()) {
            customerBean.setFiles(files);
        }
        customerService.create(customerBean);
        status.setComplete();
        return "redirect:/mainPage";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String uploadFile(@ModelAttribute("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/step3";
        }
        File temp = new File(System.getProperty("user.home") + "/" + file.getOriginalFilename());

        files.add(temp);

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(System.getProperty("user.home") + "/" + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/step3";
    }

    @RequestMapping(value = "/step3/delete/{name}", method = RequestMethod.GET)
    public String delete(@PathVariable("name") String name) {
        List<File> toRemove = new ArrayList<>();
        files.stream().forEach(file -> {
            String fileName = file.getName();
            String nameWithoutExtension = fileName.substring(0, fileName.length() - 4);
            if (Objects.equals(nameWithoutExtension, name)) {
                toRemove.add(file);
            }
        });
        files.removeAll(toRemove);
        return "redirect:/step3";
    }

    @RequestMapping(value = "/step3/download/{name}", method = RequestMethod.GET)
    public String dwonload(@PathVariable("name") String name, HttpServletResponse response) {
        files.stream().forEach(file -> {
            try {
                InputStream in = new FileInputStream(file);

                response.setContentType("plain/text");
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                response.setHeader("Content-Length", String.valueOf(file.length()));
                FileCopyUtils.copy(in, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return "redirect:/step3";
    }

}
