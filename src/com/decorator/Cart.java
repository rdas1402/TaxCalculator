package com.decorator;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {

	private final Map<Item, Integer> itemMap = new HashMap<Item, Integer>();
	
	DecimalFormat df = new DecimalFormat("###.00");
	
	public void put (Item item, int count){
		if (item.isImported()) item = new ImportTax(item);
		if (!item.isExempt()) item = new SalesTax(item);
		Integer i = this.itemMap.get(item); 
		if ( i!= null) count += i;
		this.itemMap.put(item, count);
	}
	
	public void remove (Item item) {
		this.itemMap.remove(item);
	}
	
	public void clear () {
		this.itemMap.clear();
	}
	
	public Set<Item> getItems() {
		return itemMap.keySet();
	}
	
	public int getQuantity(Item item){
		return itemMap.get(item);	
	}
	
		
	
	//This method is responsible for printing the Receipt
	public void printReceipt() {	
		double taxtotal = 0;
		double total = 0;
		System.out.println("Bill: ");
		Set<Item> taxedItems = itemMap.keySet();
		for (Item item : taxedItems){		
			double subTotal = item.getPrice() * getQuantity(item);
			double subInitTotal = item.getInitPrice() * getQuantity(item);
			taxtotal += subTotal - subInitTotal;
			total += subTotal;
			System.out.println(getQuantity(item) + " " + item.getName() + ": " + df.format(subTotal));
		}
		total = TaxService.roundPrice(total);
		System.out.println("Sales Taxes: "+df.format(taxtotal));
		System.out.println("Total: "+df.format(total));
		System.out.println();
	}
	
	public static void main(String[] args)
	{
	    // Check how many arguments were passed in
	    if(args.length == 0)
	    {
	        System.out.println("Proper Usage is: java -jar salestax filename(s)");
	        System.out.println("example: java -jar salestax in1.txt in2.txt");
	        System.exit(0);
	    }
	    for (String fileName: args) TaxService.getFromFile(fileName);
	}

}
