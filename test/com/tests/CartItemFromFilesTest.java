package com.tests;

import org.junit.Test;

import com.decorator.TaxService;



public class CartItemFromFilesTest {

	// First test case - 1 book at 12.49
	//					 1 music CD at 14.99
	//					 1 chocolate bar at 0.85
	//					 ww eee at e.ee
	@Test 
	public void testFileEntry1() {
		TaxService.getFromFile("source/in1.txt");
	}
		
		
	// Second test case - 1 imported box of chocolates at 10.00
	//					 1 imported bottle of perfume at 47.50
	@Test
	public void testFileEntry2() {
		TaxService.getFromFile("source/in2.txt");
	}
	
	// Third test case - 1 imported bottle of perfume at 27.99
	//					 1 bottle of perfume at 18.99
	//					 1 packet of headache pills at 9.75
	//					 1 box of imported chocolates at 11.25
	@Test
	public void testFileEntry3() {
		TaxService.getFromFile("source/in3.txt");
	}
	
	// Fourth test case - Empty file
	@Test
	public void testFileEntry4() {
		TaxService.getFromFile("source/in4.txt");
	}
	
	// Fifth test case - File not available
	@Test
	public void testFileEntry5() {
		TaxService.getFromFile("source/in5.txt");
	}

}
