/*
Author: Dean Willavoys
Date: 2019-10-25
*/
import java.util.Scanner;

class Stack{
	private char[] stack; //Stack
	private int size;  //Size of stack


//Constructor
	public Stack(){
		stack = new char[1];
		size = 1;
	}
	public Stack(int size){
		this();
		createStack(size);
	}

//Access
	//Pops the top element of the stack if the stack is not empty, returns escape char if stack is empty
	public char pop(){
		if(!isEmpty()){
			char c = stack[--size];
			stack[size] = '\0'; //Default char is escape
			return c;
		}
		return '\0';
	}

	//Adds a new element to the stack if doing so does not exceed stack capacity
	//Returns prematurely on error
	public void push(char c){
		if(size + 1 > stack.length){ //Additional char would exceed max stack size
			return;
		}
		stack[size++] = c; 
	}

	//Returns the top element in the stack
	public char top(){
		if(!isEmpty())
			return stack[size-1];
		return '\0';	
	}	

	//Returns the size of the stack
	public int size(){
		return size; 
	}

	//Returns true if the stack is empty
	public boolean isEmpty(){
		return size <= 0;
	}


	//Debug
	public void out(){
		for(char c: stack){
			System.out.print("[" + c + "]");
		}
		System.out.print("\n");
	}




//Internal
	//Initializes array using size given by user, if valid(size > 0)
	private int createStack(int size){
		if(validate(size)){
			stack = new char[size];
			this.size = 0; //Current size of array
		}
		return size;
	}

	//Checks passed size greater than 0
	private boolean validate(int size){
		return size > 0;
	}





//Driver
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter a string to check: ");
		String s = sc.nextLine();


		Stack stack = new Stack(s.length());

		String open = "{[("; //Valid opening and closing brackets
		String close = "}])";
		String valid = "0123456789+-/* ";
		boolean balanced = true;


		for(char c: s.toCharArray()){
			if(open.indexOf(c) != -1){ //c present in open
				stack.push(c);
			}
			else if(close.indexOf(c) != -1){ //c present in close
				if(!stack.isEmpty() && close.indexOf(c) == open.indexOf(stack.top())){
					stack.pop();
				}
				else{
					balanced = false;
					break;
				}
			}
			else if(valid.indexOf(c) == -1){
				balanced = false;
				break;
			}
	

		}
		if(!stack.isEmpty()){ //Stack has remaining elements
			balanced = false;
		}

		System.out.println(balanced);
	}
	
}



/*
Worst-case time is O(n) where n represents the length in characters of the passed String.
Worst-case is a String of n length which is balanced, algorithm ends early when unbalanced but must
parse all characters when balanced.
 
As this stack data type has constant time methods O(1), the run time doesn't increase through their use.
Therefore run time determined by for loop, which loops over all characters in String s, therefore O(n).
*/
