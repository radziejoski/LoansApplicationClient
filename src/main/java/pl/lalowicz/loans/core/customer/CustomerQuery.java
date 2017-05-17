package pl.lalowicz.loans.core.customer;

import java.util.Optional;

/**
 * Created by radoslaw.lalowicz on 2017-05-06.
 */
public class CustomerQuery {
    private Optional<LoanType> loanType = Optional.empty();
    private Optional<String> fiscalCode = Optional.empty();

    public Optional<String> getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = Optional.of(fiscalCode);
    }

    public Optional<LoanType> getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = Optional.of(loanType);
    }
}
