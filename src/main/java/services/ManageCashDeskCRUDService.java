package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;
import org.hyperledger.fabric.contract.Context;


public interface ManageCashDeskCRUDService {

	/* all system operations of the use case*/
	boolean createCashDesk(final Context ctx, int id, String name, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	CashDesk queryCashDesk(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyCashDesk(final Context ctx, int id, String name, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteCashDesk(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	CashDesk getCurrentCashDesk();
	void setCurrentCashDesk(CashDesk currentcashdesk);
	Store getCurrentStore();
	void setCurrentStore(Store currentstore);
	
	/* invariant checking function */
}
