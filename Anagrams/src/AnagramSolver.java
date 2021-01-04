import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnagramSolver {
	private static Map<String, LetterInventory> storedInventories;

	/*
	 * pre: list != null
	 * 
	 * @param list Contains the words to form anagrams from.
	 */
	public AnagramSolver(Set<String> dictionary) {
		if (dictionary == null)
			throw new IllegalArgumentException("dictionary cannot be null");
		storedInventories = new HashMap<>();
		// getting each word for dictionary, and adding word and its
		// LetterInventory to storedInventories if its not already in
		// storedInventories
		for (String word : dictionary) {
			// if that word is not a key in storedInventories (i.e not a repeat
			// word), add it as well as LetterInventory of that word to
			// storedInventories
			if (!storedInventories.containsKey(word))
				storedInventories.put(word, new LetterInventory(word));
		}

	}

	/*
	 * pre: maxWords >= 0, s != null, s contains at least one English letter.
	 * Return a list of anagrams that can be formed from s with no more than
	 * maxWords, unless maxWords is 0 in which case there is no limit on the
	 * number of words in the anagram
	 */
	public List<List<String>> getAnagrams(String s, int maxWords) {
		if (maxWords < 0 || s == null)
			throw new IllegalArgumentException(
					"maxWords has to be greater than 0 and string cannot be null");
		LetterInventory anagram = new LetterInventory(s);
		// precon, if s does not have at least one english letter
		if (anagram.isEmpty())
			throw new IllegalArgumentException(
					"s does not contain at least one english letter");
		List<List<String>> anagramStorage = new ArrayList<>();
		// if maxWords is 0, then can as many words as possible in anagrams. The
		// max amount of words that an anagram for a word that can have is
		// string's length (each word in anagram is 1 letter))
		if (maxWords == 0)
			maxWords = s.length();
		// passing in reduced dictionary based on anagram, anagramStorage, a new
		// ArrayList to store current words in anagram, string we are making
		// anagrams out of, maxWords, and the index of keys to start traversing
		// from (0)
		recursiveAnagrams(reduce(anagram), anagramStorage, new ArrayList<>(), s,
				anagram, maxWords, 0);
		// sorting anagramStorage using AnagramComparator
		Collections.sort(anagramStorage, new AnagramComparator());
		return anagramStorage;
	}

	// pre: none
	// post: recursively finds anagrams and stores it in anagramStorage
	private static void recursiveAnagrams(List<String> keys,
			List<List<String>> anagramStorage, List<String> words, String s,
			LetterInventory anagram, int maxWords, int index) {
		// base case: if s is empty, add a copy of it to anagramStorage (the
		// else if below checks if its within the limit of maxWords)
		if (s.isEmpty())
			anagramStorage.add(new ArrayList<>(words));
		// recursive step: num of words in current anagrams has to be strictly
		// less than maxWords (not <= because would be off by 1 because not
		// checked in base case)
		else if (words.size() < maxWords) {
			// traversing through arrayLsit of keys that are possibilities
			for (int i = index; i < keys.size(); i++) {
				String word = keys.get(i);
				// storing anagram before I change it
				LetterInventory oldAnagram = anagram;
				anagram = anagram.subtract(storedInventories.get(word));
				// if anagram is null, then its not a valid possibility, so stop
				// recursion, else, continue
				if (anagram != null) {
					// adding the word to the arrayList storing words of current
					// anagram
					words.add(word);
					// recursion, passing in anagram.toString() for s because
					// thats the same as s-the letters taken out from anagram of
					// current key, and i for index because do not want repeats
					recursiveAnagrams(keys, anagramStorage, words,
							anagram.toString(), anagram, maxWords, i);
					// if got here, recursive backtracking, remove the added
					// word from arrayList storing words of anagram
					words.remove(words.size() - 1);
				}
				// revert anagram to oldAnagram
				anagram = oldAnagram;

			}

		}

	}

	// pre: none
	// post: returns a list of keys that have possible anagrams based on
	// inventory
	private static List<String> reduce(LetterInventory inventory) {
		List<String> keys = new ArrayList<>();
		// for each entry in stored inventories
		for (Map.Entry<String, LetterInventory> entries : storedInventories
				.entrySet()) {
			// get the value for that entry (LetterInventory)
			LetterInventory inventOfEntry = entries.getValue();
			// if you use subtract and get a null, then its not a possible
			// inventory so don't add, else add
			if (inventory.subtract(inventOfEntry) != null)
				keys.add(entries.getKey());
		}
		// sort the list now so anagram builds in stored order
		Collections.sort(keys);
		return keys;
	}

	// nested comparator class, used to sort anagramStorage using
	// Collections.sort()
	public static class AnagramComparator implements Comparator<List<String>> {
		// pre:none
		// post: return int , if <0 then o1 precedes o2, if >0 then o2 precedes
		// o1, if 0 then are identical (shouldn't happen)
		public int compare(List<String> o1, List<String> o2) {
			int compare = o1.size() - o2.size();
			// if they are not same size, return the difference
			if (compare != 0)
				return compare;
			int i = 0;
			// traverse until you get to a different string and while <o1.size
			// which is same o2.size by this point
			while (compare == 0 && i < o1.size()) {
				// use string compareTo to lexicographically compare strings at
				// index i of both lists
				compare = o1.get(i).compareTo(o2.get(i));
				i++;
			}
			return compare;
		}

	}

}