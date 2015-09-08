package me.mziccard.please.entities;

public class MavenCentralPackage {
  private final String id;
  private final String g;
  private final String a;
  private final String latestVersion;
  private final String repositoryId;
  private final String p;
  private final long timestamp;
  private final int versionCount;
  private final String[] text;
  private final String[] ec;

  public String getId() {
    return id;
  }

  public String getG() {
    return g;
  }

  public String getA() {
    return a;
  }

  public String getLatestVersion() {
    return latestVersion;
  }

  public String getRepositoryId() {
    return repositoryId;
  }

  public String getP() {
    return p;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getVersionCount() {
    return versionCount;
  }

  public String[] getText() {
    return text;
  }
  public String[] getEc() {
    return ec;
  }

  public MavenCentralPackage(
    String id,
    String g,
    String a,
    String latestVersion,
    String repositoryId,
    String p,
    long timestamp,
    int versionCount,
    String[] text,
    String[] ec) {
      this.id = id;
      this.g = g;
      this.a = a;
      this.latestVersion = latestVersion;
      this.repositoryId = repositoryId;
      this.p = p;
      this.timestamp = timestamp;
      this.versionCount = versionCount;
      this.text = text;
      this.ec = ec;
  }
}