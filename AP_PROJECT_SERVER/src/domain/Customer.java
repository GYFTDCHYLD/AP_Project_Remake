package domain;

import java.io.Serializable;
import java.util.List;

public class Customer extends User  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Billing> BillingAccount;

	public Customer() {
		super();
	}
	
	public Customer(String userId, String nameTitle, String firstName, String lastName, long phoneNumber, String email, String password){
		super(userId, nameTitle, firstName, lastName, phoneNumber, email, password);
	}

	public Customer(List<domain.Billing> billingAccount) {
		super();
		BillingAccount = billingAccount;
	}

	public List<Billing> getBillingAccount() {
		return BillingAccount;
	}

	public void setBillingAccount(List<Billing> billingAccount) {
		BillingAccount = billingAccount;
	}
}