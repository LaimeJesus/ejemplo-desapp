package rest.controllers;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import rest.dtos.generics.ResponseDTO;
import rest.dtos.registers.RegisterDTO;
import services.general.GeneralService;

@Path("/registers")
public class RegistersController {

	private GeneralService generalService;
	private ResponseDTO response;
	
	@PostConstruct
	public void init(){
		response = new ResponseDTO();
	}
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getRegisters(){
		return response.ok(RegisterDTO.create(generalService.getShopService().getCashRegisterManager().getRegisters()));
	}

	@PUT
	@Path("/{registerId}/close")
	@Produces("application/json")
	public Response closeRegister(@PathParam("registerId") Integer registerId){
		generalService.getShopService().getCashRegisterManager().closeCashRegister(registerId);
		return response.ok(new RegisterDTO(generalService.getShopService().getCashRegisterManager().getRegisters().get(registerId)));
	}
	@PUT
	@Path("/{registerId}/open")
	@Produces("application/json")
	public Response openRegister(@PathParam("registerId") Integer registerId){
		generalService.getShopService().getCashRegisterManager().openCashRegister(registerId);
		return response.ok(new RegisterDTO(generalService.getShopService().getCashRegisterManager().getRegisters().get(registerId)));
	}
	@GET
	@Path("/{registerId}")
	@Produces("application/json")
	public Response getRegister(@PathParam("registerId") Integer registerId){
		return response.ok(new RegisterDTO(generalService.getShopService().getCashRegisterManager().getRegisters().get(registerId)));
	}

	public GeneralService getGeneralService() {
		return generalService;
	}


	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
}
