package me.mziccard.please.managers;

import me.mziccard.please.entities.RepositoryPackage;

public class ConsoleManager implements DependenciesManager {

  private final String preamble;

  public ConsoleManager(String preamble) {
    this.preamble = preamble;
  }

  @Override
  public void addDependency(DependencyFormatter formatter) {
    System.out.println(preamble);
    System.out.println();
    System.out.println(formatter.format());
    System.out.println();
  }
}