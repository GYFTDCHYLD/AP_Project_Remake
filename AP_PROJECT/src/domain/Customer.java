package domain;

import java.io.Serializable;

public class Customer extends User  implements Serializable{
	
	private BillingAccount BillingAccount;

	
	public Customer() {
		super();
	}
	
	public Customer(BillingAccount billingAccount) {
		super();
		BillingAccount = billingAccount;
	}


	public BillingAccount getBillingAccount() {
		return BillingAccount;
	}
	
}
