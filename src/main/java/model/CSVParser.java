package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class CSVParser<T> {
	
	public List<String> parseLine(String csvLine){
		List<String> res = new ArrayList<String>();

		Scanner scanner = new Scanner(csvLine);
		scanner.useDelimiter(",");
		while(scanner.hasNext()){
			res.add(scanner.next());
		}
		scanner.close();
		return res;
	}
	
	public List<List<String>> parse(String csv){
		List<List<String>> res = new ArrayList<List<String>>();
		Scanner scanner = new Scanner(csv);
		while(scanner.hasNext()){
			List<String> newRes = this.parseLine(scanner.next());
			res.add(newRes);
		}
		scanner.close();
		return res;
	}
	public abstract T toObject(List<String> attributes) throws Exception;
	
	public List<T> toListObject(List<List<String>> allAttributes) throws Exception{
		List<T> res = new ArrayList<T>();
		for(List<String> attributes : allAttributes){
			res.add(this.toObject(attributes));
		}
		return res;
	}
}
