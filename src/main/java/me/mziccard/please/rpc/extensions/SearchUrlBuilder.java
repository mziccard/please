package me.mziccard.please.rpc.extensions;

import me.mziccard.please.rpc.RepositoryOptions;
import me.mziccard.please.rpc.SearchOptions;

/**
 * Interface for a search URI builder
 */
public interface SearchUrlBuilder {

  /**
   * Build a URI to search a repository.
   *
   * @param repositoryOptions Options for the repository
   * @param searchOptions     Options for the search to be performed
   * @return                  The URI with the search results
   */
  public String build(
    RepositoryOptions repositoryOptions, 
    SearchOptions searchOptions);

}