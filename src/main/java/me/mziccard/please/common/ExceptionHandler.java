package me.mziccard.please.common;

public abstract class ExceptionHandler {

  private static final ExceptionHandler INSTANCE = 
    new ExceptionHandler() {
      @Override
      public void handle(Exception exception) {
        System.out.println("ERROR: " + exception.getMessage());
        System.exit(0);
      }
    };

  /**
   * Return the exception handler.
   *
   * @return  The {@code RepositoryService} instance
   */
  public static ExceptionHandler instance() {
    return INSTANCE;
  }

  /**
   * Handle an exception according ot an application specific policy.
   *
   * @param exception   Exception to be handled
   */
  public abstract void handle(Exception exception);
}