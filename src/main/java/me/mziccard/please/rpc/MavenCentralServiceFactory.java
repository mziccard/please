package me.mziccard.please.rpc;

import me.mziccard.please.rpc.extensions.MavenCentralSearchExtension;


/**
 * Base class for Maven Central {@code RepositoryService} factories.
 */
public abstract class MavenCentralServiceFactory implements RepositoryServiceFactory {

  public static final RepositoryOptions DEFAULT_OPTIONS = 
    new RepositoryOptions.Builder(
      "https://repo1.maven.org/maven2/",
      "https://search.maven.org/",
      "maven-metadata.xml")
        .searchExtension(new MavenCentralSearchExtension())
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
   * Return the Maven Central factory instance.
   *
   * @return  The Maven Central {@code RepositoryService} factory
   */
  public static RepositoryServiceFactory instance() {
    return INSTANCE;
  }
};
