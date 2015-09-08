package me.mziccard.please.actions;

import java.util.List;
import java.io.IOException;
import java.lang.UnsupportedOperationException;
import java.text.ParseException;

import me.mziccard.please.entities.RepositoryPackage;
import me.mziccard.please.managers.DependencyFormatter;
import me.mziccard.please.managers.DependenciesManager;
import me.mziccard.please.managers.ConsoleManager;
import me.mziccard.please.rpc.JCenterServiceFactory;
import me.mziccard.please.rpc.MavenCentralServiceFactory;
import me.mziccard.please.rpc.RepositoryService;
import me.mziccard.please.rpc.SearchOptions;
import me.mziccard.please.common.ExceptionHandler;
import me.mziccard.please.common.ResourceNotFoundException;

public class AddAction implements Action {

  private RepositoryPackage dependency;
  private ActionOptions options;
  private RepositoryService repositoryService;

  public AddAction(
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

  public void search() throws Exception {
    System.out.println("Dependency not found, similar artifacts are:");
    SearchOptions searchOptions = 
      new SearchOptions.Builder(dependency.getName()).build();
    List<RepositoryPackage> packages = repositoryService.search(searchOptions);
    for (RepositoryPackage similarDependency : packages) {
      System.out.println(similarDependency);
    }
  }

  public void save() {
    DependenciesManager mavenManager = null;
    DependenciesManager gradleManager = null;
    DependenciesManager sbtManager = null;
    DependencyFormatter mavenFormatter =
      new DependencyFormatter.Maven(dependency, options.dev());
    DependencyFormatter gradleFormatter =
      new DependencyFormatter.Gradle(dependency, options.dev());
    DependencyFormatter sbtFormatter =
      new DependencyFormatter.Sbt(dependency, options.dev());

    if (options.save()) {
      System.out.println("Save as a dependency");
      // TODO: change to MavenManager, SbtManager and GradleManager
      mavenManager = new ConsoleManager("");
      gradleManager = new ConsoleManager("");
      sbtManager = new ConsoleManager("");
    } else {
      System.out.println("Print maven, sbt and gradle code");
      mavenManager = new ConsoleManager("Maven dependency:");
      gradleManager = new ConsoleManager("Gradle dependency: ");
      sbtManager = new ConsoleManager("Sbt dependency: ");
    }

    mavenManager.addDependency(mavenFormatter);
    gradleManager.addDependency(gradleFormatter);
    sbtManager.addDependency(sbtFormatter);
  }

  @Override
  public void apply() {
    System.out.println("Adding dependency...");
    try {
      if (dependency.getVersion() == null) {
        try {
          dependency.setVersion(repositoryService.latestVersion(dependency));
          save();
        } catch(ResourceNotFoundException e) {
          search();
        } catch(IOException e) {
          e.printStackTrace(System.out);
          ExceptionHandler.instance().handle(e); 
        }
      } else {
        if (repositoryService.exists(dependency)) {
          save();
        } else {
          search();
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      ExceptionHandler.instance().handle(e);
    }
  }
};