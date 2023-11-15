package software;

import hardware.Store;
import hardware.Warehouse;

public class StoreOrder extends Order
{
	private Store orderedBy;
	private Warehouse fulfilledBy;
		
	public StoreOrder(Store orderedByStore, Warehouse fulfilledByWH)
	{
		orderIdentifier = "S";
		orderedBy = orderedByStore;
		orderedByStore.addOrder(this);
		fulfilledBy = fulfilledByWH;
		fulfilledByWH.addOrder(this);
	}
	
	public void setOrderedBy(Store aStore)
	{
		orderedBy = aStore; 
	}
	public Store getOrderedBy()
	{
		return orderedBy;
	}
	
	public void setFulfilledBy(Warehouse aWH)
	{
		fulfilledBy = aWH;
	}
	public Warehouse geFulfilledBy()
	{
		return fulfilledBy;
	}	
}