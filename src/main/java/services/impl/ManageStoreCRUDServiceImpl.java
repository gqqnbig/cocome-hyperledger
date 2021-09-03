package services.impl;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;
import services.*;
import entities.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Iterator;
import org.hyperledger.fabric.shim.*;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.contract.*;

@Contract
public class ManageStoreCRUDServiceImpl implements ManageStoreCRUDService, Serializable, ContractInterface {
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageStoreCRUDServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
	private CashDesk currentCashDesk;
	private Store currentStore;

	private final Genson genson = new Genson();
			
	/* all get and set functions for temp property*/
	public CashDesk getCurrentCashDesk() {
		return currentCashDesk;
	}	
	
	public void setCurrentCashDesk(CashDesk currentcashdesk) {
		this.currentCashDesk = currentcashdesk;
	}


	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public boolean getB(final Context ctx)
	{
		return true;
	}

	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean getC(final Context ctx)
	{
		return true;
	}

//	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public Store getCurrentStore() {
		return currentStore;
	}	
	
	public void setCurrentStore(Store currentstore) {
		this.currentStore = currentstore;
	}
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	

	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public Store getStore(final Context ctx) {
		Store s =new Store();
		s.setId(1);
		s.setName("Target");
		s.setAddress("Weyburn");
		return s;
	}

	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public String getAllStores(final Context ctx) {
		List<Store> list=new LinkedList<>();
		Store s =new Store();
		s.setId(1);
		s.setName("Target");
		s.setAddress("Weyburn");
		list.add(s);

		final String response = genson.serialize(list);
		return response;
	}
	
	/* Generate buiness logic according to functional requirement */
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean createStore(final Context ctx, int id, String name, String address, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* Code generated for contract definition */
		//Get store
		Store store = null;
		//no nested iterator --  iterator: any previous:any
		for (Store sto : (List<Store>)EntityManager.getAllInstancesOf(Store.class))
		{
			if (sto.getId() == id)
			{
				store = sto;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(store) == true) 
		{ 
			/* Logic here */
			Store sto = null;
			sto = (Store) EntityManager.createObject("Store");
			sto.setId(id);
			sto.setName(name);
			sto.setAddress(address);
			sto.setIsOpened(isopened);
			EntityManager.addObject("Store", sto);
			
			
			;
			// post-condition checking
			if (!(true && 
			sto.getId() == id
			 && 
			sto.getName() == name
			 && 
			sto.getAddress() == address
			 && 
			sto.getIsOpened() == isopened
			 && 
			StandardOPs.includes(((List<Store>)EntityManager.getAllInstancesOf(Store.class)), sto)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [name, address]
		//all relevant vars : sto
		//all relevant entities : Store
	} 
	 
	static {opINVRelatedEntity.put("createStore", Arrays.asList("Store"));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public Store queryStore(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* Code generated for contract definition */
		//Get store
		Store store = null;
		//no nested iterator --  iterator: any previous:any
		for (Store sto : (List<Store>)EntityManager.getAllInstancesOf(Store.class))
		{
			if (sto.getId() == id)
			{
				store = sto;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(store) == false) 
		{ 
			/* Logic here */
			
			
			;
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			; return store;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean modifyStore(final Context ctx, int id, String name, String address, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* Code generated for contract definition */
		//Get store
		Store store = null;
		//no nested iterator --  iterator: any previous:any
		for (Store sto : (List<Store>)EntityManager.getAllInstancesOf(Store.class))
		{
			if (sto.getId() == id)
			{
				store = sto;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(store) == false) 
		{ 
			/* Logic here */
			store.setId(id);
			store.setName(name);
			store.setAddress(address);
			store.setIsOpened(isopened);
			
			
			;
			// post-condition checking
			if (!(store.getId() == id
			 && 
			store.getName() == name
			 && 
			store.getAddress() == address
			 && 
			store.getIsOpened() == isopened
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [name, address]
		//all relevant vars : store
		//all relevant entities : Store
	} 
	 
	static {opINVRelatedEntity.put("modifyStore", Arrays.asList("Store"));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean deleteStore(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* Code generated for contract definition */
		//Get store
		Store store = null;
		//no nested iterator --  iterator: any previous:any
		for (Store sto : (List<Store>)EntityManager.getAllInstancesOf(Store.class))
		{
			if (sto.getId() == id)
			{
				store = sto;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(store) == false && StandardOPs.includes(((List<Store>)EntityManager.getAllInstancesOf(Store.class)), store)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Store", store);
			
			
			;
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Store>)EntityManager.getAllInstancesOf(Store.class)), store)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : store
		//all relevant entities : Store
	} 
	 
	static {opINVRelatedEntity.put("deleteStore", Arrays.asList("Store"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
