package com.rd.Item;


public interface Item {

String getName();

double getInitPrice(); //method to give base price

boolean isImported(); //method to check if the item is imported

boolean isExempt(); //method to check if item is exempted from tax

double getPrice(); //method to give price including tax

}