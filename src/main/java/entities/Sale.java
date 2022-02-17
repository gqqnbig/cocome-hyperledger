package entities;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;
import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

import org.hyperledger.fabric.contract.annotation.*;
import com.owlike.genson.annotation.*;

@DataType()
public class Sale implements Serializable {

	// Without @JsonProperty, genson will not set this field during deserialization.
	@JsonProperty
	private final String guid = EntityManager.getGuid();
	public Object getPK() {
		return guid;
	}

	/* all primary attributes */
	@Property()
	private LocalDate time;
	@Property()
	private boolean isComplete;
	@Property()
	private float amount;
	@Property()
	private boolean isReadytoPay;
	
	/* all references */
	@JsonProperty
	private int BelongedstorePK; 
	@JsonProperty
	private int BelongedCashDeskPK; 
	@JsonProperty
	private List<String> ContainedSalesLinePKs = new LinkedList<>(); 
	@JsonProperty
	private String AssoicatedPaymentPK; 
	
	/* all get and set functions */
	public LocalDate getTime() {
		return time;
	}	
	
	public void setTime(LocalDate time) {
		this.time = time;
	}
	public boolean getIsComplete() {
		return isComplete;
	}	
	
	public void setIsComplete(boolean iscomplete) {
		this.isComplete = iscomplete;
	}
	public float getAmount() {
		return amount;
	}	
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public boolean getIsReadytoPay() {
		return isReadytoPay;
	}	
	
	public void setIsReadytoPay(boolean isreadytopay) {
		this.isReadytoPay = isreadytopay;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public Store getBelongedstore() {
		return EntityManager.getStoreByPK(BelongedstorePK);
	}	
	
	public void setBelongedstore(Store store) {
		this.BelongedstorePK = store.getId();
	}

	@JsonIgnore
	public CashDesk getBelongedCashDesk() {
		return EntityManager.getCashDeskByPK(BelongedCashDeskPK);
	}	
	
	public void setBelongedCashDesk(CashDesk cashdesk) {
		this.BelongedCashDeskPK = cashdesk.getId();
	}

	@JsonIgnore
	public List<SalesLineItem> getContainedSalesLine() {
		return ContainedSalesLinePKs.stream().map(EntityManager::getSalesLineItemByPK).collect(Collectors.toList());
	}

	public void addContainedSalesLine(SalesLineItem saleslineitem) {
		this.ContainedSalesLinePKs.add(saleslineitem.getGuid());
	}

	public void deleteContainedSalesLine(SalesLineItem saleslineitem) {
		this.ContainedSalesLinePKs.remove(saleslineitem.getGuid());
	}

	@JsonIgnore
	public Payment getAssoicatedPayment() {
		Payment p = EntityManager.getCashPaymentByPK(AssoicatedPaymentPK);
		if (p != null)
			return p;
		p = EntityManager.getCardPaymentByPK(AssoicatedPaymentPK);
		if (p != null)
			return p;

		return null;
	}

	public void setAssoicatedPayment(Payment payment) {
		this.AssoicatedPaymentPK = payment.getGuid();
	}


	/* invarints checking*/
	public boolean Sale_AmountGreatAndEqualZero() {
		
		if (amount >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Sale_AmountGreatAndEqualZero()) {
			return true;
		} else {
			return false;
		}
	}	
	
	//check invariant by inv name
	public boolean checkInvariant(String INVname) {
		
		try {
			Method m = this.getClass().getDeclaredMethod(INVname);
			return (boolean) m.invoke(this);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	
	}	
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Sale_AmountGreatAndEqualZero"));

}
