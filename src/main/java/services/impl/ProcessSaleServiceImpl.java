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
import java.util.logging.Logger;

import org.hyperledger.fabric.shim.*;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.contract.*;
import com.owlike.genson.Genson;
import java.util.*;

@Contract
public class ProcessSaleServiceImpl implements ProcessSaleService, Serializable, ContractInterface {
	private static final Genson genson = new Genson();

	private static final Logger logger = Logger.getLogger("ProcessSaleServiceImpl");


	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ProcessSaleServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
	private Object currentCashDeskPK;
	private Object currentStorePK;
			
	/* all get and set functions for temp property*/
	public CashDesk getCurrentCashDesk() {
		return EntityManager.getCashDeskByPK(getCurrentCashDeskPK());
	}

	private Object getCurrentCashDeskPK() {
		if (currentCashDeskPK == null)
			currentCashDeskPK = genson.deserialize(EntityManager.stub.getStringState("system.currentCashDeskPK"), Integer.class);
	
		return currentCashDeskPK;
	}	
	
	public void setCurrentCashDesk(CashDesk currentcashdesk) {
		setCurrentCashDeskPK(currentcashdesk.getPK());
	}

	private void setCurrentCashDeskPK(Object currentCashDeskPK) {
		String json = genson.serialize(currentCashDeskPK);
		EntityManager.stub.putStringState("system.currentCashDeskPK", json);
		this.currentCashDeskPK = currentCashDeskPK;
	}
	public Store getCurrentStore() {
		return EntityManager.getStoreByPK(getCurrentStorePK());
	}

	private Object getCurrentStorePK() {
		if (currentStorePK == null)
			currentStorePK = genson.deserialize(EntityManager.stub.getStringState("system.currentStorePK"), Integer.class);
	
		return currentStorePK;
	}	
	
	public void setCurrentStore(Store currentstore) {
		setCurrentStorePK(currentstore.getPK());
	}

	private void setCurrentStorePK(Object currentStorePK) {
		String json = genson.serialize(currentStorePK);
		EntityManager.stub.putStringState("system.currentStorePK", json);
		this.currentStorePK = currentStorePK;
	}
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	
	
