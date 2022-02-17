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
import org.hyperledger.fabric.contract.annotation.*;
import com.owlike.genson.annotation.*;

@DataType()
public class SalesLineItem implements Serializable {

	// Without @JsonProperty, genson will not set this field during deserialization.
	@JsonProperty
	private final String guid = EntityManager.getGuid();
	public Object getPK() {
		return guid;
	}
	
	/* all primary attributes */
	@Property()
	private int quantity;
	@Property()
	private float subamount;
	
	/* all references */
	@JsonProperty
	private String BelongedSalePK; 
	@JsonProperty
	private int BelongedItemPK; 
	
	/* all get and set functions */
	public int getQuantity() {
		return quantity;
	}	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getSubamount() {
		return subamount;
	}	
	
	public void setSubamount(float subamount) {
		this.subamount = subamount;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public Sale getBelongedSale() {
		return EntityManager.getSaleByPK(BelongedSalePK);
	}

	public void setBelongedSale(Sale sale) {
		this.BelongedSalePK = sale.getGuid();
	}

	@JsonIgnore
	public Item getBelongedItem() {
		return EntityManager.getItemByPK(BelongedItemPK);
	}

	public void setBelongedItem(Item item) {
		this.BelongedItemPK = item.getBarcode();
	}


}
