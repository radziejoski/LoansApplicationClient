package pl.lalowicz.loans.core.customer;

import java.util.List;

/**
 * Created by radoslaw.lalowicz on 2017-05-12.
 */
public interface CustomerRemoteService {

    CustomerDTO getCustomer(Long id);

    List<CustomerSimpleDTO> search(CustomerQuery query);

    void create(CustomerBean customer) throws Exception;

    void update(CustomerBean customer) throws Exception;
}