	/* Generate buiness logic according to functional requirement */
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean makeNewSale(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		Sale currentSale = getCurrentSale();


		/* previous state in post-condition*/

		logger.info(String.format("%s", StandardOPs.oclIsundefined(getCurrentCashDesk())));
		logger.info(String.format("%s", getCurrentCashDesk().getIsOpened()));
		logger.info(String.format("%s", getCurrentSale()));

		/* check precondition */
		if (StandardOPs.oclIsundefined(getCurrentCashDesk()) == false && getCurrentCashDesk().getIsOpened() == true && (StandardOPs.oclIsundefined(currentSale) == true || (StandardOPs.oclIsundefined(currentSale) == false && currentSale.getIsComplete() == true))) 
		{ 
			/* Logic here */
			Sale s = null;
			s = (Sale) EntityManager.createObject("Sale");
			s.setBelongedCashDesk(getCurrentCashDesk());
			getCurrentCashDesk().addContainedSales(s);
			s.setIsComplete(false);
			s.setIsReadytoPay(false);
			EntityManager.addObject("Sale", s);
			this.setCurrentSale(s);
			
			
			;
			// post-condition checking
			if (!(true && 
			s.getBelongedCashDesk() == getCurrentCashDesk()
			 && 
			StandardOPs.includes(getCurrentCashDesk().getContainedSales(), s)
			 && 
			s.getIsComplete() == false
			 && 
			s.getIsReadytoPay() == false
			 && 
			StandardOPs.includes(((List<Sale>)EntityManager.getAllInstancesOf(Sale.class)), s)
			 && 
			this.getCurrentSale() == s
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
		//all relevant vars : s this
		//all relevant entities : Sale 
	} 
	 
	static {opINVRelatedEntity.put("makeNewSale", Arrays.asList("Sale",""));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean enterItem(final Context ctx, int barcode, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		Sale currentSale = getCurrentSale();

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
		/* service reference */
		/* service temp attribute */
		/* objects in definition */
		Item Pre_item = SerializationUtils.clone(item);

		/* check precondition */
		if (StandardOPs.oclIsundefined(currentSale) == false && currentSale.getIsComplete() == false && StandardOPs.oclIsundefined(item) == false && item.getStockNumber() > 0) 
		{ 
			/* Logic here */
			SalesLineItem sli = null;
			sli = (SalesLineItem) EntityManager.createObject("SalesLineItem");
			this.setCurrentSaleLine(sli);
			sli.setBelongedSale(currentSale);
			currentSale.addContainedSalesLine(sli);
			sli.setQuantity(quantity);
			sli.setBelongedItem(item);
			item.setStockNumber(item.getStockNumber()-quantity);
			sli.setSubamount(item.getPrice()*quantity);
			EntityManager.addObject("SalesLineItem", sli);
			
			
			;
			// post-condition checking
			if (!(true && 
			this.getCurrentSaleLine() == sli
			 && 
			sli.getBelongedSale() == currentSale
			 && 
			StandardOPs.includes(currentSale.getContainedSalesLine(), sli)
			 && 
			sli.getQuantity() == quantity
			 && 
			sli.getBelongedItem() == item
			 && 
			item.getStockNumber() == Pre_item.getStockNumber()-quantity
			 && 
			sli.getSubamount() == item.getPrice()*quantity
			 && 
			StandardOPs.includes(((List<SalesLineItem>)EntityManager.getAllInstancesOf(SalesLineItem.class)), sli)
			 && 
			EntityManager.saveModified(Item.class)
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
		//all relevant vars : sli item this
		//all relevant entities : SalesLineItem Item 
	} 
	 
	static {opINVRelatedEntity.put("enterItem", Arrays.asList("SalesLineItem","Item",""));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public float endSale(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		Sale currentSale = getCurrentSale();

		/* Code generated for contract definition */
		//Get sls
		List<SalesLineItem> sls = new LinkedList<>();
		sls = currentSale.getContainedSalesLine();
		//Get sub
		List<Float> sub = new LinkedList<>();
		//no nested iterator --  iterator: collect previous:collect
		for (SalesLineItem s : sls)
		{
			sub.add(s.getSubamount());
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(currentSale) == false && currentSale.getIsComplete() == false && currentSale.getIsReadytoPay() == false) 
		{ 
			/* Logic here */
			currentSale.setAmount(StandardOPs.sum(sub));
			currentSale.setIsReadytoPay(true);
			
			
			;
			// post-condition checking
			if (!(currentSale.getAmount() == StandardOPs.sum(sub)
			 && 
			currentSale.getIsReadytoPay() == true
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return currentSale.getAmount();
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : currentSale
		//all relevant entities : 
	} 
	 
	static {opINVRelatedEntity.put("endSale", Arrays.asList(""));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean makeCashPayment(final Context ctx, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		Sale currentSale = getCurrentSale();

		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(currentSale) == false && currentSale.getIsComplete() == false && currentSale.getIsReadytoPay() == true && amount >= currentSale.getAmount()) 
		{ 
			/* Logic here */
			CashPayment cp = null;
			cp = (CashPayment) EntityManager.createObject("CashPayment");
			cp.setAmountTendered(amount);
			cp.setBelongedSale(currentSale);
			currentSale.setAssoicatedPayment(cp);
			currentSale.setBelongedstore(getCurrentStore());
			getCurrentStore().addSales(currentSale);
			currentSale.setIsComplete(true);
			currentSale.setTime(LocalDate.now());
			cp.setBalance(amount-currentSale.getAmount());
			EntityManager.addObject("CashPayment", cp);
			
			
			;
			// post-condition checking
			if (!(true && 
			cp.getAmountTendered() == amount
			 && 
			cp.getBelongedSale() == currentSale
			 && 
			currentSale.getAssoicatedPayment() == cp
			 && 
			currentSale.getBelongedstore() == getCurrentStore()
			 && 
			StandardOPs.includes(getCurrentStore().getSales(), currentSale)
			 && 
			currentSale.getIsComplete() == true
			 && 
			currentSale.getTime().isEqual(LocalDate.now())
			 && 
			cp.getBalance() == amount-currentSale.getAmount()
			 && 
			StandardOPs.includes(((List<CashPayment>)EntityManager.getAllInstancesOf(CashPayment.class)), cp)
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
		//all relevant vars : currentSale cp
		//all relevant entities :  CashPayment
	} 
	 
	static {opINVRelatedEntity.put("makeCashPayment", Arrays.asList("","CashPayment"));}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	@SuppressWarnings("unchecked")
	public boolean makeCardPayment(final Context ctx, String cardAccountNumber, LocalDate expiryDate, float fee) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		Sale currentSale = getCurrentSale();

		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(currentSale) == false && currentSale.getIsComplete() == false && currentSale.getIsReadytoPay() == true && services.thirdPartyCardPaymentService(ctx, cardAccountNumber, expiryDate, fee)) {
			/* Logic here */
			CardPayment cdp = null;
			cdp = (CardPayment) EntityManager.createObject("CardPayment");
			cdp.setAmountTendered(fee);
			cdp.setBelongedSale(currentSale);
			currentSale.setAssoicatedPayment(cdp);
			cdp.setCardAccountNumber(cardAccountNumber);
			cdp.setExpiryDate(expiryDate);
			EntityManager.addObject("CardPayment", cdp);
			currentSale.setBelongedstore(getCurrentStore());
			getCurrentStore().addSales(currentSale);
			currentSale.setIsComplete(true);
			currentSale.setTime(LocalDate.now());
			
			
			;
			// post-condition checking
			if (!(true && 
			cdp.getAmountTendered() == fee
			 && 
			cdp.getBelongedSale() == currentSale
			 && 
			currentSale.getAssoicatedPayment() == cdp
			 && 
			cdp.getCardAccountNumber() == cardAccountNumber
			 && 
			cdp.getExpiryDate() == expiryDate
			 && 
			StandardOPs.includes(((List<CardPayment>)EntityManager.getAllInstancesOf(CardPayment.class)), cdp)
			 && 
			currentSale.getBelongedstore() == getCurrentStore()
			 && 
			StandardOPs.includes(getCurrentStore().getSales(), currentSale)
			 && 
			currentSale.getIsComplete() == true
			 && 
			currentSale.getTime().isEqual(LocalDate.now())
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
		//string parameters: [cardAccountNumber]
		//all relevant vars : currentSale cdp
		//all relevant entities :  CardPayment
	} 
	 
	static {opINVRelatedEntity.put("makeCardPayment", Arrays.asList("","CardPayment"));}
	
	
	
	
	/* temp property for controller */
	private SalesLineItem currentSaleLine;
	private String currentSalePK;
	private PaymentMethod currentPaymentMethod;
			
	/* all get and set functions for temp property*/
	public SalesLineItem getCurrentSaleLine() {
		return currentSaleLine;
	}	
	
	public void setCurrentSaleLine(SalesLineItem currentsaleline) {
		this.currentSaleLine = currentsaleline;
	}
	public Sale getCurrentSale() {
		if (currentSalePK == null) {
			currentSalePK = EntityManager.stub.getStringState("ProcessSaleServiceImpl.currentSalePK");
		}
		return EntityManager.getSaleByPK(currentSalePK);
	}

	public void setCurrentSale(Sale currentsale) {
		EntityManager.stub.putStringState("ProcessSaleServiceImpl.currentSalePK", currentsale.getGuid());
		currentSalePK = currentsale.getGuid();
	}
	public PaymentMethod getCurrentPaymentMethod() {
		return currentPaymentMethod;
	}	
	
	public void setCurrentPaymentMethod(PaymentMethod currentpaymentmethod) {
		this.currentPaymentMethod = currentpaymentmethod;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
