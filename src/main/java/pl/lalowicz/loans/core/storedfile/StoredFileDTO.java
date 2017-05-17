package pl.lalowicz.loans.core.storedfile;

/**
 * Created by radoslaw.lalowicz on 2017-05-13.
 */
public class StoredFileDTO {
    private Integer id;
    private String name;

    public StoredFileDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
