package model;

import java.util.Comparator;

public class BuyOrderComparator implements Comparator<Order>{	    

		@Override
		public int compare(Order o1, Order o2) {
			return o2.getPrice().compareTo(o1.getPrice());
		}	
	

}
