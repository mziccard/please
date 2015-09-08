package me.mziccard.please.rpc.extensions;

import java.util.List;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import me.mziccard.please.entities.RepositoryPackage;
import me.mziccard.please.rpc.RepositoryService;
import me.mziccard.please.rpc.RepositoryOptions;
import me.mziccard.please.rpc.SearchOptions;
import me.mziccard.please.common.ResourceNotFoundException;

public class JCenterSearchExtension implements SearchExtension {

  private RepositoryService repositoryService;

  /**
   * A {@code SearchUrlBuilder} object for JCenter repository.
   */
  private final SearchUrlBuilder searchUrlBuilder;

  /**
   * A {@code SearchResponseParser} object for JCenter repository.
   */
  private final SearchResponseParser searchResponseParser;

  public JCenterSearchExtension() {
    searchUrlBuilder = new SearchUrlBuilder() {
      @Override
      public String build(
        RepositoryOptions repositoryOptions, 
        SearchOptions searchOptions) {
          return repositoryOptions.getApiBaseUrl() +
            "search/file?name=" +
            searchOptions.getSearchString() +
            "&subject=" +
            repositoryOptions.getUser() +
            "&repo=" +
            repositoryOptions.getRepo();
        }
      };
    searchResponseParser = new JCenterSearchResponseParser();
  }

  @Override
  public void setRepositoryService(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

  @Override
  public List<RepositoryPackage> search(SearchOptions searchOptions) throws Exception {
    String url = 
      searchUrlBuilder.build(
        repositoryService.getRepositoryOptions(), 
        searchOptions);
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
              return EntityUtils.toString(entity);
            } else {
              throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
      };
            
      String responseBody = httpclient.execute(httpget, responseHandler);
      List<RepositoryPackage> packages = searchResponseParser.parse(responseBody);
      for (RepositoryPackage dependency : packages) {
        dependency.setVersion(null);
        dependency.setVersion(repositoryService.latestVersion(dependency));
      }
      return packages;
    } catch (ClientProtocolException e) {
      ResourceNotFoundException notFound = new ResourceNotFoundException(e.getMessage());
      notFound.setStackTrace(e.getStackTrace());
      throw notFound;
    } finally {
      httpclient.close();
    }
  }
}