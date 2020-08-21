/* 
* This class servers as a driver for the supporting classes
* 
* @author  Rupam Das 
* @since   2017-08-21 
*/

 
 package com.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Interface.Item;
import com.InterfaceImpl.ItemImpl;
import com.decorator.ImportTax;
import com.decorator.SalesTax;


public class TaxService {

	private static final String ITEM_DESCRIPTION_REGEX = "(\\d+)\\s((\\w+\\s)+)at\\s(\\d+.\\d+)";
	
	private final static Map<Item, Integer> itemMap = new HashMap<Item, Integer>();
	
	static DecimalFormat df = new DecimalFormat("###.00");
	
	private static Set<String> exemptItems = null;
	static	{
		exemptItems = new HashSet<String>();
		exemptItems.add("book");
		exemptItems.add("headache pills");
		exemptItems.add("packet of headache pills");
		exemptItems.add("box of imported chocolates");
		exemptItems.add("imported box of chocolates");
		exemptItems.add("box of chocolates");
		exemptItems.add("chocolate");
		exemptItems.add("chocolate bar");
		exemptItems.add("pills");
//		exemptItems.add("");
	}
	
	public static void put (Item item, int count){
		if (item.isImported()) item = new ImportTax(item);
		if (!item.isExempt()) item = new SalesTax(item);
		Integer i = itemMap.get(item); 
		if ( i!= null) count += i;
		itemMap.put(item, count);
	}
	
	public void remove (Item item) {
		itemMap.remove(item);
	}
	
	public void clear () {
		itemMap.clear();
	}
	
	public Set<Item> getItems() {
		return itemMap.keySet();
	}
	
	public static int getQuantity(Item item){
		return itemMap.get(item);	
	}
	
		
	
	/* This method is responsible for performing the tax calculation. The tax is calculated 
	   differently for normal goods and imported goods. 
	*/
	public static void printReceipt() {	
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
	
	
	
	
	
	static public double nearest5Percent(double amount) {

		return new BigDecimal(Math.ceil(amount * 20)/20).setScale(2,RoundingMode.HALF_UP).doubleValue();

	}
	
	
		
	public static double roundPrice(double amount) {

		return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP)
				.doubleValue();

	}
	
	
	public static boolean isExempt(String name) {
		return exemptItems.contains(name);
	}
	
	
	
	
	
	/* The code below will read each line from the 
	file and perform some validation based on 
	regular expression, later it will add items in the cart and will
	print the receipt.
	*/
	
	public static void getFromFile(String fileName) {
		try {
		    BufferedReader in = new BufferedReader(new FileReader(fileName));
		    String str;
		    while ((str = in.readLine()) != null) {
		    	if (matches(str) && !str.isEmpty())
		    		put(parser(str), count(str)); 
		    	else if (!str.isEmpty()) System.out.println("unknown line format: " + str);
		    }
		    in.close();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			return;
		}
		printReceipt(); 
	}
	
	

	public static boolean matches(String description) {
		return Pattern.matches(ITEM_DESCRIPTION_REGEX, description);
	}
	
	
	
	
	
	/* The code below will will give the total count of any item.
	*/
	public static int count (String order) {
		return Integer.valueOf(parse(order).group(1));
	}
	
	
	
	
	
	
	/* The code below will set values in ItemImpl object.
	*/
	
	public static Item parser(String order) {
		Matcher m = parse(order);
		String name = m.group(2).trim();
		ItemImpl item = new ItemImpl(name, Double.valueOf(m.group(4)));
		if (name.contains("imported"))
			item.setImported(true);
		if (TaxService.isExempt(name))
			item.setExempt(true);
		return item;
	}
	
	
	
	
	/* The code below will find next 
	   subsequence of the input sequence that matches the pattern.
	*/
	
	public static Matcher parse(String description) {
		Pattern pattern = Pattern.compile(ITEM_DESCRIPTION_REGEX);
		Matcher matcher = pattern.matcher(description);
		matcher.find();
		return matcher;
	}
	
	
}