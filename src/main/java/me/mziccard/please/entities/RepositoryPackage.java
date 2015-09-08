package me.mziccard.please.entities;

import java.util.Date;
import java.text.ParseException;

public class RepositoryPackage {

  private final String group;
  private final String name;
  private String version;
  private final String repo;
  private final String owner;
  private final Date createdAt;

  public String getGroup() {
    return group;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getRepo() {
    return repo;
  }

  public String getOwner() {
    return owner;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public RepositoryPackage(
    String group,
    String name,
    String version,
    String repo,
    String owner,
    Date createdAt) {
      this.group = group;
      this.name = name;
      this.version = version;
      this.repo = repo;
      this.owner = owner;
      this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return 
      group +
      ":" +
      name +
      "@" +
      version;
  }

  public static class Parser 
    implements CommandLineParser<RepositoryPackage> {

    private final String option;

    public Parser(String option) {
      this.option = option;
    }

    @Override
    public RepositoryPackage parse() throws ParseException {
      String group = null;
      String name = null;
      String version = null;
      String[] optionItems = option.split(":");
      switch(optionItems.length) {
        case 2:
          group = optionItems[0];
          name = optionItems[1];
          break;
        case 1:
          name = optionItems[0];
          break;
        default:
          throw new ParseException("Too many `:` in string", 0);
      }
      optionItems = name.split("@");
      switch(optionItems.length) {
        case 2:
          name = optionItems[0];
          version = optionItems[1];
          break;
        case 1:
          name = optionItems[0];
          break;
        default:
          throw new ParseException("Too many `@` in string", 0);
      }
      return new RepositoryPackage(
        group,
        name,
        version,
        null,
        null,
        null);
    }
  }
};
