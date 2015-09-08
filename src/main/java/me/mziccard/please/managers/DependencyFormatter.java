package me.mziccard.please.managers;

import me.mziccard.please.entities.RepositoryPackage;

public interface DependencyFormatter {

  public String format();

  public static class Maven implements DependencyFormatter {

    private final RepositoryPackage dependency;
    private final boolean dev;

    public Maven(RepositoryPackage dependency, boolean dev) {
      this.dependency = dependency;
      this.dev = dev;
    }

    @Override
    public String format() {
      String dependencyString = 
        "<dependency>" +
        System.lineSeparator() +
        "  <groupId>" + 
        dependency.getGroup() +
        "</groupId>" +
        System.lineSeparator() +
        "  <artifactId>" +
        dependency.getName() +
        "</artifactId>" +
        System.lineSeparator() +
        "  <version>" +
        dependency.getVersion() +
        "</version>" +
        System.lineSeparator();
      if (dev) {
        dependencyString +=
          "  <scope>" +
          "test" +
          "</scope>" +
          System.lineSeparator();
      }
      return dependencyString + "</dependency>";
    }
  }

  public static class Gradle implements DependencyFormatter {

    private final RepositoryPackage dependency;
    private final boolean dev;

    public Gradle(RepositoryPackage dependency, boolean dev) {
      this.dependency = dependency;
      this.dev = dev;
    }

    @Override
    public String format() {
      String dependencyString = null;
      if (dev) {
        dependencyString = "testCompile";
      } else {
        dependencyString = "compile";
      }
      dependencyString +=
        " group: '" +
        dependency.getGroup() +
        "' name: '" +
        dependency.getName() +
        "' version: '" +
        dependency.getVersion() +
        "'";
      return dependencyString;
    }
  }

  public static class Sbt implements DependencyFormatter {

    private final RepositoryPackage dependency;
    private final boolean dev;

    public Sbt(RepositoryPackage dependency, boolean dev) {
      this.dependency = dependency;
      this.dev = dev;
    }

    @Override
    public String format() {
      String dependencyString = 
        "libraryDependencies += \"" +
        dependency.getGroup() +
        "\" % \"" +
        dependency.getName() +
        "\" % \"" +
        dependency.getVersion() +
        "\"";
      if (dev) {
        dependencyString += " % \"test\"";
      }
      return dependencyString;
    }
  }
}