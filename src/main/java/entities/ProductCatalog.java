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
public class ProductCatalog implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private String name;
	
	/* all references */
	@JsonProperty
	private List<Object> ContainedItemsPKs = new LinkedList<>();

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
	
	/* all functions for reference*/
	@JsonIgnore
	public List<Item> getContainedItems() {
		return ContainedItemsPKs.stream().map(EntityManager::getItemByPK).collect(Collectors.toList());
	}

	public void addContainedItems(Item item) {
		this.ContainedItemsPKs.add(item.getPK());
	}

	public void deleteContainedItems(Item item) {
		this.ContainedItemsPKs.remove(item.getPK());
	}
	

	/* invarints checking*/
	public boolean ProductCatalog_UniqueProductCatalogId() {
		
		if (StandardOPs.isUnique(((List<ProductCatalog>)EntityManager.getAllInstancesOf("ProductCatalog")), "Id")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (ProductCatalog_UniqueProductCatalogId()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("ProductCatalog_UniqueProductCatalogId"));

}
