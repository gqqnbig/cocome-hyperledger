package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;
import org.hyperledger.fabric.contract.Context;


public interface ThirdPartyServices {

	/* all system operations of the use case*/
	boolean thirdPartyCardPaymentService(final Context ctx, String cardAccountNumber, LocalDate expiryDate, float fee) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	CashDesk getCurrentCashDesk();
	void setCurrentCashDesk(CashDesk currentcashdesk);
	Store getCurrentStore();
	void setCurrentStore(Store currentstore);
	
	/* invariant checking function */
}
