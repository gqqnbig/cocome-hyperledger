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
public class Store implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private String name;
	@Property()
	private String address;
	@Property()
	private boolean isOpened;
	
	/* all references */
	@JsonProperty
	private List<Object> AssociationCashdeskesPKs = new LinkedList<>();
	@JsonProperty
	private List<Object> ProductcatalogsPKs = new LinkedList<>();
	@JsonProperty
	private List<Object> ItemsPKs = new LinkedList<>();
	@JsonProperty
	private List<Object> CashiersPKs = new LinkedList<>();
	@JsonProperty
	private List<Object> SalesPKs = new LinkedList<>();

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
	public String getAddress() {
		return address;
	}	
	
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean getIsOpened() {
		return isOpened;
	}	
	
	public void setIsOpened(boolean isopened) {
		this.isOpened = isopened;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public List<CashDesk> getAssociationCashdeskes() {
		return AssociationCashdeskesPKs.stream().map(EntityManager::getCashDeskByPK).collect(Collectors.toList());
	}

	public void addAssociationCashdeskes(CashDesk cashdesk) {
		this.AssociationCashdeskesPKs.add(cashdesk.getPK());
	}

	public void deleteAssociationCashdeskes(CashDesk cashdesk) {
		this.AssociationCashdeskesPKs.remove(cashdesk.getPK());
	}

	@JsonIgnore
	public List<ProductCatalog> getProductcatalogs() {
		return ProductcatalogsPKs.stream().map(EntityManager::getProductCatalogByPK).collect(Collectors.toList());
	}

	public void addProductcatalogs(ProductCatalog productcatalog) {
		this.ProductcatalogsPKs.add(productcatalog.getPK());
	}

	public void deleteProductcatalogs(ProductCatalog productcatalog) {
		this.ProductcatalogsPKs.remove(productcatalog.getPK());
	}

	@JsonIgnore
	public List<Item> getItems() {
		return ItemsPKs.stream().map(EntityManager::getItemByPK).collect(Collectors.toList());
	}

	public void addItems(Item item) {
		this.ItemsPKs.add(item.getPK());
	}

	public void deleteItems(Item item) {
		this.ItemsPKs.remove(item.getPK());
	}

	@JsonIgnore
	public List<Cashier> getCashiers() {
		return CashiersPKs.stream().map(EntityManager::getCashierByPK).collect(Collectors.toList());
	}

	public void addCashiers(Cashier cashier) {
		this.CashiersPKs.add(cashier.getPK());
	}

	public void deleteCashiers(Cashier cashier) {
		this.CashiersPKs.remove(cashier.getPK());
	}

	@JsonIgnore
	public List<Sale> getSales() {
		return SalesPKs.stream().map(EntityManager::getSaleByPK).collect(Collectors.toList());
	}

	public void addSales(Sale sale) {
		this.SalesPKs.add(sale.getPK());
	}

	public void deleteSales(Sale sale) {
		this.SalesPKs.remove(sale.getPK());
	}
	

	/* invarints checking*/
	public boolean Store_UniqueStoreId() {
		
		if (StandardOPs.isUnique(((List<Store>)EntityManager.getAllInstancesOf("Store")), "Id")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Store_UniqueStoreId()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Store_UniqueStoreId"));

}
