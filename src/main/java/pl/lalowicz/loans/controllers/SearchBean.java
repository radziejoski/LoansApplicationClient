package pl.lalowicz.loans.controllers;

import org.springframework.stereotype.Component;
import pl.lalowicz.loans.core.customer.CustomerQuery;
import pl.lalowicz.loans.core.customer.LoanType;

/**
 * Created by radoslaw.lalowicz on 2017-05-09.
 */
@Component
public class SearchBean {
    private String loanType;
    private String fiscalCode;

    public CustomerQuery createQuery() {
        CustomerQuery query = new CustomerQuery();
        if (fiscalCode != null) {
            query.setFiscalCode(fiscalCode);
        }
        if (loanType != null) {
            query.setLoanType(LoanType.valueOf(loanType));
        }
        return query;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }
}
