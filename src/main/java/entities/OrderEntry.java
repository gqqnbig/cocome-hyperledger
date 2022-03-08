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
import org.hyperledger.fabric.contract.annotation.*;
import com.owlike.genson.annotation.*;

@DataType()
public class OrderEntry implements Serializable {

	// Without @JsonProperty, genson will not set this field during deserialization.
	@JsonProperty
	private final String guid = EntityManager.getGuid();

	public String getGuid() {
		return guid;
	}
	
	/* all primary attributes */
	@Property()
	private int quantity;
	@Property()
	private float subAmount;
	
	/* all references */
	@JsonProperty
	private int ItemPK; 
	
	/* all get and set functions */
	public int getQuantity() {
		return quantity;
	}	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getSubAmount() {
		return subAmount;
	}	
	
	public void setSubAmount(float subamount) {
		this.subAmount = subamount;
	}
	
	/* all functions for reference*/
	public Item getItem() {
		return Item;
	}	
	
	public void setItem(Item item) {
		this.Item = item;
	}			
	


}
