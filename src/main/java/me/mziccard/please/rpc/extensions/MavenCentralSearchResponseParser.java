package me.mziccard.please.rpc.extensions;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import me.mziccard.please.entities.RepositoryPackage;
import me.mziccard.please.entities.MavenCentralPackage;

public class MavenCentralSearchResponseParser implements SearchResponseParser {

  @Override
  public List<RepositoryPackage> parse(String response) throws ParseException {
    try {
      List<RepositoryPackage> packages = new ArrayList<RepositoryPackage>();
      Gson gson = new GsonBuilder().create();
      JsonParser parser = new JsonParser();
      JsonObject element = (JsonObject) parser.parse(response);
      JsonElement responseWrapper = 
        element.get("response").getAsJsonObject().get("docs");

      MavenCentralPackage[] mavenPackages = 
        gson.fromJson(responseWrapper, MavenCentralPackage[].class);

      for (int i = 0; i < mavenPackages.length; i++) {
        MavenCentralPackage mavenPackage = mavenPackages[i];
        packages.add(new RepositoryPackage(
          mavenPackage.getG(),
          mavenPackage.getA(),
          mavenPackage.getLatestVersion(),
          mavenPackage.getRepositoryId(),
          "MavenCentral",
          new Date(mavenPackage.getTimestamp())));
      }
      return packages;
    } catch (JsonParseException e) {
      ParseException exception = new ParseException(
        "Error parsing Maven Central search API response",
        0);
      exception.setStackTrace(e.getStackTrace());
      throw exception;
    }
  }
}