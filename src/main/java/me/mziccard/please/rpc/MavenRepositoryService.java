package me.mziccard.please.rpc;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Date;
import java.lang.UnsupportedOperationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.GsonBuilder;

import org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Reader;
import org.apache.maven.artifact.repository.metadata.Metadata;

import me.mziccard.please.entities.MavenCentralPackage;
import me.mziccard.please.entities.RepositoryPackage;
import me.mziccard.please.common.ResourceNotFoundException;

public class MavenRepositoryService implements RepositoryService {

  private RepositoryOptions serviceOptions;

  public MavenRepositoryService(RepositoryOptions options) {
    // Do something with options
    this.serviceOptions = options;
    if (serviceOptions.getSearchExtension() != null) {
      serviceOptions.getSearchExtension().setRepositoryService(this);
    }
  }

  @Override
  public RepositoryOptions getRepositoryOptions() {
    return this.serviceOptions;
  }

  @Override
  public boolean exists(RepositoryPackage item) throws Exception {
    String url = serviceOptions.getMavenUrl(item);
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      HttpHead httphead = new HttpHead(url);  

      System.out.println("Executing request " + httphead.getRequestLine());

      // Create a custom response handler
      ResponseHandler<Boolean> responseHandler = new ResponseHandler<Boolean>() {

        @Override
        public Boolean handleResponse(final HttpResponse response) 
          throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
              return true;
            } else {
              return false;
            }
        }
      };
            
      return httpclient.execute(httphead, responseHandler);
    } catch (ClientProtocolException e) {
      ResourceNotFoundException notFound = new ResourceNotFoundException(e.getMessage());
      notFound.setStackTrace(e.getStackTrace());
      throw notFound;
    } finally {
      httpclient.close();
    }
  }

  @Override
  public String latestVersion(RepositoryPackage item) throws Exception {
    String url = serviceOptions.getMavenUrl(item);
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      HttpGet httpget = new HttpGet(url);  

      System.out.println("Executing request " + httpget.getRequestLine());

      // Create a custom response handler
      ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

        @Override
        public String handleResponse(final HttpResponse response) 
          throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
              HttpEntity entity = response.getEntity();
              if (entity == null) {
                throw new ClientProtocolException("Received empty HTTP response");
              }
              try {
                Metadata metadata = new MetadataXpp3Reader().read(entity.getContent());
                return metadata.getVersioning().getLatest();
              } catch (Exception e) {
                throw new ClientProtocolException("Failed to parse received metadata");
              }
            } else {
              throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
      };
            
      return httpclient.execute(httpget, responseHandler);
    } catch (ClientProtocolException e) {
      ResourceNotFoundException notFound = new ResourceNotFoundException(e.getMessage());
      notFound.setStackTrace(e.getStackTrace());
      throw notFound;
    } finally {
      httpclient.close();
    }
  }
  
  @Override
  public List<RepositoryPackage> search(SearchOptions options) throws Exception {
    if (serviceOptions.getSearchExtension() == null) {
      throw new UnsupportedOperationException(
        "Search extension is not set for this repository");
    }
    return serviceOptions.getSearchExtension().search(options);
  }
}