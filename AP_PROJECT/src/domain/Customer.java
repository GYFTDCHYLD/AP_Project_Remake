package domain;

import java.io.Serializable;

public class Customer extends User  implements Serializable{
	
	private BillingAccount BillingAccount;

	
	public Customer() {
		super();
	}
	
	public Customer(String userId, String nameTitle, String firstName, String lastName, String password) {
		super(userId, nameTitle, firstName, lastName, password);
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
