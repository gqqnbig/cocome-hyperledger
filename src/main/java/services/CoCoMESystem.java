package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;
import org.hyperledger.fabric.contract.Context;


public interface CoCoMESystem {

	/* all system operations of the use case*/
	boolean openCashDesk(final Context ctx, int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean closeCashDesk(final Context ctx, int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean openStore(final Context ctx, int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean closeStore(final Context ctx, int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean changePrice(final Context ctx, int barcode, float newPrice) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean receiveOrderedProduct(final Context ctx, int orderID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Supplier> listSuppliers(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Item> showStockReports(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	CashDesk getCurrentCashDesk();
	void setCurrentCashDesk(CashDesk currentcashdesk);
	Store getCurrentStore();
	void setCurrentStore(Store currentstore);
	
	
	/* invariant checking function */
}
