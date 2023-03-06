package fileio;

import database.Movie;

public class ActionInput extends FiltersInput {

    private String type;
    private String page;
    private String movie;
    private String feature;
    private CredentialsInput credentials;
    private String startsWith;
    private String count;
    private Integer rate;
    private FiltersInput filters;
    private String subscribedGenre;
    private Movie addedMovie;
    private String deletedMovie;

    public ActionInput() {

    }

    /** Get the rate */
    public Integer getRate() {
        return rate;
    }

    /** Set the rate */
    public void setRate(final Integer rate) {
        this.rate = rate;
    }

    /** Get count */
    public String getCount() {
        return count;
    }

    /** Set count */
    public void setCount(final String count) {
        this.count = count;
    }

    /** Get the filters */
    public FiltersInput getFilters() {
        return filters;
    }

    /** Set the filters */
    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    /** Get the name of the searched movie */
    public String getStartsWith() {
        return startsWith;
    }

    /** Set the name of the searched movie */
    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    /** Get the type */
    public String getType() {
        return type;
    }

    /** Set the type */
    public void setType(final String type) {
        this.type = type;
    }

    /** Get the page */
    public String getPage() {
        return page;
    }

    /** Set the page */
    public void setPage(final String page) {
        this.page = page;
    }

    /** Get the movie */
    public String getMovie() {
        return movie;
    }

    /** Set the movie */
    public void setMovie(final String movie) {
        this.movie = movie;
    }

    /** Get the feature */
    public String getFeature() {
        return feature;
    }

    /** Set the feature */
    public void setFeature(final String feature) {
        this.feature = feature;
    }

    /** Get the info of the user */
    public CredentialsInput getCredentials() {
        return credentials;
    }

    /** Set the info of the user */
    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    /** Get the subscribed genre */
    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    /** Set the subscribed genre */
    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    /** Get the added movie */
    public Movie getAddedMovie() {
        return addedMovie;
    }

    /** Set the added movie */
    public void setAddedMovie(final Movie addedMovie) {
        this.addedMovie = addedMovie;
    }

    /** Get the deleted movie */
    public String getDeletedMovie() {
        return deletedMovie;
    }

    /** Set the deleted movie */
    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }
}
