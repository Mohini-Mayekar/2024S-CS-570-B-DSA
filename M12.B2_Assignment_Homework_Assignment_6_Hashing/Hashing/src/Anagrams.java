import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Anagrams {

	//Array of first 26 prime numbers
	final Integer[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101 };
	Map<Character, Integer> letterTable;
	Map<Long, ArrayList<String>> anagramTable;
	
	/**
	 * Constructor
	 * initializing letterTable and anagramTable
	 */
	public Anagrams() {
		letterTable = new HashMap<Character, Integer>();
		anagramTable = new HashMap<Long, ArrayList<String>>();
		buildLetterTable();
	}

	/**
	 * Creating the letterTable - mapping chars to prime numbers from the primes array
	 */
	private void buildLetterTable() {
		char[] c = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (int i = 0; i < c.length; i++) {
			letterTable.put(c[i], primes[i]);
		}
	}

	/**
	 * Calculate hash code for input string 's' and add to anagramTable if it is not already present
	 * @param s
	 */
	private void addWord(String s) {
		long hashCode = myHashCode(s);
		anagramTable.computeIfAbsent(hashCode, k -> new ArrayList<>()).add(s);
	}

	/**
	 * Calculate hash code for input string 's'
	 * Iterate over string s and use letterTable to generate hashCode
	 * @param s
	 * @return hashCode
	 */
	private Long myHashCode(String s) {
		long hashCode = 1;
		s = s.trim();
		for (char c : s.toCharArray()) {
			Integer prime = letterTable.get(c);
			if (prime != null) {
				hashCode *= prime;
			}
		}
		return hashCode;
	}

	/**
	 * Parse contents of the file
	 * - populate the anagramTable internally for using it in the getMaxEntries method
	 * @param s
	 * @throws IOException
	 */
	private void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			this.addWord(strLine);
		}
		br.close();
	}

	/**
	 * Iterate over anagramTable to get the list of words in a given dictionary that has the most number of anagrams
	 * @return
	 */
	private ArrayList<Map.Entry<Long, ArrayList<String>>> getMaxEntries() {
		ArrayList<Entry<Long, ArrayList<String>>> maxEntries = new ArrayList<>();
		int maxAnagrams = 0;
		for (Entry<Long, ArrayList<String>> entry : anagramTable.entrySet()) {
			int numAnagrams = entry.getValue().size();
			if (numAnagrams > maxAnagrams) {
				maxEntries.clear();
				maxEntries.add(entry);
				maxAnagrams = numAnagrams;
			} else if (numAnagrams == maxAnagrams) {
				maxEntries.add(entry);
			}
		}
		return maxEntries;
	}

	public static void main(String[] args) {
		Anagrams a = new Anagrams();
		final long startTime = System.nanoTime();
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime / 1000000000);
		System.out.println("Elapsed Time: " + seconds);
        System.out.println("Key of max anagrams: " + maxEntries.get(0).getKey());
        System.out.println("List of max anagrams: " + maxEntries.get(0).getValue());
        System.out.println("Length of list of max anagrams: " + maxEntries.get(0).getValue().size());
	}
}
