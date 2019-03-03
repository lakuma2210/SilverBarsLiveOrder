package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import file.FileUtils;
import liveOrderException.LiveOrderException;

public class LiveOrder {
	Scanner scanner = new Scanner(System.in);
	List<Order> orderList = new ArrayList<Order>();
	
	public void order() throws LiveOrderException
	{
	int option = 4;			
	do
	{	
	System.out.println("Welcome to Silver Bars Market Place Live Order");
	System.out.println("Please enter any one of the below options");
	System.out.println("1 : Register an Order");
	System.out.println("2 : Cancel a Registered Order");
	System.out.println("3 : View Live Dashboard");
	System.out.println("4 : Exit");
	String n=scanner.nextLine();			
	if(n!=null && !(n.equals("1") && n.equals("2") && n.equals("3") && n.equals("4")))
	{
		option=Integer.parseInt(n);
		if(option==1)
		{
			registerOrder();
		}
		else if(option==2)
		{
			deleteOrder();
		}
		else if (option==3)
		{
			viewLiveOrder();
		}	
		else if (option==4)
		{
			exitApp();
		}	
		
	}
	else
	{
	 System.out.println("Please enter a valid option to proceed");	
	}
	
	}while(option!=(FileUtils.optionExit));
}
	
	void exitApp()
	{
		System.out.println("4 : Exiting");
		System.exit(0);
	}
	
	void registerOrder()
	{		
		Order newOrder=new Order();
		System.out.println("Please enter user id");
		newOrder.setUserId(scanner.nextLine());
		System.out.println("Please enter order quantity");
		String quantity=scanner.nextLine();
		newOrder.setQuantity(Double.parseDouble(quantity));
		System.out.println("Please enter price per kg");
		newOrder.setPrice(scanner.nextLine());
		System.out.println("Please enter order type BUY or SELL");
		newOrder.setOrderType(scanner.nextLine());
		orderList.add(newOrder);
		
	}
	
	void deleteOrder()
	{
		System.out.println("Please enter user id you want to delete");		
		String userDel=scanner.nextLine();
		if(orderList.size()>0)
		{
			for(Order order:orderList){
		        if(userDel.equalsIgnoreCase(order.getUserId())){
		        	orderList.remove(order);
		        }
		    }

			///orderList.remove(deleteOrder);
			System.out.println("Order from user id: "+userDel+" deleted sucessfully.");
		}
		else
		{
			System.out.println("Order list is empty.");	
		}		
	}
	
	void viewLiveOrder()
	{
		List<Order> equalOrderList = new ArrayList<Order>();
		List<Order> displayOrderList = new ArrayList<Order>();
		List<Order> buyOrders = new ArrayList<Order>();
		List<Order> sellOrders = new ArrayList<Order>();
		displayOrderList.addAll(orderList);
		Order equalOrder = null;
		Double quantity = 0.0;
		String orderType;
		String price;
		if(displayOrderList.size()==0)
		{
			System.out.println("There are no orders to display in the board");
		}
		
		    while(displayOrderList.size()>1)
			{	
		    			
				List<Order> unequalOrderList = new ArrayList<Order>();		    	
				orderType=displayOrderList.get(0).getOrderType();
				price=displayOrderList.get(0).getPrice();
		    	for(int i=0; i<displayOrderList.size();i++)
		    	{
				if(displayOrderList.get(i).getOrderType().equalsIgnoreCase(orderType) && price.equalsIgnoreCase(displayOrderList.get(i).getPrice()))
				{
				equalOrder=new Order();
				quantity+=displayOrderList.get(i).getQuantity();
				equalOrder.setQuantity(quantity);
				equalOrder.setPrice(displayOrderList.get(i).getPrice());	
				equalOrder.setOrderType(displayOrderList.get(i).getOrderType());				
				}
				else
				{
					unequalOrderList.add(displayOrderList.get(i));
				}
						
			}
		    	if(equalOrder!=null)
		    	{
		    		equalOrderList.add(equalOrder);	
		    	}
		    	if(unequalOrderList.size()!=0)
		    	{
		    		displayOrderList.clear();
		    		displayOrderList.addAll(unequalOrderList);
		    	}
		    	else
		    	{
		    		displayOrderList=new ArrayList<Order>();
		    	}
				quantity=0.0;
				orderType=null;
				price=null;
				
			}
		    if(displayOrderList.size()==1)
		    {
		    	equalOrderList.add(displayOrderList.get(0));
		    }		
				
		for(Order order: equalOrderList)
		{
			if(FileUtils.BUY.equalsIgnoreCase(order.getOrderType()))
			{	
				buyOrders.add(order);							
			}
			else
			{
				sellOrders.add(order);			
			}
		}
		equalOrderList=null;
		displayOrderList=null;
		printOutput(buyOrders,sellOrders);		
		
	}
	
	void printOutput(List<Order> buyOrders,List<Order> sellOrders)
	{
		Collections.sort(sellOrders,new SellOrderComparator());
		Collections.sort(buyOrders,new BuyOrderComparator());
		System.out.println("Summary of Live Order Board");
		System.out.println("SELL Orders:");
		if(sellOrders.size()==0)
		{
			System.out.println("There are no SELL Orders");
		}
		else
		{
		for (Order sellOrder: sellOrders)
		{			
			System.out.println(sellOrder.getQuantity()+" kg for £"+sellOrder.getPrice());
			
		}
		}
		System.out.println("BUY Orders:");
		if(buyOrders.size()==0)
		{
			System.out.println("There are no BUY Orders");
		}
		else
		{
		for(Order buyOrder: buyOrders)
		{
			System.out.println(buyOrder.getQuantity()+" kg for £"+buyOrder.getPrice());	
			
		}
		}
	}


}
