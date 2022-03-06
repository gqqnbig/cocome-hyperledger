package entities;

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
public class CashDesk implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private String name;
	@Property()
	private boolean isOpened;
	
	/* all references */
	@JsonProperty
	private List<Object> ContainedSalesPKs = new LinkedList<>();
	@JsonProperty
	private Object BelongedStorePK;

	/* all get and set functions */
	public Object getPK() {
		return id;
	}

	public int getId() {
		return id;
	}	
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}	
	
	public void setName(String name) {
		this.name = name;
	}
	public boolean getIsOpened() {
		return isOpened;
	}	
	
	public void setIsOpened(boolean isopened) {
		this.isOpened = isopened;
	}
	
	/* all functions for reference*/
	//The getter or setter of PK fields must be marked with @JsonIgnore.
	@JsonIgnore
	public List<Sale> getContainedSales() {
		return ContainedSalesPKs.stream().map(EntityManager::getSaleByPK).collect(Collectors.toList());
	}

	public void addContainedSales(Sale sale) {
		this.ContainedSalesPKs.add(sale.getPK());
	}

	public void deleteContainedSales(Sale sale) {
		this.ContainedSalesPKs.remove(sale.getPK());
	}

	@JsonIgnore
	public Store getBelongedStore() {
		return EntityManager.getStoreByPK(BelongedStorePK);
	}

	public void setBelongedStore(Store store) {
		this.BelongedStorePK = store.getPK();
	}


	/* invarints checking*/
	public boolean CashDesk_UniqueCashDeskId() {
		
		if (StandardOPs.isUnique(((List<CashDesk>)EntityManager.getAllInstancesOf("CashDesk")), "Id")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (CashDesk_UniqueCashDeskId()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("CashDesk_UniqueCashDeskId"));

}
