/* CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
 * LetterInventory.java AND AnagramSolver.java CLASSES.
 *
 * Student information for assignment:
 *
 *  On my honor, Ayush Patel, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ap55837
 *  email address: patayush01@utexas.edu
 *  TA name: Tony 
 *  Number of slip days I am using: 1
 */
public class LetterInventory {
	private final int UNIQUE_LETTERS = 26;
	private final char lowerCaseA = 'a';
	private final char lowerCaseZ = 'z';
	private int[] letterStorage;
	private int numLetters;

	// pre: parameter cannot be null
	// post: creates a LetterInventory object based on the word passed in.
	// Stores the amount of times each letter occurs in string in letterStorage,
	// and stores the amount of letters added in numLetters
	public LetterInventory(String word) {
		if(word==null)
			throw new IllegalArgumentException("parameter cannot be null");
		word = word.toLowerCase();
		//initializing array to constant size of 26
		letterStorage = new int[UNIQUE_LETTERS];
		numLetters = 0;
		//traversing each letter of word
		for (int i = 0; i < word.length(); i++) {
			//getting the character at i
			char letter = word.charAt(i);
			//if letter is a letter in the english alphabet
			if (lowerCaseA <= letter && letter <= lowerCaseZ) {
				//add count of that letter in letterStorage( which is at the int value of letter- lowerCaseA)
				letterStorage[letter - lowerCaseA]++;
				//increment size variable
				numLetters++;
			}
		}
	}

	// pre: char passed in is a letter
	// post: returns the amount of times that letter is in LetterInvetory
	public int get(char letter) {
		letter= Character.toLowerCase(letter);
		if (lowerCaseA <= letter && letter <= lowerCaseZ) {
			return letterStorage[letter - lowerCaseA];
		}
		throw new IllegalArgumentException("Letter not in english dictionary");
	}

	// pre: none
	// post: returns an int that says how many letters are stored
	public int size() {
		return numLetters;
	}

	// pre: none
	// post: returns true if no letters stored, false otherwise
	public boolean isEmpty() {
		return numLetters == 0;
	}

	// pre: none
	// post: returns string that has appropriate amount of letters as original
	// word, in alphabetical order
	public String toString() {
		StringBuilder output = new StringBuilder();
		//traversing letterStorage
		for (int i = 0; i < letterStorage.length; i++) {
			//getting the quantity of that letter
			int quantity = letterStorage[i];
			//adding that letter quantity number of times
			for (int addTimes = 0; addTimes < quantity; addTimes++) {
				//char(i+ int value of lowerCaseA) will store the add the appropriate char to output
				output.append((char) (lowerCaseA + i));
			}
		}
		return output.toString();
	}

	// pre: parameter is not null
	// post: returns a new LetterInventory object that has added values of other
	// and calling object. Both other and calling object are not altered by
	// method
	public LetterInventory add(LetterInventory other) {
		if (other == null)
			throw new IllegalArgumentException("parameter cannot be null");
		//making a new LetterInventory object, passing in empty string
		LetterInventory result = new LetterInventory("");
		for (int i = 0; i < letterStorage.length; i++) {
			//adding the quantity at i of both letterInventories to result
			result.letterStorage[i] += (letterStorage[i]
					+ other.letterStorage[i]);
			//updating size based on the number of elements at i 
			result.numLetters += result.letterStorage[i];
		}
		return result;
	}

	// pre: parameter is not null
	// post: returns a new LetterInventory object that has subtracted values of
	// other and calling object. If there is more of a letter in other than the
	// calling object, method returns null. Neither other and calling object are
	// altered by this method.
	public LetterInventory subtract(LetterInventory other) {
		if (other == null)
			throw new IllegalArgumentException("parameter cannot be null");
		//making a new LetterInventory object, passing in empty string
		LetterInventory result = new LetterInventory("");
		for (int i = 0; i < letterStorage.length; i++) {
			//subtracting the quantity at i of this.letterStorage and other.letterStorage and adding that difference to result
			result.letterStorage[i] += (letterStorage[i]
					- other.letterStorage[i]);
			//if difference calculated is <0, then return null
			if (result.letterStorage[i] < 0)
				return null;
			//updating size based on the number of elements for result at i
			result.numLetters += result.letterStorage[i];

		}
		return result;
	}

	// pre: none
	// post: returns true if other is instanceof LetterInventory and has same
	// number of each letter has calling object, false otherwise
	public boolean equals(Object other) {
		//if other is a LetterInventory
		if (other instanceof LetterInventory) {
			LetterInventory otherInvent = (LetterInventory) other;
			//if they have same amount of letters stored
			if (otherInvent.numLetters == numLetters) {
				//traverse letterStorage
				for (int i = 0; i < letterStorage.length; i++) {
					//if find a quantity that is not the same, return false
					if (letterStorage[i] != otherInvent.letterStorage[i])
						return false;
				}
				//if got here, then size is same and no quantities of letters are different
				return true;
			}
		}
		//other is not an instanceof LetterInventory, so return false
		return false;
	}

}
