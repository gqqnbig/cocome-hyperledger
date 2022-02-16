package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;
import org.hyperledger.fabric.contract.Context;


public interface CoCoMEOrderProducts {

	/* all system operations of the use case*/
	boolean makeNewOrder(final Context ctx, int orderid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Item[] listAllOutOfStoreProducts(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean orderItem(final Context ctx, int barcode, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean chooseSupplier(final Context ctx, int supplierID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean placeOrder(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	OrderProduct getCurrentOrderProduct();
	void setCurrentOrderProduct(OrderProduct currentorderproduct);
	
	/* all get and set functions for temp property*/
	CashDesk getCurrentCashDesk();
	void setCurrentCashDesk(CashDesk currentcashdesk);
	Store getCurrentStore();
	void setCurrentStore(Store currentstore);
	
	/* invariant checking function */
}
