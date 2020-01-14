/** HashMap for 254. A simplified version of HashMap. 
 * Use separate chaining to resolve collisions. 
 */


import java.util.LinkedList;
import java.util.Random; 

public class HashMap254<Key, Value> {
	private static final int INIT_CAPACITY = 4;
	private int n; // number of elements
	private int m; // hash table size
	private LinkedListForHash254<Key, Value>[] st; 
	// array of linked-list.
	
	public HashMap254() {
		this(INIT_CAPACITY);
	}

	// used in resize.
	public HashMap254(int m) {
		this.m = m;
		st = (LinkedListForHash254<Key, Value>[]) new LinkedListForHash254[m];
		for (int i = 0; i < m; i++)
			st[i] = new LinkedListForHash254<Key, Value>();
	}

	// resize the hash table to have the given number of chains,
	// rehashing all of the keys
	private void resize(int n) {
		
		HashMap254<Key, Value> temp = new HashMap254<Key, Value>(n);
		
		for (int i = 0; i < m; i++) {
			for (Key key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.m = temp.m;
		this.n = temp.n;
		this.st = temp.st;
	}

	// hash value between 0 and m-1
	private int myhash(Key key) {
/*		int hash = 7;
		String k = (String) key; //here we assume keys are strings.
		int base=31;
		for (int i = 0; i < k.length(); i++)
			//calculate hashcode (h1) using polynomial method: 
			// hash=base^{n-1} key[0]+base^{n-2} key[1] +....+key[n-1]
			//hash=(int)Math.round((Math.pow(31,k.length()-i-1)))*k.charAt(i)+hash;
			//Horner's Method:
			hash = (hash * base + k.charAt(i));
*/
		//Improved method using bitwise operations
		int hash = 0;
		String k = (String) key;
		for(int i = 0; i < k.length(); i++){
			hash = (hash << 5) | (hash >>> 27); //5 bit cyclic shift
			hash += (int)k.charAt(i);
		}


		//Implement h2 using division method
		hash=Math.abs(hash) % m;
		return hash;
	}

	public Value get(Key key) {
		int i = myhash(key);
		return st[i].get(key);
	}

	public void put(Key key, Value val) {
		// double table size if load factor m/n >0.1
		if (n >= 10 * m)
			resize(2 * m);
	 
		int h = myhash(key);  		 
		if(st[h].get(key) != null){ 	
			n++;			
		}
		st[h].put(key, val); 		
	}

	public void delete(Key key) {
		if (key == null)
			throw new IllegalArgumentException("argument to delete() is null");
		int i = myhash(key);
		if (st[i].get(key)!=null)
			n--;
		st[i].delete(key);

		// halve table size if average length of list <= 2
		if (m > INIT_CAPACITY && n <= 2 * m)
			resize(m / 2);
	}

	public LinkedList<Key> keys() {
		LinkedList<Key> queue = new LinkedList<Key>();
		for (int i = 0; i < m; i++) {
			for (Key key : st[i].keys())
				queue.add(key);
		}
		return queue;
	}
}
