package me.mziccard.please.rpc;

import me.mziccard.please.rpc.extensions.SearchExtension;
import me.mziccard.please.rpc.extensions.JCenterSearchExtension;
import me.mziccard.please.rpc.extensions.MavenCentralSearchExtension;
import me.mziccard.please.entities.RepositoryPackage;

public class RepositoryOptions {

  private final String mavenBaseUrl;
  private final String apiBaseUrl;
  private final String metadataFilename;
  private final String repo;
  private final String user;
  private final SearchExtension searchExtension;

  public RepositoryOptions(Builder builder) {
      this.mavenBaseUrl = builder.mavenBaseUrl;
      this.apiBaseUrl = builder.apiBaseUrl;
      this.metadataFilename = builder.metadataFilename;
      this.user = builder.user;
      this.repo = builder.repo;
      this.searchExtension = builder.searchExtension;
  }

  public String getMavenBaseUrl() {
    return mavenBaseUrl;
  }

  public String getApiBaseUrl() {
    return apiBaseUrl;
  }

  public String getMetadataFilename() {
    return metadataFilename;
  }  

  public String getUser() {
    return user;
  }

  public String getRepo() {
    return repo;
  }


  public SearchExtension getSearchExtension() {
    return searchExtension;
  }

  public String getMavenUrl(RepositoryPackage dependency) {
    String path = 
      dependency.getGroup().replace('.', '/') +
      '/' +
      dependency.getName() +
      '/';
    if (dependency.getVersion() == null) {
      path += metadataFilename;
    } else {
      path += dependency.getVersion();
    }
    return mavenBaseUrl + path;
  }

  public static class Builder {

    private final String mavenBaseUrl;
    private final String apiBaseUrl;
    private final String metadataFilename;

    private String repo;
    private String user;
    private SearchExtension searchExtension;

    public Builder(
      String mavenBaseUrl, 
      String apiBaseUrl, 
      String metadataFilename) {
        this.mavenBaseUrl = mavenBaseUrl;
        this.apiBaseUrl = apiBaseUrl;
        this.metadataFilename = metadataFilename;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder repo(String repo) {
      this.repo  = repo;
      return this;
    }

    public Builder searchExtension(SearchExtension searchExtension) {
      this.searchExtension  = searchExtension;
      return this;
    }

    public RepositoryOptions build() {
      return new RepositoryOptions(this);
    }
  }
};
