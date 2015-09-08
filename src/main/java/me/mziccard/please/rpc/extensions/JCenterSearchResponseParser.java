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
import me.mziccard.please.entities.BintrayFile;
import me.mziccard.please.rpc.adapters.DistinctPackageListAdapter;

public class JCenterSearchResponseParser implements SearchResponseParser {

  @Override
  public List<RepositoryPackage> parse(String response) throws ParseException {
    try {
      List<RepositoryPackage> packages = new ArrayList<RepositoryPackage>();
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
      BintrayFile[] files = gson.fromJson(response, BintrayFile[].class);

      for (int i = 0; i < files.length; i++) {
        BintrayFile file = files[i];
        String packageGroup = null;
        String packageName = file.getPackageName();
        String[] packageNameComponents = packageName.split(":");
        if (packageNameComponents.length != 2) {
          //TODO Should never happen throw error?
        } else {
          packageGroup = packageNameComponents[0];
          packageName = packageNameComponents[1];
        }
        packages.add(new RepositoryPackage(
          packageGroup,
          packageName,
          file.getVersion(),
          file.getRepo(),
          file.getOwner(),
          file.getCreated()));
      }
      DistinctPackageListAdapter adapter = new DistinctPackageListAdapter();
      adapter.add(packages);
      return adapter.list();
    } catch (JsonParseException e) {
      ParseException exception = new ParseException(
        "Error parsing JCenter search API response",
        0);
      exception.setStackTrace(e.getStackTrace());
      throw exception;
    }
  }
}