package fileio;

public class CredentialsInput {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public CredentialsInput() {
    }

    public CredentialsInput(final CredentialsInput cr) {
        this.name = cr.name;
        this.password = cr.password;
        this.country = cr.country;
        this.balance = cr.balance;
        this.accountType = cr.accountType;
    }

    /** Get the name */
    public String getName() {
        return name;
    }

    /** Set the name */
    public void setName(final String name) {
        this.name = name;
    }

    /** Get the password */
    public String getPassword() {
        return password;
    }

    /** Set the password */
    public void setPassword(final String password) {
        this.password = password;
    }

    /** Get the account type */
    public String getAccountType() {
        return accountType;
    }

    /** Set the account type */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /** Get the user country */
    public String getCountry() {
        return country;
    }

    /** Set the user country */
    public void setCountry(final String country) {
        this.country = country;
    }

    /** Get the balance */
    public String getBalance() {
        return balance;
    }

    /** Set the balance */
    public void setBalance(final String balance) {
        this.balance = balance;
    }


}
