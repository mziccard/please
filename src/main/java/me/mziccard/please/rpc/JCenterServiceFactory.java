package me.mziccard.please.rpc;

import me.mziccard.please.rpc.extensions.JCenterSearchExtension;

/**
 * Base class for JCenter {@code RepositoryService} factories.
 */
public abstract class JCenterServiceFactory implements RepositoryServiceFactory {

  public static final RepositoryOptions DEFAULT_OPTIONS = 
    new RepositoryOptions.Builder(
      "https://jcenter.bintray.com/",
      "https://api.bintray.com/",
      "maven-metadata.xml")
        .searchExtension(new JCenterSearchExtension())
        .repo("jcenter")
        .user("bintray")
        .build();

  private static final RepositoryServiceFactory INSTANCE = 
    new RepositoryServiceFactory() {
      @Override
      public RepositoryService get(RepositoryOptions options) {
        return new MavenRepositoryService(options);
      }
      @Override
      public RepositoryService get() {
        return new MavenRepositoryService(DEFAULT_OPTIONS);
      }
    };

  /**
   * Return the JCenter factory instance.
   *
   * @return  The JCenter {@code RepositoryService} factory
   */
  public static RepositoryServiceFactory instance() {
    return INSTANCE;
  }
};
