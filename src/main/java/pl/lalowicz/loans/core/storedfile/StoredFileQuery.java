package pl.lalowicz.loans.core.storedfile;

/**
 * Created by radoslaw.lalowicz on 2017-05-17.
 */
public class StoredFileQuery {
    private Long id;

    public StoredFileQuery(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
