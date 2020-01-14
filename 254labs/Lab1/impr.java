import java.io.File;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;

public class impr {
	public static Entry<String, Integer> improvedArray(String[] tokens) {
		int numWords = tokens.length; //Number of words passed
		Arrays.sort(tokens); //Sorts the words passed

		String currWord; //Current word and count
		int currCount = 1;

		String maxWord = " "; //Max word and count so far encountered
		int maxCount = 1;

		for(int i = 0; i < numWords - 1; i++){
			currWord = tokens[i];

			if(currWord.compareTo(tokens[i+1]) == 0){ //Case for multiple of the same word
				currCount += 1;
			}
			else{ //Case for distinct words
				currCount = 1;
			}
			
			if(currCount > maxCount){ //Case for num words greater than current max num words
				maxWord = currWord;
				maxCount = currCount;
			}

		}

		return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
	}



	public static Entry<String, Integer> improvedLL(String[] tokens) {
		LinkedList<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>();
		
		Arrays.sort(tokens); //Sorts the words passed
		int numWords = tokens.length; //The number of words passed


		int currCount = 1; //The number of times a word repeats
		for(int i = 0; i < numWords - 1; i+= currCount){
			currCount = 1; 
			int j = i; //Integer used to increment through sub array of repeat words
			
			while(tokens[j].compareTo(tokens[j+1]) == 0){ //Loops through subarray of repeated words
				currCount++;
				j++;
				if(j >= numWords - 1){	//Case for end of list being reached
					break;
				}
			}
			list.addLast(new AbstractMap.SimpleEntry<String, Integer>(tokens[i], currCount));
		}


		int maxCount = 0; //The largest amount of times a word appears
		String maxWord = ""; //The most frequent word
		int listSize = list.size(); //The size of the linked list

		for (int i = 0; i < listSize; i++) {
			int count = list.get(i).getValue();
			if (count > maxCount) {
				maxWord = list.get(i).getKey();
				maxCount = count;
			}
		}
		return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
	}


	public static Entry<String, Integer> linear(String[] tokens){
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for(String s: tokens){
			if(map.containsKey(s)){
				map.put(s, map.get(s) + 1);
			}
			else{			
				map.put(s, 0);
			}		
		}
		
		String maxWord = " ";
		int maxCount = 0;		
		for(HashMap.Entry<String, Integer> entry: map.entrySet()){
			if(entry.getValue() > maxCount){
				maxWord = entry.getKey();
				maxCount = entry.getValue();
			}
		}
		

		
		return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
	}

public static Entry<String, Integer> originalArray(String[] tokens) {
		LinkedList<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>();
		for (int j = 0; j < tokens.length; j++) {
			String word = tokens[j];
			boolean found = false;
			for (int i = 0; i < list.size(); i++) {
				Entry<String, Integer> e = list.get(i);

				if (word.equals(e.getKey())) {
					e.setValue(e.getValue() + 1);
					list.set(i, e);
					found = true;
					break;
				}
			}
			if (!found)
				list.add(new AbstractMap.SimpleEntry<String, Integer>(word, 1));
		}

		int maxCount = 0;
		String maxWord = "";
		for (int i = 0; i < list.size(); i++) {
			int count = list.get(i).getValue();
			if (count > maxCount) {
				maxWord = list.get(i).getKey();
				maxCount = count;
			}
		}
		return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
	}



	static String[] readText(String PATH) throws Exception {
		Scanner doc = new Scanner(new File(PATH)).useDelimiter("[^a-zA-Z]+");
		int length = 0;
		while (doc.hasNext()) {
			doc.next();
			length++;
		}

		String[] tokens = new String[length];
		Scanner s = new Scanner(new File(PATH)).useDelimiter("[^a-zA-Z]+");
		length = 0;
		while (s.hasNext()) {
			tokens[length] = s.next().toLowerCase();
			length++;
		}
		doc.close();

		return tokens;
	}

	public static void main(String[] args) throws Exception {
		boolean DEBUG = false;

		String PATH = "../text/dblp100k.txt"; 
		String[] tokens = readText(PATH);

//		long startTime = System.currentTimeMillis();

//		Entry<String, Integer> entry = improvedLL(tokens); 

//		long endTime = System.currentTimeMillis();

//		String time = String.format("%12d", endTime - startTime);
//		System.out.println("time\t" + time + "\t" + entry.getKey() + ":" + entry.getValue());

		tokens = readText(PATH);
		long startTime = System.currentTimeMillis();

		Entry<String, Integer> entry = linear(tokens);

		long endTime = System.currentTimeMillis();

		String time = String.format("%12d", endTime - startTime);

		System.out.println("time\t" + time + "\t" + entry.getKey() + ":" + entry.getValue());
	

	

//		tokens = readText(PATH);
//		startTime = System.currentTimeMillis();

//		entry = linear(tokens);

//		endTime = System.currentTimeMillis();

//		time = String.format("%12d", endTime - startTime);

//		System.out.println("time\t" + time + "\t" + entry.getKey() + ":" + entry.getValue());
		





		if(DEBUG){
			tokens = readText(PATH);
			startTime = System.currentTimeMillis();

			entry = originalArray(tokens);

			endTime = System.currentTimeMillis();

			time = String.format("%12d", endTime - startTime);

			System.out.println("time\t" + time + "\t" + entry.getKey() + ":" + entry.getValue());
		}


	}

}
