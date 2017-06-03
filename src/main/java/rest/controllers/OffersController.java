package rest.controllers;

import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonSyntaxException;

import exceptions.CategoryOfferNotExistException;
import exceptions.CombinationOfferNotExistException;
import exceptions.ProductNotExistException;
import model.products.Product;
import rest.dtos.ResponseDTO;
import rest.dtos.offers.CategoryOfferDTO;
import rest.dtos.offers.CombinationOfferDTO;
import services.general.GeneralService;

@Path("/offers")
public class OffersController {

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

	//////////////////////////////////////////////////
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getOffers(){
		return response.ok(generalService.getOffers());
	}
	@DELETE
	@Path("/")
	public Response deleteOffers(){
		generalService.deleteOffers();
		return response.ok("deleted offers");
	}
	
	//////////////////////////////////////////////////
	@GET
	@Path("/category")
	@Produces("application/json")
	public Response getCategoryOffers(){
		return response.ok(generalService.getCategoryOffers().stream().map(x -> new CategoryOfferDTO(x)).collect(Collectors.toList()));
	}
	@POST
	@Path("/category")
	@Consumes("application/json")
	public Response createCategoryOffer(String categoryOfferJson){
		try{
			generalService.createCategoryOffer(response.gson.fromJson(categoryOfferJson, CategoryOfferDTO.class).toCategoryOffer());			
			return response.ok("created");
		}catch(JsonSyntaxException e){
			return response.error(Status.CONFLICT, e.getMessage());
		}
	}
	@GET
	@Path("/category/{categoryOfferId}")
	@Produces("application/json")
	public Response getCategoryOffer(@PathParam("categoryOfferId") Integer categoryOfferId){
		try {
			return response.ok(new CategoryOfferDTO(generalService.getCategoryOfferById(categoryOfferId)));
		} catch (CategoryOfferNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@DELETE
	@Path("/category/{categoryOfferId}")
	public Response deleteCategoryOffer(@PathParam("categoryOfferId") Integer categoryOfferId){
		try {
			generalService.deleteCategoryOffer(categoryOfferId);
			return response.ok("deleted category offer");			
		} catch (CategoryOfferNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		}
	}
	//////////////////////////////////////////////////
	@GET
	@Path("/combination")
	@Produces("application/json")
	public Response getCombinationOffers(){
		return response.ok(generalService.getCombinationOffers().stream().map(x -> new CombinationOfferDTO(x)).collect(Collectors.toList()));
	}
	
	@POST
	@Path("/combination")
	@Consumes("application/json")
	public Response createCombinationOffer(String combinationOfferJson){		
		try {
			CombinationOfferDTO co = response.gson.fromJson(combinationOfferJson, CombinationOfferDTO.class); 
			Product p1 = generalService.getProductById(co.relatedProductId);
			Product p2 = generalService.getProductById(co.combinatedProductId);
			generalService.createCombinationOffer(co.toCombinationOffer(p1, p2));
			return response.ok("created");
		} catch (JsonSyntaxException | ProductNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		}
	}
	@GET
	@Path("/combination/{combinationOfferId}")
	@Produces("application/json")
	public Response getCombinationOffer(@PathParam("combinationOfferId") Integer combinationOfferId){
		try {
			return response.ok(new CombinationOfferDTO(generalService.getCombinationOfferById(combinationOfferId)));
		} catch (CombinationOfferNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		}
	}
	@DELETE
	@Path("/combination/{combinationOfferId}")
	public Response deleteCombinationOffer(@PathParam("combinationOfferId") Integer combinationOfferId){
		try {
			generalService.deleteCombinationOffer(combinationOfferId);
			return response.ok("deleted category offer");			
		} catch (CombinationOfferNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		}
	}
}
