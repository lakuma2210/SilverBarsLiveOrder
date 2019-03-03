

import liveOrderException.LiveOrderException;
import model.LiveOrder;

public class SilverBarsLiveOrder {

	public static void main(String[] args) {
		try
		{
		LiveOrder order= new LiveOrder();
		order.order();
		}catch(LiveOrderException e)
		{
			System.out.println("There was an error executing Order method:" + e.getLocalizedMessage());
		}
	}
}
