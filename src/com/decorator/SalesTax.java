/* 
* This is a concrete decorator class extending the Tax class.
* 
* @author  Rupam Das 
* @since   2017-08-21 
*/

package com.decorator;

import com.Interface.Item;

public class SalesTax extends Tax {

	private Item itemToDecorate;

	final double rate = 0.1;

	public SalesTax(Item item) {
		super(item);
		this.itemToDecorate = item;
	}

	@Override
	double getRate() {
		return this.rate;
	}

	public boolean isImported() {
		return itemToDecorate.isImported();
	}

	public String getName() {
		return itemToDecorate.getName();
	}

	public double getInitPrice() {
		return itemToDecorate.getInitPrice();
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
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
	public boolean isExempt() {
		return itemToDecorate.isExempt();
	}

}