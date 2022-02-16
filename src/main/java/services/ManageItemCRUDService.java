package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;
import org.hyperledger.fabric.contract.Context;


public interface ManageItemCRUDService {

	/* all system operations of the use case*/
	boolean createItem(final Context ctx, int barcode, String name, float price, int stocknumber, float orderprice) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Item queryItem(final Context ctx, int barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyItem(final Context ctx, int barcode, String name, float price, int stocknumber, float orderprice) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteItem(final Context ctx, int barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	CashDesk getCurrentCashDesk();
	void setCurrentCashDesk(CashDesk currentcashdesk);
	Store getCurrentStore();
	void setCurrentStore(Store currentstore);
	
	/* invariant checking function */
}
