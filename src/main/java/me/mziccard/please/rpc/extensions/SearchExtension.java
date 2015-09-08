package me.mziccard.please.rpc.extensions;

import java.util.List;

import me.mziccard.please.entities.RepositoryPackage;
import me.mziccard.please.rpc.RepositoryService;
import me.mziccard.please.rpc.SearchOptions;

/**
 * Basic interface for a repository search service that allows to interrogate a repo
 * looking for files.
 */
public interface SearchExtension {

  /**
   * Set the repository service this extension is used by
   *
   * @param service   Repository service that uses thsi SearchExtension
   */
  public void setRepositoryService(RepositoryService service);

  /**
   * Search a repository for packages matching the provided options.
   *
   * @param options   Options for packages search
   * @return          List of packages matching the options
   */
  public List<RepositoryPackage> search(SearchOptions options) throws Exception;

}
