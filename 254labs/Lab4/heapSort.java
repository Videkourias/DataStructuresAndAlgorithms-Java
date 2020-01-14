import java.io.File;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;

public class heapSort {

	/** Merge two strings. See the textbook for explanation. **/
	public static void merge(String[] S1, String[] S2, String[] S) {
		int i = 0, j = 0;
		while (i + j < S.length) {
			if (j == S2.length || (i < S1.length && S1[i].compareTo(S2[j]) < 0))
				S[i + j] = S1[i++];
			else
				S[i + j] = S2[j++];
		}
	}

	public static void mergeSort(String[] S) {
		int n = S.length;
		if (n < 2)
			return;
		int mid = n / 2;
		// partition the string into two strings
		String[] S1 = Arrays.copyOfRange(S, 0, mid);
		String[] S2 = Arrays.copyOfRange(S, mid, n);
		mergeSort(S1);
		mergeSort(S2);
		merge(S1, S2, S);
	}

	static void constructHeap(String[] a){
        	int n = a.length;
        	for (int k = n/2; k >= 1; k--)
            		sink(a, k,n);
	}

    private static void heapSort(String[] pq) {
        int n = pq.length;
        constructHeap(pq);
        while (n > 1) {
            exch(pq, 1, n);
            n--;
            sink(pq, 1,n);
        }
    }
    
    private static void sink(String[] pq, int k, int n) {
	    while(2*k <= n){ //while children exist
            int j = 2*k;
            if(j < n && less(pq, j, j + 1)){ //if right child exists and is greater
                j = j + 1;
            }
            if(less(pq,j,k)){ //if the child is less than the parent, break
                break;
            }
            exch(pq,k,j); //swap values
            k = j; //continue with j
        }
    }

/** Note that less and exch are defined to offset the 1-based array **/
    private static boolean less(String[] pq, int i, int j) {
        return pq[i-1].compareTo( pq[j-1])<0;
    }

    private static void exch(String[] pq, int i, int j) {
        String swap = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = swap;
    }

	private static void swap(String[] array, int i, int j) {
		String tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

	public static Entry<String, Integer> count_ARRAY_SORT(String[] tokens, String sortMethod){
		int CAPACITY = 1000000;
		String[] words = new String[CAPACITY];
		int[] counts = new int[CAPACITY];
		if (sortMethod.equals("HEAP"))
			heapSort(tokens);
		else if (sortMethod.equals("MERGE"))
			mergeSort(tokens);
		else
			System.out.println(sortMethod + " sorting method does not exist.");
		int j = 0, k = 0;
		int len = tokens.length;
		while (j < len - 1) {
			int duplicates = 1;
			while (j < len - 2 & tokens[j].equals(tokens[j + 1])) {
				j++;
				duplicates++;
			}

			words[k] = tokens[j];
			counts[k] = duplicates;
			j++;
			k++;
		}

		/** get the max count **/
		int maxCount = 0;
		String maxWord = "";
		for (int i = 0; i < CAPACITY & words[i] != null; i++) {
			if (counts[i] > maxCount) {
				maxWord = words[i];
				maxCount = counts[i];
			}
		}

		HeapInt h=new HeapInt(counts);
		for (int i=0; i<20; i++)
			System.out.println(h.removeMax());

		return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
	}

	static String[] readText(String PATH) throws Exception {
		Scanner doc = new Scanner(new File(PATH)).useDelimiter("[^a-zA-Z]+");
		// tokenize text. any characters other than English letters(a-z and A-Z// ) are delimiters.
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
		String PATH = "../text/dblp";
        
//		String[] METHODS = { "HEAP","MERGE"};
//		String[] DATASETS = { "200", "500", "1k", "5k", "10k", "100k", "1m", "" };
       	String[] METHODS = {"HEAP"};
       	String[] DATASETS = {"500"};

		String[] tokens;
		// run the experiments on different data sets
		for (int j = 0; j < 1; j++) {
			// run the experiments using different methods
			System.out.println("Data is " + DATASETS[j]);
			for (int i = 0; i < 1; i++) {
				tokens = readText(PATH + DATASETS[j] + ".txt");
				long startTime = System.currentTimeMillis();
				Entry<String, Integer> entry = count_ARRAY_SORT(tokens, METHODS[i]);
				long endTime = System.currentTimeMillis();
				String time = String.format("%12d", endTime - startTime);
				System.out.println(METHODS[i] + " method\t time=" + time + ". Most popular word is " + entry.getKey() + ":" + entry.getValue());
			}
		}
	}
}
