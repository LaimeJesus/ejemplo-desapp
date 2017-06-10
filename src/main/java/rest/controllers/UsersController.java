package rest.controllers;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import com.google.gson.JsonSyntaxException;

import exceptions.InvalidSelectedProduct;
import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListNotExistException;
import exceptions.ProductNotExistException;
import exceptions.PurchaseRecordNotExistException;
import exceptions.SelectedProductNotExistException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import model.products.ProductList;
import model.users.User;
import rest.dtos.generics.DurationDTO;
import rest.dtos.generics.ResponseDTO;
import rest.dtos.productlists.PurchaseRecordDTO;
import rest.dtos.productlists.SelectedProductDTO;
import rest.dtos.users.ProfileDTO;
import rest.dtos.users.UserDTO;
import services.general.GeneralService;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/users")
public class UsersController {
	
	private GeneralService generalService;
	private ResponseDTO responseDTO;

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
	@PostConstruct
	public void init(){
		responseDTO = new ResponseDTO();
	}
	
	// DEFAULT USERS ACTIVITY

	@GET
	@Path("/")
	@Produces("application/json")
	public Response users(){
		return responseDTO.ok(UserDTO.createUsers(getGeneralService().getUsers()));
	}
	
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createUser(String userJson){
		try {
			User u = responseDTO.gson.fromJson(userJson, User.class);
			generalService.createUser(u);
			return responseDTO.ok(generalService.getUserService().findByUsername(u.getUsername()));
		} catch (JsonSyntaxException | UserAlreadyExistsException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}		
	}
	@DELETE
	@Path("/")
	public Response deleteUsers(){
		generalService.getUserService().deleteAll();
		return responseDTO.ok("users deleted");
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	//ALL METHOD INVOLVING USERS only
	@GET
	@Path("/{userId}")
	@Produces("application/json")
	public Response getById(@PathParam("userId") Integer userId){
		try {
			return responseDTO.ok(new UserDTO(generalService.getUserById(userId)));
		} catch (UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}

	@PUT
	@Path("/{userId}")
	@Consumes("application/json")
	public Response createOrUpdateUser(@PathParam("userId") Integer userId, String userJson){
		try {
			User user = responseDTO.gson.fromJson(userJson, User.class);
			generalService.createOrUpdateUser(userId, user);
			return responseDTO.ok("user updated or created");
		} catch (JsonSyntaxException | UserAlreadyExistsException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}	
	
	@DELETE
	@Path("/{userId}")
	public Response deleteUser(@PathParam("userId") Integer userId){
		try {
			generalService.deleteUser(userId);
			return responseDTO.ok("deleted user");
		} catch (UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	/////////////////////////////////////////////////////////////////////////////////
	
	//PROFILE METHODS OF AN USER
	@GET
	@Path("/{userId}/profile")
	@Produces("application/json")
	public Response getProfileById(@PathParam("userId") Integer userId){
		try {
			return responseDTO.ok(new ProfileDTO(generalService.getProfile(userId)));
		} catch (UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}

	//END POINTS NO FUNCIONA, tengo q revisarlo
//	@PUT
//	@Path("/{id}/profile")
//	public Response createOrUpdateProfile(@PathParam("id") Integer id, ProfileDTO profile){
//		User u = generalService.getUserService().findById(id);
//		u.getProfile().setAddress(new Address(profile.address));
//		generalService.getUserService().update(u);
//		return Response.ok().build();
//	}

	/////////////////////////////////////////////////////////////////////////////////
	//PURCHASE METHODS OF AN USER
	@GET
	@Path("/{userId}/purchases")
	@Produces("application/json")
	public Response getPurchases(@PathParam("userId") Integer userId){
		try {
			return responseDTO.ok(PurchaseRecordDTO.createPurchaseRecords(generalService.getPurchaseRecords(userId)));
		} catch (UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}

	@GET
	@Path("{userId}/purchases/{purchaseId}")
	@Produces("application/json")
	public Response getPurchaseById(@PathParam("userId") Integer userId, @PathParam("purchaseId") Integer purchaseId){
		try {
			return responseDTO.ok(new PurchaseRecordDTO(generalService.getPurchaseRecord(userId, purchaseId)));
		} catch (PurchaseRecordNotExistException | UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return responseDTO.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@DELETE
	@Path("{userId}/purchases/{purchaseId}")
	public Response deletePurchaseById(@PathParam("userId") Integer userId, @PathParam("purchaseId") Integer purchaseId){
		try {
			generalService.deletePurchaseRecord(userId, purchaseId);
			return responseDTO.ok("purchase deleted");
		} catch (UserDoesNotExistException | UserIsNotLoggedException | PurchaseRecordNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	//ADDRESS METHODS OF AN USER
	@GET
	@Path("/{userId}/address")
	@Produces("application/json")
	public Response getAddress(@PathParam("userId") Integer userId){
		try {
			return responseDTO.ok(generalService.getUserById(userId).getProfile().getAddress());
		} catch (UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// PRODUCT LISTS METHODS OF AN USER
	@GET
	@Path("/{userId}/productlists")
	@Produces("application/json")
	public Response getProductLists(@PathParam("userId") Integer userId, @QueryParam("sortedBy") List<String> sorts){
		try {
			return responseDTO.ok(generalService.getProductLists(userId));
		} catch (UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	@GET
	@Path("/{userId}/productlists/{productlistId}")
	@Produces("application/json")
	public Response getProductListById(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId){
		try {
			return responseDTO.ok(generalService.getProductListById(userId, productlistId));
		} catch (UserDoesNotExistException | ProductListNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	@POST
	@Path("/{userId}/productlists/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createProductList(@PathParam("userId") Integer userId, String productListJson){
		try {
			ProductList pl = responseDTO.gson.fromJson(productListJson, ProductList.class);
			generalService.createProductList(userId, pl);
			pl = generalService.getUserById(userId).getProductListByName(pl.getName());
			return responseDTO.ok(pl.updateTotalAmount());
		} catch (UserDoesNotExistException | UserIsNotLoggedException | ProductListNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	@DELETE
	@Path("/{userId}/productlists/{productlistId}")
	public Response deleteProductListById(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId){
		try {
			generalService.deleteProductList(userId, productlistId);
			return responseDTO.ok("product list deleted");
		} catch (UserDoesNotExistException | ProductListNotExistException | UserIsNotLoggedException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	/////////////////////////////////////////////////////////////////////////////////
	//SHOP
	@GET
	@Path("/{userId}/productlists/{productlistId}/waitingtime")
	public Response getWaitingTimeOfAList(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId){
		try {
			return responseDTO.ok(new DurationDTO(generalService.getWaitingTime(userId, productlistId)));
		} catch (UserDoesNotExistException | ProductListNotExistException | UserIsNotLoggedException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return responseDTO.error(Status.INTERNAL_SERVER_ERROR, "problem in server");
		}
	}
	
	@GET
	@Path("/{userId}/productlists/{productlistId}/ready")
	public Response ready(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId){
		try {
			return responseDTO.ok(new DurationDTO(generalService.ready(userId, productlistId)));
		} catch (UserDoesNotExistException | UserIsNotLoggedException | ProductListNotExistException
				| InvalidSelectedProduct e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return responseDTO.error(Status.INTERNAL_SERVER_ERROR, "problem in server");
		}
	}
	
	//no tiene validacion de si estaba primero en la caja ni tampoco si la eligio para comprar, simplemente la compra
	@POST
	@Path("/{userId}/productlists/{productlistId}/shop")
	public Response shop(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId){
		try {
			generalService.shop(userId,productlistId);
			return responseDTO.ok("ya compre");
		} catch (UserDoesNotExistException | UserIsNotLoggedException | ProductListNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return responseDTO.error(Status.INTERNAL_SERVER_ERROR, "problem in server");
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	//SELECT PRODUCT METHODS OF A LIST OF AN USER
	@GET
	@Path("/{userId}/productlists/{productlistId}/selectedproducts")
	@Produces("application/json")
	public Response getSelectedProducts(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId){
		try {
			return responseDTO.ok(generalService.getProductListById(userId, productlistId).getAllProducts());
		} catch (ProductListNotExistException | UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	@GET
	@Path("/{userId}/productlists/{productlistId}/selectedproducts/{selectedproductId}")
	@Produces("application/json")
	public Response getSelectedProduct(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId, @PathParam("selectedproductId") Integer selectedproductId){
		try {
			return responseDTO.ok(generalService.getProductListById(userId, productlistId).getSelectedProduct(selectedproductId));
		} catch (ProductListNotExistException | UserDoesNotExistException | SelectedProductNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	@POST
	@Path("/{userId}/productlists/{productlistId}/selectedproducts")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createSelectedProduct(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId, String selectedProductJson){
		try {
			SelectedProductDTO selectedProduct = responseDTO.gson.fromJson(selectedProductJson, SelectedProductDTO.class);
			generalService.createSelectedProduct(userId, productlistId, selectedProduct.productId, selectedProduct.quantity);
			return responseDTO.ok("product selected");
		} catch (UserDoesNotExistException | UserIsNotLoggedException | ProductListNotExistException
				| ProductNotExistException | ProductIsAlreadySelectedException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		} catch(JsonSyntaxException e){
			return responseDTO.error(Status.CONFLICT, "json error");
		}
		catch(Exception e){
			return responseDTO.error(Status.INTERNAL_SERVER_ERROR, "can not take that request");
		}
	}
	@DELETE
	@Path("/{userId}/productlists/{productlistId}/selectedproducts/{selectedproductId}")
	public Response deleteSelectedProduct(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId, @PathParam("selectedproductId") Integer selectedProductId){
		try {
			generalService.deleteSelectedProduct(userId,productlistId, selectedProductId);
			return responseDTO.ok("selectedproduct deleted");
		} catch (UserDoesNotExistException | ProductListNotExistException | SelectedProductNotExistException
				| ProductDoesNotExistOnListException | ProductIsAlreadySelectedException
				| MoneyCannotSubstractException | UserIsNotLoggedException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return responseDTO.error(Status.INTERNAL_SERVER_ERROR, "can not take that request");
		}
	}
	@PUT
	@Path("/{userId}/productlists/{productlistId}/selectedproducts/{selectedproductId}")
	public Response updateSelectedProduct(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId, @PathParam("selectedproductId") Integer selectedProductId, String selectedProductJson){
		try {
			generalService.updateSelectedProduct(userId, productlistId, selectedProductId, responseDTO.gson.fromJson(selectedProductJson,  SelectedProductDTO.class));
			return responseDTO.ok("selected product updated");
		} catch (JsonSyntaxException | UserDoesNotExistException | UserIsNotLoggedException
				| ProductListNotExistException | ProductNotExistException | ProductIsAlreadySelectedException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return responseDTO.error(Status.INTERNAL_SERVER_ERROR, "can not take that request");
		}
	}
	/////////////////////////////////////////////////////////////////////////////////
	//OFFERS APPLIED OF A LIST OF AN USER METHODS
	@GET
	@Path("/{userId}/productlists/{productlistId}/offers")
	@Produces("application/json")
	public Response getOffers(@PathParam("userId") Integer userId, @PathParam("productlistId") Integer productlistId){
		try {
			return responseDTO.ok(generalService.getProductListById(userId, productlistId).getAppliedOffers());
		} catch (ProductListNotExistException | UserDoesNotExistException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}	
	
//	AUTH VALIDATIONS 
	@POST
	@Path("/signup")
	@Consumes("application/json")
	@Produces("application/json")
	public Response create(String userJson){
		try {
			generalService.createUser(responseDTO.gson.fromJson(userJson, UserDTO.class).signUpUser());
			return responseDTO.ok("user created");
		} catch (UserAlreadyExistsException e) {
			return responseDTO.error(Status.FORBIDDEN, e.getMessage());
		} catch (JsonSyntaxException j){
			return responseDTO.error(Status.BAD_REQUEST, "user created incorrectly");
		}
	}

	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(String userDTO){
		try {
			User u = responseDTO.gson.fromJson(userDTO, UserDTO.class).fullUser();
			generalService.loginUser(u);
			return responseDTO.ok(new UserDTO(generalService.getUserService().findByUsername(u.getUsername())));
		} catch (UsernameDoesNotExistException | UsernameOrPasswordInvalidException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	
	//users/loginwithmail
	@POST
	@Path("/loginwithmail")
	@Consumes("application/json")
	@Produces("application/json")
	public Response loginwithmail(String userJson){
		try {
			User u = responseDTO.gson.fromJson(userJson, User.class);
			generalService.loginWithMailUser(u);
			return responseDTO.ok(new UserDTO(generalService.getUserService().findByUsername(u.getUsername())));
		} catch (UsernameDoesNotExistException | UsernameOrPasswordInvalidException | UserAlreadyExistsException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}
	}
	
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(UserDTO user){
		try {
			generalService.logoutUser(user.toUser());
			return responseDTO.ok("user logged out");
		} catch (UsernameDoesNotExistException | UserIsNotLoggedException e) {
			return responseDTO.error(Status.CONFLICT, e.getMessage());
		}		
	}

}
