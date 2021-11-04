import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.Objects;

@Contract
public class Demo implements ContractInterface {
	static Integer count;

	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public int increase(final Context ctx) {
		ChaincodeStub stub = ctx.getStub();

		if (count == null) {
			String json = stub.getStringState("count");
			if (json != null && Objects.equals(json, "") == false)
				count = Integer.parseInt(json);
			else
				count = 0;
		}
		
		count++;
		stub.putStringState("count", String.valueOf(count));
		return count;
	}

}
