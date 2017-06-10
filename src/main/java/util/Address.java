package util;

public class Address extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8462158664424317226L;
	public String address;

	public Address(){
		
	}
	
	public Address(String anAddress) {
		this.address = anAddress;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}

	@Override
	public String toString() {
		return address;
	}

}
