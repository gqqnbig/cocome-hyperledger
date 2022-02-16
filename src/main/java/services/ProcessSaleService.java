package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;
import org.hyperledger.fabric.contract.Context;


public interface ProcessSaleService {

	/* all system operations of the use case*/
	boolean makeNewSale(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean enterItem(final Context ctx, int barcode, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	float endSale(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean makeCashPayment(final Context ctx, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean makeCardPayment(final Context ctx, String cardAccountNumber, LocalDate expiryDate, float fee) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	SalesLineItem getCurrentSaleLine();
	void setCurrentSaleLine(SalesLineItem currentsaleline);
	Sale getCurrentSale();
	void setCurrentSale(Sale currentsale);
	PaymentMethod getCurrentPaymentMethod();
	void setCurrentPaymentMethod(PaymentMethod currentpaymentmethod);
	
	/* all get and set functions for temp property*/
	CashDesk getCurrentCashDesk();
	void setCurrentCashDesk(CashDesk currentcashdesk);
	Store getCurrentStore();
	void setCurrentStore(Store currentstore);
	
	/* invariant checking function */
}
