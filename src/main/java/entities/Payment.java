package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;
import org.hyperledger.fabric.contract.annotation.*;

@DataType()
public class Payment implements Serializable {
	
	/* all primary attributes */
	@Property()
	private float amountTendered;
	
	/* all references */
	private Sale BelongedSale; 
	
	/* all get and set functions */
	public float getAmountTendered() {
		return amountTendered;
	}	
	
	public void setAmountTendered(float amounttendered) {
		this.amountTendered = amounttendered;
	}
	
	/* all functions for reference*/
	public Sale getBelongedSale() {
		return BelongedSale;
	}	
	
	public void setBelongedSale(Sale sale) {
		this.BelongedSale = sale;
	}			
	


}
