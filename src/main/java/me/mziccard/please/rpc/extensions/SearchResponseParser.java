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
import me.mziccard.please.entities.MavenCentralPackage;
import me.mziccard.please.rpc.adapters.DistinctPackageListAdapter;

/**
 * Interface for a repository search API response parser. Exhibits a method
 * that given a {@code String} parses it to get a list of 
 * {@code RepositoryPackage} objects.
 */
public interface SearchResponseParser {

/**
 * Parse a string to get a list of packages. If the parsing fails an exception
 * is thrown.
 *
 * @param response  The string from which to extract 
 *                  {@code RepositoryPackage} objects according to the logic
 *                  implemented in this method
 * @return          A list of repository packages
 */
  List<RepositoryPackage> parse(String response) throws ParseException;

}