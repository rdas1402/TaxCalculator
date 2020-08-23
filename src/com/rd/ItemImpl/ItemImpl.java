/*
 *This is a implementation class of Item interface.
 * @author  Rupam Das 
 * @since   2017-08-24 
 */
package com.rd.ItemImpl;

import com.rd.Item.Item;

public class ItemImpl implements Item {

	private String name;
	private boolean isImported = false;
	private boolean isExempt = false;
	private double initPrice;

	public ItemImpl(String name, double initPrice) {
		this.name = name;
		this.initPrice = initPrice;
	}

	
	public String getName() {
		return name;
	}

	public boolean isImported() {
		return isImported;
	}

	public boolean isExempt() {
		return isExempt;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setImported(boolean isImported) {
		this.isImported = isImported;
	}
	
	public void setExempt(boolean isExempt) {
		this.isExempt = isExempt;
	}

	public void setInitPrice(double price) {
		this.initPrice = price;
	}

	public double getInitPrice() {
		return this.initPrice;
	}

	@Override
	public int hashCode() {
		return name.hashCode() + (int) (initPrice * 100);
	}
	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		} else if (obj instanceof Item) {
			return (((Item) obj).hashCode() == this.hashCode());

		} else
			return false;
	}

	@Override
	public double getPrice() {
		return this.initPrice;
	}
}