package entities;

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

@DataType()
public class Payment implements Serializable {
	
	/* all primary attributes */
	@Property()
	private float amountTendered;
	
	/* all references */
	@JsonProperty
	private String BelongedSalePK;

	/* all get and set functions */
	public float getAmountTendered() {
		return amountTendered;
	}	
	
	public void setAmountTendered(float amounttendered) {
		this.amountTendered = amounttendered;
	}
	
	/* all functions for reference*/
	public Sale getBelongedSale() {
		return EntityManager.getSaleByPK(BelongedSalePK);
	}

	public void setBelongedSale(Sale sale) {
		this.BelongedSalePK = sale.getGuid();
	}


}
