package com.decorator;
/*
 This is class is responsible for creating objects for the items which needs to be added in
 the bill.
 */
public class BillableItem implements Item {

	private String name;
	private boolean isImported = false;
	private boolean isExempt = false;
	private double initPrice;

	public BillableItem(String name, double initPrice) {
		this.name = name;
		this.initPrice = initPrice;
	}

	
	/*
	 * Getters and setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isImported() {
		return isImported;
	}

	public void setImported(boolean isImported) {
		this.isImported = isImported;
	}

	public boolean isExempt() {
		return isExempt;
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
		return name.hashCode() + new Integer((int) (initPrice * 100));
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
