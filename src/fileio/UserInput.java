package fileio;

public class UserInput {
    private CredentialsInput credentials;

    public UserInput() {
    }

    public UserInput(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    public UserInput(final UserInput us) {
        this.credentials = new CredentialsInput(us.getCredentials());
    }

    /** Get the info about the user */
    public CredentialsInput getCredentials() {
        return credentials;
    }

    /** Set the info about the user */
    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

}
