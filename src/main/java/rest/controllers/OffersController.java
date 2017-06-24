package rest.controllers;

import java.util.ArrayList;
import java.util.List;
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

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import com.google.gson.JsonSyntaxException;

import exceptions.CategoryOfferNotExistException;
import exceptions.CombinationOfferNotExistException;
import exceptions.CrossingOfferNotExistException;
import exceptions.ProductNotExistException;
import rest.dtos.CategoryDTO;
import rest.dtos.generics.ResponseDTO;
import rest.dtos.offers.CategoryOfferDTO;
import rest.dtos.offers.CombinationOfferDTO;
import rest.dtos.offers.CrossingOfferDTO;
import rest.dtos.offers.OfferDTO;
import services.general.GeneralService;
import util.Category;

@CrossOriginResourceSharing(allowAllOrigins = true)
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
		List<OfferDTO> offers = new ArrayList<OfferDTO>();
		try{
			offers.addAll(CategoryOfferDTO.createCategoryOffers(generalService.getCategoryOffers()));
			offers.addAll(CombinationOfferDTO.createCombinationOffers(generalService.getCombinationOffers()));
			offers.addAll(CrossingOfferDTO.createCrossingOffers(generalService.getCrossingOffers()));
			return response.ok(offers);			
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "error getting offers");
		}
	}
	@DELETE
	@Path("/")
	public Response deleteOffers(){
		try{
			generalService.deleteOffers();
			return response.ok("deleted offers");
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
	}
	
	//////////////////////////////////////////////////
	@GET
	@Path("/categories")
	@Produces("application/json")
	public Response getAllCategories(){
		try {
			List<CategoryDTO> values = new ArrayList<CategoryDTO>(); 
			for (Category category : Category.values()){
				values.add(new CategoryDTO(category));
			}
			return response.ok(values);
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/category")
	@Produces("application/json")
	public Response getCategoryOffers(){
		try{
			return response.ok(CategoryOfferDTO.createCategoryOffers(generalService.getCategoryOffers()));
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
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
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
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
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
	}
	//////////////////////////////////////////////////
	@GET
	@Path("/combination")
	@Produces("application/json")
	public Response getCombinationOffers(){
		try {
		return response.ok(CombinationOfferDTO.createCombinationOffers(generalService.getCombinationOffers()));
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
	}
	
	@POST
	@Path("/combination")
	@Consumes("application/json")
	public Response createCombinationOffer(String combinationOfferJson){		
		try { 
			generalService.createCombinationOffer(response.gson.fromJson(combinationOfferJson, CombinationOfferDTO.class));
			return response.ok("created");
		} catch (JsonSyntaxException | ProductNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
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
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
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
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
	}
	//////////////////////////////////////////////////
	@GET
	@Path("/crossing")
	@Produces("application/json")
	public Response getCrossingOffers(){
		try{
			return response.ok(CrossingOfferDTO.createCrossingOffers(generalService.getCrossingOffers()));			
		}catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "problem in server");
		}
	}

	@POST
	@Path("/crossing")
	@Consumes("application/json")
	public Response createCrossingOffer(String crossingOfferJson){
		try {
			generalService.createCrossingOffer(response.gson.fromJson(crossingOfferJson, CrossingOfferDTO.class));
			return response.ok("created");
		} catch (JsonSyntaxException | ProductNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "can not create crossing offer");
		}
	}
	
	@GET
	@Path("/crossing/{crossingOfferId}")
	@Produces("application/json")
	public Response getCrossingOffer(@PathParam("crossingOfferId") Integer crossingOfferId){
		try {
			return response.ok(new CrossingOfferDTO(generalService.getCrossingOfferById(crossingOfferId)));
		} catch (CrossingOfferNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "error in server");
		}
	}
	
	@DELETE
	@Path("/crossing/{crossingOfferId}")
	public Response deleteCrossingOffer(@PathParam("crossingOfferId") Integer crossingOfferId){
		try {
			generalService.deleteCrossingOffer(crossingOfferId);
			return response.ok("crossing offer deleted");
		} catch (CrossingOfferNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "can not delete crossing offer");
		}
	}
}
