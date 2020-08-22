/* 
* This class servers as a driver for the supporting classes
* 
* @author  Rupam Das 
* @since   2017-08-21 
*/

package com.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Interface.Item;
import com.InterfaceImpl.ItemImpl;
import com.decorator.ImportTax;
import com.decorator.SalesTax;
import com.util.Util;

public class ShoppingCart {

	private static final String ITEM_DESCRIPTION_REGEX = "(\\d+)\\s((\\w+\\s)+)at\\s(\\d+.\\d+)";//Pattern to match
    private final Map<Item, Integer> itemMap = new HashMap<Item, Integer>();
	DecimalFormat df = new DecimalFormat("###.00");
	
	// The code below will set values in ItemImpl object.
	public static Item parser(String order) {
		Matcher m = parse(order);
		String name = m.group(2).trim();
		ItemImpl item = new ItemImpl(name, Double.valueOf(m.group(4)));
		if (name.contains("imported"))
			item.setImported(true);
		if (Util.isExempt(name))
			item.setExempt(true);
		return item;
	}

	//The code below will will give the total count of any item.
	public static int count (String order) {
		return Integer.valueOf(parse(order).group(1));
	}
	
	
	//The code below will find next subsequence of the input sequence that matches the pattern.
	public static Matcher parse(String description) {
		Pattern pattern = Pattern.compile(ITEM_DESCRIPTION_REGEX);
		Matcher matcher = pattern.matcher(description);
		matcher.find();
		return matcher;
	}
	
	
	public static boolean matches(String description) {
		return Pattern.matches(ITEM_DESCRIPTION_REGEX, description);
	}
	
	
   /*
    * The code below will verify the type of item(local or imported), based on that it will decide 
	*whether tax will exempted or not and the related data will be stored in item.
	*/
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
	
	
	
   /*
    * First, the total amount is calculated and from there base price of the items removed, which will
	*give the total tax applied on all the product.
	*/
	public double getTaxtotal() {
		double taxtotal = 0;
		for (Item item : itemMap.keySet()){		
			double subTotal = item.getPrice() * getQuantity(item);
			double subInitTotal = item.getInitPrice() * getQuantity(item);
			taxtotal += subTotal - subInitTotal;
		}
		return taxtotal;
	}

	
	//The code below will calculate total bill amount.
	public double getTotal() {
		double total = 0;
		for (Item item : itemMap.keySet()){		
			double subTotal = item.getPrice() * getQuantity(item);
			total += subTotal;
		}
		return Util.roundPrice(total);
	}

	
	//The below code will print the input order
	public void printOrderInput() {
		System.out.println("Order input: ");
		for ( Item item : itemMap.keySet() ){
			System.out.println(itemMap.get(item) + " " + item.getName() + " at " + df.format(item.getInitPrice()));
		}	
		System.out.println();
	}
	
	
	/*
	* The below code will print the output i.e., the bill amount. It will print 
	*the total tax applied on goods and the total bill amount.
	*/
	public void printOrderResults() {	
		double taxtotal = 0;
		double total = 0;
		System.out.println("Order results: ");
		Set<Item> taxedItems = itemMap.keySet();
		for (Item item : taxedItems){		
			double subTotal = item.getPrice() * getQuantity(item);
			double subInitTotal = item.getInitPrice() * getQuantity(item);
			taxtotal += subTotal - subInitTotal;
			total += subTotal;
			System.out.println(getQuantity(item) + " " + item.getName() + ": " + df.format(subTotal));
		}
		total = Util.roundPrice(total);
		System.out.println("Sales Taxes: "+df.format(taxtotal));
		System.out.println("Total: "+df.format(total));
		System.out.println();
	}
	
	
		
	public static void main(String[] args)
	{
	    // Check how many arguments were passed in
	    if(args.length == 0)
	    {
	        System.exit(0);
	    }
	    for (String fileName: args) Util.getFromFile(fileName);
	}

}
