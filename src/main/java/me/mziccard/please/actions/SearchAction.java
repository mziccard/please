package me.mziccard.please.actions;

import java.util.List;

import me.mziccard.please.entities.RepositoryPackage;
import me.mziccard.please.rpc.JCenterServiceFactory;
import me.mziccard.please.rpc.MavenCentralServiceFactory;
import me.mziccard.please.rpc.RepositoryService;
import me.mziccard.please.rpc.SearchOptions;
import me.mziccard.please.common.ExceptionHandler;

public class SearchAction implements Action {

  private RepositoryPackage dependency;
  private ActionOptions options;
  private RepositoryService repositoryService;

  public SearchAction(
    RepositoryPackage dependency,
    ActionOptions options) {
    this.dependency = dependency;
    this.options = options;
    switch(options.repository()) {
      case MAVEN_CENTRAL:
        repositoryService = MavenCentralServiceFactory.instance().get();
        break;
      case JCENTER:
        repositoryService = JCenterServiceFactory.instance().get();
        break;
      default:
        repositoryService = MavenCentralServiceFactory.instance().get();
        break;
    }
  }

  @Override
  public void apply() {
    System.out.println("Searching artifact...");
    SearchOptions searchOptions = 
      new SearchOptions.Builder(dependency.getName()).build();
    try {
      List<RepositoryPackage> packages = repositoryService.search(searchOptions);
      for (RepositoryPackage p : packages) {
        System.out.println(p);
      }
    } catch (Exception e) {
      ExceptionHandler.instance().handle(e);
    }
  }
};