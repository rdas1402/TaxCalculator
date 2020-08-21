package com.decorator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
	private static final String ITEM_DESCRIPTION_REGEX = "(\\d+)\\s((\\w+\\s)+)at\\s(\\d+.\\d+)";
	
	
	//This method is to check if the item is imported and needs to be exempted from tax
	public static Item parser(String order) {
		Matcher m = parse(order);
		String name = m.group(2).trim();
		BillableItem item = new BillableItem(name, Double.valueOf(m.group(4)));
		if (name.contains("imported"))
			item.setImported(true);
		if (TaxService.isExempt(name))
			item.setExempt(true);
		return item;
	}

	// This method is to perform the counting of the total number of quantity of any particular item
	public static int count (String order) {
		return Integer.valueOf(parse(order).group(1));
	}
	
	// This method performs matching input parameter and grouping them so that we can find different data i.e., name, quantity and count
	public static Matcher parse(String description) {
		Pattern pattern = Pattern.compile(ITEM_DESCRIPTION_REGEX);
		Matcher matcher = pattern.matcher(description);
		matcher.find();
		return matcher;
	}
	
	// Validation of input parameters based on regular expression
	public static boolean matches(String description) {
		return Pattern.matches(ITEM_DESCRIPTION_REGEX, description);
	}
}
