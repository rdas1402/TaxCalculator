/* 
* This is a abstract decorator class implementing the Item interface.
* 
* @author  Rupam Das 
* @since   2017-08-21 
*/

package com.decorator;

import com.Interface.Item;
import com.util.Util;

public abstract class Tax implements Item{

protected Item item;

protected double rate;

abstract double getRate();

public Tax(Item item){
this.item = item;
}

//The below code will calculate the price of the goods. Baseprice*taxRate
public double getPrice(){
	double salesTax = Util.nearest5Percent(this.item.getInitPrice() * this.getRate());
	return Util.roundPrice(this.item.getPrice() + salesTax);
}

}