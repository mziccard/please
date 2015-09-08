package me.mziccard.please.rpc;

import java.util.List;

import me.mziccard.please.entities.RepositoryPackage;

/**
 * Basic interface for a package repository providing basic services:
 * 1) Check whether a packages exists
 * 2) Get the latestVersion of a package if exists
 * 3) Search the package database for one mathing some search options
 */
public interface RepositoryService {

  public RepositoryOptions getRepositoryOptions();

  public boolean exists(RepositoryPackage item) throws Exception;

  public String latestVersion(RepositoryPackage item) throws Exception;

  public List<RepositoryPackage> search(SearchOptions options) throws Exception;

}
