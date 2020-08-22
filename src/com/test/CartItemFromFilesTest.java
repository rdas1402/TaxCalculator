/*
* <h1>Calculate Tax on goods</h1> 
* Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is
* an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.
* When I purchase items, I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total
* cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price
* of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.
* 
* This class servers as a Junit Test case class
* 
* @author  Rupam Das 
* @since   2017-08-21 
*/

package com.test;

import org.junit.Test;

import com.util.Util;



public class CartItemFromFilesTest {

	@Test 
	public void testFileEntry1() {
		Util.getFromFile("source/in1.txt");
	}
		
		
	@Test
	public void testFileEntry2() {
		Util.getFromFile("source/in2.txt");
	}
	
	@Test
	public void testFileEntry3() {
		Util.getFromFile("source/in3.txt");
	}
	
	@Test
	public void testFileEntry4() {
		Util.getFromFile("source/in4.txt");
	}
	
	@Test
	public void testFileEntry5() {
		Util.getFromFile("source/in5.txt");
	}

}
