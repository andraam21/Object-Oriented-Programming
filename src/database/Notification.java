package database;

public class Notification {
    private String movieName;
    private String message;

    public Notification() {
        this.movieName = "No recommendation";
        this.message = "Recommendation";
    }

    /** Get the movie */
    public String getMovieName() {
        return movieName;
    }

    /** Set the movie */
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    /** Get the message */
    public String getMessage() {
        return message;
    }

    /** Set the message */
    public void setMessage(final String message) {
        this.message = message;
    }
}
