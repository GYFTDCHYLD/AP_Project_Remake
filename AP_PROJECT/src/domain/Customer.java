package domain;

import java.io.Serializable;

public class Customer extends User  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private BillingAccount BillingAccount;

	public Customer() {
		super();
	}
	
	public Customer(String userId, String nameTitle, String firstName, String lastName, long phoneNumber, String email, String password, BillingAccount Billing){
		super(userId, nameTitle, firstName, lastName, phoneNumber, email, password);
		this.BillingAccount = Billing;
	}
	
	public Customer(BillingAccount billingAccount) {
		super();
		BillingAccount = billingAccount;
	}


	public BillingAccount getBillingAccount() {
		return BillingAccount;
	}

	public void setBillingAccount(BillingAccount billingAccount) {
		BillingAccount = billingAccount;
	}
}