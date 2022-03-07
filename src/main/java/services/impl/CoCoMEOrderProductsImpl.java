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

@Contract
public class CoCoMEOrderProductsImpl implements CoCoMEOrderProducts, Serializable, ContractInterface {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public CoCoMEOrderProductsImpl() {
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
	public void refresh() {
		CoCoMESystem cocomesystem_service = (CoCoMESystem) ServiceManager.getAllInstancesOf(CoCoMESystem.class).get(0);
		cocomesystem_service.setCurrentCashDesk(currentCashDesk);
		cocomesystem_service.setCurrentStore(currentStore);
	}
	
	/* Generate buiness logic according to functional requirement */
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean makeNewOrder(final Context ctx, int orderid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			OrderProduct op = null;
			op = (OrderProduct) EntityManager.createObject("OrderProduct");
			op.setOrderStatus(OrderStatus.NEW);
			op.setId(orderid);
			op.setTime(LocalDate.now());
			EntityManager.addObject("OrderProduct", op);
			this.setCurrentOrderProduct(op);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			op.getOrderStatus() == OrderStatus.NEW
			 && 
			op.getId() == orderid
			 && 
			op.getTime().isEqual(LocalDate.now())
			 && 
			StandardOPs.includes(((List<OrderProduct>)EntityManager.getAllInstancesOf(OrderProduct.class)), op)
			 && 
			this.getCurrentOrderProduct() == op
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : op this
		//all relevant entities : OrderProduct 
	} 
	 
	static {opINVRelatedEntity.put("makeNewOrder", Arrays.asList("OrderProduct",""));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public Item[] listAllOutOfStoreProducts(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			List<Item> tempsitem = new LinkedList<>();
			//no nested iterator --  iterator: select
			for (Item item : ((List<Item>)EntityManager.getAllInstancesOf(Item.class)))
			{
				if (item.getStockNumber() == 0)
				{
					tempsitem.add(item);
				} 
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return tempsitem.toArray(Item[]::new);
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean orderItem(final Context ctx, int barcode, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* Code generated for contract definition */
		//Get item
		Item item = null;
		//no nested iterator --  iterator: any previous:any
		for (Item i : (List<Item>)EntityManager.getAllInstancesOf(Item.class))
		{
			if (i.getBarcode() == barcode)
			{
				item = i;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(item) == false) 
		{ 
			/* Logic here */
			OrderEntry order = null;
			order = (OrderEntry) EntityManager.createObject("OrderEntry");
			order.setQuantity(quantity);
			order.setSubAmount(item.getOrderPrice()*quantity);
			order.setItem(item);
			EntityManager.addObject("OrderEntry", order);
			currentOrderProduct.addContainedEntries(order);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			order.getQuantity() == quantity
			 && 
			order.getSubAmount() == item.getOrderPrice()*quantity
			 && 
			order.getItem() == item
			 && 
			StandardOPs.includes(((List<OrderEntry>)EntityManager.getAllInstancesOf(OrderEntry.class)), order)
			 && 
			StandardOPs.includes(currentOrderProduct.getContainedEntries(), order)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : order
		//all relevant entities : OrderEntry
	} 
	 
	static {opINVRelatedEntity.put("orderItem", Arrays.asList("OrderEntry"));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean chooseSupplier(final Context ctx, int supplierID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* Code generated for contract definition */
		//Get sup
		Supplier sup = null;
		//no nested iterator --  iterator: any previous:any
		for (Supplier s : (List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class))
		{
			if (s.getId() == supplierID)
			{
				sup = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(sup) == false && StandardOPs.oclIsundefined(currentOrderProduct) == false) 
		{ 
			/* Logic here */
			currentOrderProduct.setSupplier(sup);
			
			
			refresh();
			// post-condition checking
			if (!(currentOrderProduct.getSupplier() == sup
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : currentOrderProduct
		//all relevant entities : 
	} 
	 
	static {opINVRelatedEntity.put("chooseSupplier", Arrays.asList(""));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean placeOrder(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.stub = stub;
		
		
		/* previous state in post-condition*/
		/* service reference */
		/* service temp attribute */
		OrderProduct Pre_currentOrderProduct = SerializationUtils.clone(currentOrderProduct);
		/* objects in definition */

		/* check precondition */
		if (StandardOPs.oclIsundefined(currentOrderProduct) == false) 
		{ 
			/* Logic here */
			currentOrderProduct.setOrderStatus(OrderStatus.REQUESTED);
			//no nested iterator --  iterator: forAll
			for (OrderEntry o : currentOrderProduct.getContainedEntries())
			{
				currentOrderProduct.setAmount(currentOrderProduct.getAmount()+o.getSubAmount());
			}
			
			
			refresh();
			// post-condition checking
			if (!(currentOrderProduct.getOrderStatus() == OrderStatus.REQUESTED
			 && 
			((Predicate<List>) (list) -> {	
				Iterator<OrderEntry> oIt =  list.iterator();
				Iterator<OrderEntry> Pre_oIt =  Pre_currentOrderProduct.getContainedEntries().iterator();
				OrderEntry o = null;
				OrderEntry Pre_o = null;
					while (oIt.hasNext() && Pre_oIt.hasNext()) {
					o = oIt.next();
					Pre_o = Pre_oIt.next();
					if (!(currentOrderProduct.getAmount() == Pre_currentOrderProduct.getAmount()+o.getSubAmount())) {
						return false;
					}
				}
				return true;
			}).test(currentOrderProduct.getContainedEntries())
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : currentOrderProduct o
		//all relevant entities :  OrderEntry
	} 
	 
	static {opINVRelatedEntity.put("placeOrder", Arrays.asList("","OrderEntry"));}
	
	
	
	
	/* temp property for controller */
	private OrderProduct currentOrderProduct;
			
	/* all get and set functions for temp property*/
	public OrderProduct getCurrentOrderProduct() {
		return currentOrderProduct;
	}	
	
	public void setCurrentOrderProduct(OrderProduct currentorderproduct) {
		this.currentOrderProduct = currentorderproduct;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
