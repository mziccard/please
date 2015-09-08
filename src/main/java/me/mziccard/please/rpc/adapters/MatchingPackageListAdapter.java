package me.mziccard.please.rpc.adapters;

import me.mziccard.please.entities.RepositoryPackage;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

public class MatchingPackageListAdapter extends DistinctPackageListAdapter {

  private final RepositoryPackage dependency;

  public MatchingPackageListAdapter(RepositoryPackage dependency) {
    super();
    this.dependency = dependency;
  }

  @Override
  public void add(RepositoryPackage p) {
    if (p.getGroup() != null && 
      p.getName() != null &&
      p.getGroup().equals(dependency.getGroup()) &&
      p.getName().equals(dependency.getName())) {
        System.out.println("=>"+p);
        super.add(p);
    }
  }

  @Override
  public void add(List<RepositoryPackage> packages) {
    for(RepositoryPackage dependencyFile : packages) {
      this.add(dependencyFile);
    }
  }

  @Override
  public List<RepositoryPackage> list() {
    Collections.sort(packages, new Comparator<RepositoryPackage>() {
      @Override
      public int compare(RepositoryPackage o1, RepositoryPackage o2) {
        return -(o1.getCreatedAt().compareTo(o2.getCreatedAt()));
      }
    });
    return packages;
  }
}