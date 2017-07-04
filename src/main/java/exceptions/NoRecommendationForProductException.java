package exceptions;

public class NoRecommendationForProductException extends Exception {

  public NoRecommendationForProductException(){
    super("No recommendation for product");
  }
  
  /**
   * 
   */
  private static final long serialVersionUID = 1288363510666540833L;

}
