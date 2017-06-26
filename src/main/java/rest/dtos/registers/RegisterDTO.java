package rest.dtos.registers;

import java.util.List;
import java.util.stream.Collectors;

import model.products.ProductList;
import model.registers.CashRegister;
import rest.dtos.generics.DurationDTO;

public class RegisterDTO {

	public Integer id;
	public int size;
	public DurationDTO waiting_time;
	public boolean isClosed;

	public RegisterDTO(CashRegister x) {
		id = x.id;
		size = x.size();
		waiting_time = new DurationDTO(x.getWaitingTime());
		isClosed = !x.filter.accepts(new ProductList());
	}

	public static List<RegisterDTO> create(List<CashRegister> registers) {
		return registers.stream().map(x -> new RegisterDTO(x)).collect(Collectors.toList());
	}

}
