package me.mziccard.please.rpc.adapters;

import me.mziccard.please.entities.RepositoryPackage;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class DistinctPackageListAdapter {

  protected final List<RepositoryPackage> packages;
  private final HashSet<String> distinctPackageIdentifiers;

  public DistinctPackageListAdapter() {
    this.packages = new ArrayList<RepositoryPackage>();
    this.distinctPackageIdentifiers = new HashSet<String>();
  }

  public void add(RepositoryPackage dependencyFile) {
    String packageString = dependencyFile.getGroup() + ":" + dependencyFile.getName();
    if (!distinctPackageIdentifiers.contains(packageString)) {
      distinctPackageIdentifiers.add(packageString);
      this.packages.add(dependencyFile);
    }
  }

  public void add(List<RepositoryPackage> packages) {
    for (RepositoryPackage dependencyFile : packages) {
      this.add(dependencyFile);
    }
  }

  public List<RepositoryPackage> list() {
    return this.packages;
  }
}