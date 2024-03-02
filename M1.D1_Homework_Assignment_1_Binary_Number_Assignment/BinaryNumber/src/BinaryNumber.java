import java.util.Arrays;

/**
 * @author Mohini Mayekar
 * */

public class BinaryNumber {	
	private int data[];
	private boolean overflow;
	
	/**
	 * constructor for creating a binary number of length
	 * 
	 * @param length
	 */
	public BinaryNumber(int length) {
        if (length <= 0) {
            System.out.println("Error: Length should be greater than 0.");
            return;
        }
        data = new int[length];
        overflow = false;
    }
	
	/**
	 * constructor for creating a binary number given a string
	 * 
	 * @param str
	 */
	public BinaryNumber(String str) {
        int length = str.length();
        if (length <= 0) {
            System.out.println("Error: String should not be empty.");
            return;
        }

        data = new int[length];
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            int digit = Character.getNumericValue(ch);
            if (digit != 0 && digit != 1) {
                System.out.println("Error: Invalid binary string.");
                return;
            }
            data[i] = digit;
        }
        overflow = false;
    }
	
	/**
	 * determine the length of a binary number
	 * 
	 * @return
	 */
	public int getLength() {
		return data.length;
	}
	
	/**
	 * return a digit of a binary number given an index
	 * 
	 * @param index
	 * @return
	 */
	public int getDigit(int index) {
		if(index<0 || index >= data.length) {
			System.out.println("Error: Index out of bounds.");
			return -1;
			//throw new ArrayIndexOutOfBoundsException(index);
		}
		return data[index];
	}
	
	/**
	 * shifting all digits in a binary number to the right
	 * 
	 * @param amount
	 */
	public void shiftR(int amount) {
        if (amount <= 0) {
            System.out.println("Error: Amount should be greater than 0.");
            return;
        }

        reallocate(data.length + amount);
        for (int i = data.length - 1; i >= amount; i--) {
            data[i] = data[i - amount];
        }

        for (int i = 0; i < amount; i++) {
            data[i] = 0;
        }
    }

    /**
     * @param newLength
     */
    private void reallocate(int newLength) {
        data = Arrays.copyOf(data, newLength);
    }
	
	/**
	 * adding two binary numbers
	 * 
	 * @param aBinaryNumber
	 */
	public void add(BinaryNumber aBinaryNumber) {
		if(aBinaryNumber.getLength() != getLength()) {
			System.out.println("Error: Lengths of the binary numbers do not coincide.");
			return;
		}
		
		int carry = 0;
		for(int i = data.length - 1; i >= 0; i--) {
			int sum = data[i] + aBinaryNumber.getDigit(i) + carry;
			data[i] = sum % 2;
			carry = sum/2;
		}
		
		if(carry != 0) overflow = true;
	}
	
	/**
	 * transform a binary number to a String
	 */
	public String toString() {
		if(overflow) return "Overflow";
		
		StringBuilder sb = new StringBuilder();
		for(int digit: data) {
			sb.append(digit);
		}
		return sb.toString();
	}
	
	/**
	 * transform a binary number to its decimal notation
	 * 
	 * @return
	 */
	public int toDecimal() {
		int decimalVal = 0;
		int power = 0;
		
		for(int i = data.length - 1; i >= 0; i--) {
			decimalVal += data[i] * Math.pow(2, power);
			power++;
		}
		return decimalVal;
	}
	
	/**
	 * clears the overflow flag
	 */
	public void clearOverflow() {
		overflow = false;
	}
}
