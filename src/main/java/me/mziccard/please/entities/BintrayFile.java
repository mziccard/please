package me.mziccard.please.entities;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class BintrayFile {

  private final String name;
  private final String path;
  @SerializedName("package")
  private final String packageName;
  private final String version;
  private final String repo;
  private final String owner;
  private final Date created;
  private final int size;
  private final String sha1;

  public String getName() {
    return name;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getVersion() {
    return version;
  }

  public String getRepo() {
    return repo;
  }

  public String getOwner() {
    return owner;
  }

  public Date getCreated() {
    return created;
  }

  public BintrayFile(
    String name,
    String path,
    String packageName,
    String version,
    String repo,
    String owner,
    Date created,
    int size,
    String sha1) {
      this.name = name;
      this.path = path;
      this.packageName = packageName;
      this.version = version;
      this.repo = repo;
      this.owner = owner;
      this.created = created;
      this.size = size;
      this.sha1 = sha1;
  }
};
