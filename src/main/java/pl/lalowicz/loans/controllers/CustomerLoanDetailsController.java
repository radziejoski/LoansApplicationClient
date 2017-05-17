package pl.lalowicz.loans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import pl.lalowicz.loans.core.customer.CustomerBean;
import pl.lalowicz.loans.core.customer.CustomerDTO;
import pl.lalowicz.loans.core.customer.CustomerRemoteService;
import pl.lalowicz.loans.core.storedfile.StoredFileDTO;
import pl.lalowicz.loans.core.storedfile.StoredFileQuery;
import pl.lalowicz.loans.core.storedfile.StoredFileRemoteService;

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

/**
 * Created by radoslaw.lalowicz on 2017-05-06.
 */
@Controller
@Transactional
@Scope(scopeName = "session")
public class CustomerLoanDetailsController {

    @Autowired
    private CustomerRemoteService customerRemoteService;

    @Autowired
    private StoredFileRemoteService storedFileRemoteService;

    private Long id;
    private List<StoredFileDTO> storedFiles = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    private CustomerDTO customer;

    @RequestMapping(value = "/customerLoanDetails/{id}")
    public String loadPage(@PathVariable("id") Long id, Model model) {
        this.id = id;
        customer = customerRemoteService.getCustomer(id);
        storedFiles = storedFileRemoteService.search(new StoredFileQuery(id));
        return "redirect:/customerLoanDetails";
    }

    @RequestMapping(value = "/customerLoanDetails")
    public String reload(Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("files", storedFiles);
        return "customerLoanDetails";
    }

    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public String uploadFile(@ModelAttribute("file") MultipartFile file) {
        File temp = new File(System.getProperty("user.home") + "/" + file.getOriginalFilename());
        if (file.isEmpty()) {
            return "redirect:/customerLoanDetails";
        }

        files.add(temp);
        storedFiles.add(new StoredFileDTO(null, temp.getName()));

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(System.getProperty("user.home") + "/" + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/customerLoanDetails";
    }

    @RequestMapping(value = "/download/{id}")
    public String downloadFile(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        ResponseEntity<byte[]> responseEtity = storedFileRemoteService.get(id);
        if (responseEtity.getStatusCode().equals(HttpStatus.OK)) {
            response.setHeader("Content-Disposition", "attachment; filename=" + "downloadedFile.pdf");
            response.setHeader("Content-Type", "application/pdf");
            InputStream input = new FileInputStream(new File("filename.pdf"));
            FileCopyUtils.copy(input, response.getOutputStream());
        }
        return "redirect:/customerLoanDetails";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteFile(@PathVariable("id") Long fileId) {
        storedFileRemoteService.delete(fileId);
        return "redirect:/customerLoanDetails/" + id;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("customer") CustomerBean customerBean, SessionStatus status) throws Exception {
        if (!files.isEmpty()) {
            customerBean.setFiles(files);
        }
        customerBean.setId(id);
        customerRemoteService.update(customerBean);
        status.setComplete();
        return "redirect:/mainPage";
    }


}
