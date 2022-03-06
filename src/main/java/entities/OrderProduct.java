package entities;

import com.owlike.genson.annotation.*;
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
public class OrderProduct implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private LocalDate time;
	@Property()
	private OrderStatus orderStatus;
	@Property()
	private float amount;
	
	/* all references */
	@JsonProperty
	private Object SupplierPK;
	@JsonProperty
	private List<Object> ContainedEntriesPKs = new LinkedList<>();

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
	public LocalDate getTime() {
		return time;
	}	
	
	public void setTime(LocalDate time) {
		this.time = time;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}	
	
	public void setOrderStatus(OrderStatus orderstatus) {
		this.orderStatus = orderstatus;
	}
	public float getAmount() {
		return amount;
	}	
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public Supplier getSupplier() {
		return EntityManager.getSupplierByPK(SupplierPK);
	}

	public void setSupplier(Supplier supplier) {
		this.SupplierPK = supplier.getPK();
	}

	@JsonIgnore
	public List<OrderEntry> getContainedEntries() {
		return ContainedEntriesPKs.stream().map(EntityManager::getOrderEntryByPK).collect(Collectors.toList());
	}

	public void addContainedEntries(OrderEntry orderentry) {
		this.ContainedEntriesPKs.add(orderentry.getPK());
	}

	public void deleteContainedEntries(OrderEntry orderentry) {
		this.ContainedEntriesPKs.remove(orderentry.getPK());
	}
	


}
