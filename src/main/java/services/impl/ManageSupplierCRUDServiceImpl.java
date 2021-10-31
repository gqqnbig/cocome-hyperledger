package services.impl;

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

public class ManageSupplierCRUDServiceImpl implements ManageSupplierCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageSupplierCRUDServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
	private CashDesk currentCashDesk;
	private Store currentStore;
			
	/* all get and set functions for temp property*/
	public CashDesk getCurrentCashDesk() {
		return currentCashDesk;
	}	
	
	public void setCurrentCashDesk(CashDesk currentcashdesk) {
		this.currentCashDesk = currentcashdesk;
	}
	public Store getCurrentStore() {
		return currentStore;
	}	
	
	public void setCurrentStore(Store currentstore) {
		this.currentStore = currentstore;
	}
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	
	
	/* Generate buiness logic according to functional requirement */
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean createSupplier(final Context ctx, int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);
		
		
		/* Code generated for contract definition */
		//Get supplier
		Supplier supplier = null;
		//no nested iterator --  iterator: any previous:any
		for (Supplier sup : (List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class))
		{
			if (sup.getId() == id)
			{
				supplier = sup;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(supplier) == true) 
		{ 
			/* Logic here */
			Supplier sup = null;
			sup = (Supplier) EntityManager.createObject("Supplier");
			sup.setId(id);
			sup.setName(name);
			EntityManager.addObject("Supplier", sup);
			
			
			;
			// post-condition checking
			if (!(true && 
			sup.getId() == id
			 && 
			sup.getName() == name
			 && 
			StandardOPs.includes(((List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class)), sup)
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
		//string parameters: [name]
		//all relevant vars : sup
		//all relevant entities : Supplier
	} 
	 
	static {opINVRelatedEntity.put("createSupplier", Arrays.asList("Supplier"));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public Supplier querySupplier(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);
		
		
		/* Code generated for contract definition */
		//Get supplier
		Supplier supplier = null;
		//no nested iterator --  iterator: any previous:any
		for (Supplier sup : (List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class))
		{
			if (sup.getId() == id)
			{
				supplier = sup;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(supplier) == false) 
		{ 
			/* Logic here */
			
			
			;
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			; return supplier;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean modifySupplier(final Context ctx, int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);
		
		
		/* Code generated for contract definition */
		//Get supplier
		Supplier supplier = null;
		//no nested iterator --  iterator: any previous:any
		for (Supplier sup : (List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class))
		{
			if (sup.getId() == id)
			{
				supplier = sup;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(supplier) == false) 
		{ 
			/* Logic here */
			supplier.setId(id);
			supplier.setName(name);
			
			
			;
			// post-condition checking
			if (!(supplier.getId() == id
			 && 
			supplier.getName() == name
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
		//string parameters: [name]
		//all relevant vars : supplier
		//all relevant entities : Supplier
	} 
	 
	static {opINVRelatedEntity.put("modifySupplier", Arrays.asList("Supplier"));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean deleteSupplier(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);
		
		
		/* Code generated for contract definition */
		//Get supplier
		Supplier supplier = null;
		//no nested iterator --  iterator: any previous:any
		for (Supplier sup : (List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class))
		{
			if (sup.getId() == id)
			{
				supplier = sup;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(supplier) == false && StandardOPs.includes(((List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class)), supplier)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Supplier", supplier);
			
			
			;
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class)), supplier)
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
		//all relevant vars : supplier
		//all relevant entities : Supplier
	} 
	 
	static {opINVRelatedEntity.put("deleteSupplier", Arrays.asList("Supplier"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
