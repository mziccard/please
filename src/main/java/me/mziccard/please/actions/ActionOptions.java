package me.mziccard.please.actions;

import me.mziccard.please.rpc.RepositoryOptions;

public class ActionOptions {

  public static enum Repository {
    MAVEN_CENTRAL,
    JCENTER
  }

  private final Repository repository;
  private final boolean save;
  private final boolean dev;
  private final RepositoryOptions repositoryOptions;

  private ActionOptions(Builder builder) {
    this.repository = builder.repository;
    this.save = builder.save;
    this.dev = builder.dev;
    this.repositoryOptions = builder.repositoryOptions;
  }

  public Repository repository() {
    return this.repository;
  }

  public boolean save() {
    return this.save;
  }

  public boolean dev() {
    return this.dev;
  }

  public RepositoryOptions repositoryOptions() {
    return this.repositoryOptions;
  }

  public static class Builder {

    private Repository repository;
    private RepositoryOptions repositoryOptions;
    private boolean save;
    private boolean dev;

    public Builder(Repository repository) {
      this.repository = repository;
    }

    public Builder save() {
      save = true;
      return this;
    }

    public Builder dev() {
      dev = true;
      return this;
    }

    public Builder repositoryOptions(RepositoryOptions repositoryOptions) {
      this.repositoryOptions = repositoryOptions;
      return this;
    }

    public ActionOptions build() {
      ActionOptions options = new ActionOptions(this);
      return options;
    }
  }
};