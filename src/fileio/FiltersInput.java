package fileio;

public class FiltersInput {

    private SortInput sort;
    private ContainsInput contains;

    public FiltersInput() {

    }

    /** Get the parameters for sorting */
    public SortInput getSort() {
        return sort;
    }

    /** Set the parameters for sorting */
    public void setSort(final SortInput sort) {
        this.sort = sort;
    }

    /** Get the contains parameters */
    public ContainsInput getContains() {
        return contains;
    }

    /** Set the contains parameters */
    public void setContains(final ContainsInput contains) {
        this.contains = contains;
    }


}
