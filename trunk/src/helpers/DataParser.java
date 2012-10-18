package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataParser {

	public static List<Unit> parseUnit(File file){
		List<Unit> units = new ArrayList<Unit>();
		Scanner scan;
		try {
			scan = new Scanner(file);
			
			String line = scan.nextLine();
			
			//Read until finding the unit header
			while(line != null && !line.equals("==Unit=="))
				line = scan.nextLine();
			
			if(scan.hasNext()) line = scan.nextLine();//gets the next line
			else line=null;
			
			//Read until the end of the file or until the next header
			while(line != null && !line.contains("==")){
				if(line.equals("")) break;
				String[] lineArray = line.split(",");
				try{
					units.add(new Unit(lineArray[0], lineArray[1], Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]), Integer.parseInt(lineArray[4])));
				}catch(IndexOutOfBoundsException | NumberFormatException e){ 
				}//Ignore incorrect lines
				if(scan.hasNext()) line = scan.nextLine();//gets the next line
				else line=null;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return units;
	}
	
	public static List<Customer> parseCustomer(File file){
		List<Customer> customers = new ArrayList<Customer>();
		Scanner scan;
		try {
			scan = new Scanner(file);
			
			String line = scan.nextLine();
			
			//Read until finding the unit header
			while(line != null && !line.equals("==Customers=="))
				line = scan.nextLine();
			
			if(scan.hasNext()) line = scan.nextLine();//gets the next line
			else line=null;
			
			//Read until the end of the file or until the next header
			while(line != null && !line.contains("==")){
				if(line.equals("")) break;
				String[] lineArray = line.split(",");
				try{
					customers.add(new Customer(lineArray[0], lineArray[1], lineArray[2]));
				}catch(IndexOutOfBoundsException e){ 
				}//Ignore incorrect lines
				if(scan.hasNext()) line = scan.nextLine();//gets the next line
				else line=null;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public static List<TimeShare> parseShares(File file){
		List<TimeShare> timeshares = new ArrayList<TimeShare>();
		Scanner scan;
		try {
			scan = new Scanner(file);
			
			String line = scan.nextLine();
			
			//Read until finding the unit header
			while(line != null && !line.equals("==Timeshares=="))
				line = scan.nextLine();
			
			if(scan.hasNext()) line = scan.nextLine();//gets the next line
			else line=null;
			
			//Read until the end of the file or until the next header
			while(line != null && !line.contains("==")){
				if(line.equals("")) break;
				String[] lineArray = line.split(",");
				try{
					timeshares.add(new TimeShare(new Customer(lineArray[0], lineArray[1], null), new Unit(lineArray[2], lineArray[3], 0, 0, 0), Integer.parseInt(lineArray[4])));
				}catch(IndexOutOfBoundsException | NumberFormatException e){ 
				}//Ignore incorrect lines
				if(scan.hasNext()) line = scan.nextLine();//gets the next line
				else line=null;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return timeshares;
	}
	
}
