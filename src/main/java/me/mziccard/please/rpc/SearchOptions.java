package me.mziccard.please.rpc;

/**
 * Class holding options for a repository search.
 */
public class SearchOptions {

  private final String searchString;
  private final int startPos;
  private final int count;

  private SearchOptions(Builder builder) {
      this.searchString = builder.searchString;
      this.startPos = builder.startPos;
      this.count = builder.count;
  }

  /**
   * Get the string to be searched.
   * @return  The string to be searched
   */
  public String getSearchString() {
    return searchString;
  }

  /**
   * Get the position to start with in paginated search.
   * @return  The search position
   */
  public int getStartPos() {
    return startPos;
  }

  /**
   * Get the maximum number of results to be shown in the search.
   * @return  The maximum number of results
   */
  public int getCount() {
    return count;
  }

  /**
   * Builder for {@code SearchOptions} objects.
   */
  public static class Builder {
    private final String searchString;
    private int startPos;
    private int count;

    /**
     * Builder constructor.
     *
     * @param searchString  String to be searched
     */
    public Builder(String searchString) {
      this.searchString = searchString;
      this.count = 20;
    }

    /**
     * Start position setter.
     *
     * @param startPos  Set the start position in a paginated search
     * @return          The builder object
     */
    public Builder startPos(int startPos) {
      this.startPos = startPos;
      return this;
    }

    /**
     * Maximum number of results to show.
     *
     * @param count  Set the number of results to show
     * @return       The builder object
     */
    public Builder count(int count) {
      this.count = count;
      return this;
    }

    /**
     * Build a {@code SearchOptions} object from this builder.
     *
     * @return  A {@code SearchOptions} object
     */
    public SearchOptions build() {
      return new SearchOptions(this);
    }
  }
};
