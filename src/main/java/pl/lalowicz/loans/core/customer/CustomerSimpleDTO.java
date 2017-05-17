package pl.lalowicz.loans.core.customer;

/**
 * Created by radoslaw.lalowicz on 2017-05-07.
 */
public class CustomerSimpleDTO {
    private Integer id;
    private String name;
    private String surname;
    private String totalAmount;
    private int rates;
    private LoanType loanType;

    public CustomerSimpleDTO(Integer id, String name, String surname, String totalAmount, int rates, LoanType loanType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.totalAmount = totalAmount;
        this.rates = rates;
        this.loanType = loanType;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getRates() {
        return rates;
    }

    public void setRates(int rates) {
        this.rates = rates;
    }

}
