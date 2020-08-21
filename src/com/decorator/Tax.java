/* 
* This is a abstract decorator class implementing the Item interface.
* 
* @author  Rupam Das 
* @since   2017-08-21 
*/

package com.decorator;

import com.Interface.Item;
import com.service.TaxService;

public abstract class Tax implements Item{

protected Item item;

protected double rate;

abstract double getRate();

public Tax(Item item){
this.item = item;
}

public double getPrice(){
	double salesTax = TaxService.nearest5Percent(this.item.getInitPrice() * this.getRate());
	return TaxService.roundPrice(this.item.getPrice() + salesTax);
}

}