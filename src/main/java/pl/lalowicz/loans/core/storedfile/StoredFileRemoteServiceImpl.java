package pl.lalowicz.loans.core.storedfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by radoslaw.lalowicz on 2017-05-13.
 */
@Transactional
@Service("storedFileService")
public class StoredFileRemoteServiceImpl implements StoredFileRemoteService {

    @Autowired
    private Environment environment;

    @Override
    public List<StoredFileDTO> search(StoredFileQuery query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(environment.getRequiredProperty("webservices.path") + "files/" + query.getId());
        HttpEntity<?> entity = new HttpEntity<>(headers);


        ResponseEntity<List> exchange = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, List.class);

        return convertFromMap(exchange.getBody());

    }

    @Override
    public ResponseEntity<byte[]> get(Long id) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(environment.getRequiredProperty("webservices.path") + "/downloadFile/" + id, HttpMethod.GET, entity, byte[].class, "1");
    }

    @Override
    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(environment.getRequiredProperty("webservices.path") + "deleteFile/" + id);
    }

    private List<StoredFileDTO> convertFromMap(List<LinkedHashMap<String, Object>> map) {
        List<StoredFileDTO> files = new ArrayList<>();
        if (files == null)
            return files;

        for (LinkedHashMap<String, Object> entry : map) {
            Integer id = (Integer) entry.get("id");
            String name = (String) entry.get("fileName");
            files.add(new StoredFileDTO(id, name));
        }
        return files;
    }
}
