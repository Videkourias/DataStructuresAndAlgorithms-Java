public class HeapInt{
	private int[] heap;
	private int n = 0;
	
	public HeapInt(int a[]){
		n = a.length;
		for(int k = n/2; k >= 1; k--){
			sink(a, k, n);
		}
		heap = a;
	}


	private boolean less(int[] a, int i, int j) {
		return a[i-1] < a[j-1];
    	}

    	private void exch(int[] a, int i, int j) {
        	int swap = a[i-1];
        	a[i-1] = a[j-1];
        	a[j-1] = swap;
    	}

    	private void sink(int[] a, int k, int n) {
	    	while(2*k <= n){ //while children exist
        	    	int j = 2*k;
            		if(j < n && less(a, j, j + 1)){ //if right child exists and is greater
                		j = j + 1;
            		}
            		if(less(a,j,k)){ //if the child is less than the parent, break
                		break;
            		}
            		exch(a,k,j); //swap values
            		k = j; //continue with j
            	}
    	}


	public int removeMax(){
		int max = heap[0]; //get current max
		exch(heap, 1, n); //exchange top with bottom       
        	n--;
        	sink(heap, 1, n);

		return max;
	}
}
