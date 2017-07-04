package rest.controllers;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import exceptions.NoRecommendationForProductException;
import exceptions.ProductNotExistException;
import rest.dtos.generics.ResponseDTO;
import rest.dtos.products.ProductDTO;
import services.general.GeneralService;

@Path("/recommendations")
public class RecommendationsController {

	private GeneralService generalService;
	private ResponseDTO response;

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	@PostConstruct
	public void init(){
		response = new ResponseDTO();
	}
	
	@PUT
	@Path("/initialize")
	@Produces("application/json")
	public Response initialize(){
		generalService.initializeRecommender();
		return response.ok("recommender initialized");
	}
	
	@GET
	@Path("/{productId}")
	@Produces("application/json")
	public Response getRecommendationForProduct(@PathParam("productId") Integer productId){
		try {
			return response.ok(ProductDTO.createProducts(generalService.getRecommendation(productId, 2)));
		} catch (ProductNotExistException e) {
			return response.error(Status.FORBIDDEN, "product can not get recommenadtion");
		} catch (NoRecommendationForProductException e) {
      return response.error(Status.FORBIDDEN, e.getMessage());
    }	catch (Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server problem");
		}
	}
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getRecommendations(){
		try {
			return response.ok(generalService.getRecommendations());
		} catch (Exception e) {
			return response.error(Status.INTERNAL_SERVER_ERROR, "server problem");
		}
	}
}
