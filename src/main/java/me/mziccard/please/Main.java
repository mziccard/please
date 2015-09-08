package me.mziccard.please;

import java.lang.IllegalStateException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import me.mziccard.please.rpc.RepositoryServiceFactory;
import me.mziccard.please.rpc.RepositoryService;
import me.mziccard.please.rpc.RepositoryOptions;
import me.mziccard.please.entities.RepositoryPackage;

import me.mziccard.please.actions.ActionOptions;
import me.mziccard.please.actions.ActionOptions.Repository;
import me.mziccard.please.actions.SearchAction;
import me.mziccard.please.actions.AddAction;
import me.mziccard.please.actions.Action;

public class Main {

  private static void printAddUsage() {
    System.out.println();
    System.out.println(
      "please add <groupId>:<artifactId>" +
      "\t\tAdd the latest version of the artifact");
    System.out.println(
      "please add <groupId>:<artifactId>@<version>" + 
      "\tAdd the artifact");
    System.out.println();
    System.out.println("Options:");
    System.out.println();
    System.out.println("--save\t\t Save the artifact as a dependency");
    System.out.println("--save-dev\t Save the artifact as a development dependency");
    System.out.println();
  }

  private static void printSearchUsage() {
    System.out.println();
    System.out.println(
      "please search <artifactId>" +
      "\t\t\tSearch artifacts with matching (or similar) id");
    System.out.println();
  }

  private static void printUsage() {
    System.out.println();
    System.out.println("Usage: please <command>");
    System.out.println();
    System.out.println("Where <command> is one of:");
    System.out.println("\tadd, search, seach");
    System.out.println();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      printUsage();
      System.exit(1);
    }

    Action action = null;
    ActionOptions.Builder builder = new ActionOptions.Builder(Repository.JCENTER);

    if (args[0].equals("add")) {
      if (args.length < 2 || args[1].equals("-h")) {
        printAddUsage();
        System.exit(1);
      }
      
      String item = null;
      try {
        for (int i = 1; i < args.length; i++) {
          String currentArg = args[i];
          if (currentArg.equals("--save")) {
            builder.save();
          } else if (currentArg.equals("--dev")) {
            builder.dev();
          } else if (item == null) {
            item = currentArg;
          } else {
            throw new IllegalStateException();
          }
        }

        RepositoryPackage dep = new RepositoryPackage.Parser(item).parse();
        System.out.println(dep);
        if (dep.getGroup() == null) {
          throw new IllegalStateException();   
        }

        action = new AddAction(
          dep,
          builder.build()
        );
      } catch(IllegalStateException e) {
        printAddUsage();
        System.exit(1);
      }
      
      // if --save or --save-dev try to save the library 
      // otherwise print the maven / sbt / gradle code to
      // add it
      // do add
    } else if (args[0].equals("search") || args[0].equals("seach")) {
      if (args.length < 2 || args[1].equals("-h")) {
        printSearchUsage();
        System.exit(1);
      }
      RepositoryPackage dep = new RepositoryPackage("", args[1], "", null, null, null);
      action = new SearchAction(
        dep,
        builder.build()
      );      

    } else {
      printUsage();
      System.exit(1);
    }
    System.out.println(args[0]);
    action.apply();

/*    
    RepositoryService service = RepositoryServiceFactory.instance().get(
      new RepositoryOptions("bintray", "jcenter"));
    List<RepositoryPackage> packages = 
      service.list();
    for (RepositoryPackage rp : packages) {
      System.out.println(rp.getName() + rp.getVersion() + rp.getCreatedAt());
    }
    RepositoryPackage dependency = new RepositoryPackage(
              "com.google.code.gson",
              "gson",
              "2.3", null, null, null);
    if (service.exists(dependency)) {
      System.out.println("PACKAGE GSON EXISTS");
    } else {
      System.out.println("PACKAGE GSON DOES NOT EXIST");
    }
*/
  }
};
