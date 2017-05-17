package pl.lalowicz.loans.core.storedfile;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by radoslaw.lalowicz on 2017-05-13.
 */
public interface StoredFileRemoteService {
    List<StoredFileDTO> search(StoredFileQuery query);

    ResponseEntity<byte[]> get(Long id) throws Exception;

    void delete(Long id);
}
