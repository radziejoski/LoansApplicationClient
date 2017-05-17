package pl.lalowicz.loans.core.customer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by radoslaw.lalowicz on 2017-05-12.
 */
@Service("customerRemoteService")
@Transactional
public class CustomerRemoteServiceImpl implements CustomerRemoteService {

    @Autowired
    private Environment environment;

    @Override
    public CustomerDTO getCustomer(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        CustomerDTO customer = restTemplate.getForObject(environment.getRequiredProperty("webservices.path") + "customer/" + id, CustomerDTO.class);
        return customer;
    }

    @Override
    public List<CustomerSimpleDTO> search(CustomerQuery query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        Optional<LoanType> loanTypeOpt = query.getLoanType();
        Optional<String> fiscalCodeOpt = query.getFiscalCode();

        LoanType loanType = loanTypeOpt.isPresent() ? loanTypeOpt.get() : null;
        String fiscalCode = fiscalCodeOpt.isPresent() ? fiscalCodeOpt.get() : null;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(environment.getRequiredProperty("webservices.path") + "/customers")
                .queryParam("type", loanType)
                .queryParam("code", fiscalCode);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List> exchange = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, List.class);

        return convertFromMap(exchange.getBody());
    }

    @Override
    public void create(CustomerBean customer) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        URL url = new URL(environment.getRequiredProperty("webservices.path") + "customer");
        CustomerBean customerBean = restTemplate.postForObject(url.toURI(), customer, CustomerBean.class);

        List<File> files = customer.getFiles();
        if (!files.isEmpty()) {
            MultipartEntity mpEntity = new MultipartEntity();
            addFilesToMultipartEntity(files, mpEntity);
            HttpPost httppost = new HttpPost(environment.getRequiredProperty("webservices.path") + "uploadFiles");
            mpEntity.addPart("id", new StringBody(customerBean.getId().toString(), ContentType.DEFAULT_TEXT));

            httppost.setEntity(mpEntity);
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpResponse response = httpClient.execute(httppost);
        }
    }

    @Override
    public void update(CustomerBean customer) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        HttpPost httppost = new HttpPost(environment.getRequiredProperty("webservices.path") + "add");

        MultipartEntity mpEntity = new MultipartEntity();

        addFilesToMultipartEntity(customer.getFiles(), mpEntity);

        ContentBody totalAmount = new StringBody(customer.getTotalAmount(), ContentType.DEFAULT_TEXT);
        ContentBody rates = new StringBody(customer.getRates().toString(), ContentType.DEFAULT_TEXT);
        ContentBody id = new StringBody(customer.getId().toString(), ContentType.DEFAULT_TEXT);
        mpEntity.addPart("totalAmount", totalAmount);
        mpEntity.addPart("rates", rates);
        mpEntity.addPart("id", id);

        httppost.setEntity(mpEntity);

        HttpResponse response = httpClient.execute(httppost);
        org.apache.http.HttpEntity resEntity = response.getEntity();

        if (resEntity != null) {
            resEntity.consumeContent();
        }

        httpClient.getConnectionManager().shutdown();
    }

    private MultipartEntity addFilesToMultipartEntity(List<File> files, MultipartEntity mpEntity) {
        files.stream().forEach(file ->
                mpEntity.addPart("file", new FileBody(file, ContentType.MULTIPART_FORM_DATA)));
        return mpEntity;
    }

    private List<CustomerSimpleDTO> convertFromMap(List<LinkedHashMap<String, Object>> map) {
        List<CustomerSimpleDTO> customers = new ArrayList<>();
        if (map == null)
            return customers;

        for (LinkedHashMap<String, Object> entry : map) {
            Integer id = (Integer) entry.get("id");
            String name = (String) entry.get("name");
            String surname = (String) entry.get("surname");
            LoanType loanType = LoanType.valueOf((String) entry.get("loanType"));
            String totalAmount = (String) entry.get("totalAmount");
            int rates = (Integer) entry.get("rates");

            customers.add(new CustomerSimpleDTO(id, name, surname, totalAmount, rates, loanType));
        }
        return customers;
    }
}
