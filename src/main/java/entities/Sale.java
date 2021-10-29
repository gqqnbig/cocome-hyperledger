package entities;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

@DataType()
public class Sale implements Serializable {
	
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
	private Integer BelongedstorePK;
	@JsonProperty
	private Integer BelongedCashDeskPK;
	private List<SalesLineItem> ContainedSalesLine = new LinkedList<SalesLineItem>(); 
	private Payment AssoicatedPayment; 
	
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
	public List<SalesLineItem> getContainedSalesLine() {
		return ContainedSalesLine;
	}	
	
	public void addContainedSalesLine(SalesLineItem saleslineitem) {
		this.ContainedSalesLine.add(saleslineitem);
	}
	
	public void deleteContainedSalesLine(SalesLineItem saleslineitem) {
		this.ContainedSalesLine.remove(saleslineitem);
	}
	public Payment getAssoicatedPayment() {
		return AssoicatedPayment;
	}	
	
	public void setAssoicatedPayment(Payment payment) {
		this.AssoicatedPayment = payment;
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
