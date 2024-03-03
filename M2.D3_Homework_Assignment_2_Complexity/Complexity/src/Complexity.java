

/**
 * @author Mohini Mayekar
 * */

//import java.util.Random;

public class Complexity {
	//time complexity O(n^2)
	public static void method1(int n){
		if(checkInput(n)) {
			int counter = 1;
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n;j++) {
					System.out.println("Operation_1 : "+counter); 
					counter++;
				}
			}
		}		
	}
	
	//time complexity O(n^3)
	public static void method2(int n){
		if(checkInput(n)) {
			int counter = 1;
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n;j++) {
					for(int k = 0; k < n;k++) {
						System.out.println("Operation_2 : "+counter); 
						counter++;
					}
				}
			}
		}
	}
	
	//time complexity O(logn)
	public static void method3(int n) {
		if(checkInput(n)) {
			int counter = 1;
			int low = 0;
			int high = n;
			
			while(low <= high) {
				int mid = low + (high - low)/2;
				
				System.out.println("Operation_3 : "+counter);
				counter++;
				
				if(low < n/2) {
					high = mid -1;
				} else if(low > n/2) {
					low = mid + 1;
				} else {
					return;
				}
			}
		}
	}
	
//	private static int[] generateRandomArray(int n) {
//		int[] arr = new int[n];
//		Random r = new Random();
//		for(int i = 0; i < n; i ++) {
//			int temp = r.nextInt(0, 100);
//			arr[i] = temp;
//		}
//		return arr;
//	}
	
	//time complexity O(nlogn)
	public static void method4(int n) {
		if(checkInput(n)) {
			int counter = 1;
			//int[] array = generateRandomArray(n); 
			
			for(int i = 0; i < n; i++) {
				int low = 0;
				int high = n;
				
				while(low <= high) {
					int mid = low + (high - low)/2;
					
					System.out.println("Operation_4 : "+counter);
					counter++;
					
					if(low < n/2) {
						high = mid -1;
					} else if(low > n/2) {
						low = mid + 1;
					} else {
						return;
					}
				}
			}
		}
	}
	
	//time complexity O(loglogn)
	public static void method5(int n) {
		if(checkInput(n)) {
			//int[] array = generateRandomArray(n); 
			int[] array = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
	        int key = 13;

	        interpolationSearch(array, key);		
		}
	}
	private static int interpolationSearch(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;
        int counter = 0;

        while (low <= high && array[low] <= key && key <= array[high]) {
            int pos = low + ((key - array[low]) * (high - low)) / (array[high] - array[low]);
            
            System.out.println("Operation_5 : " + counter);
            counter++;

            if (array[pos] == key)
                return pos;
            
            if (array[pos] < key)
                low = pos + 1;
            else
                high = pos - 1;
        }

        return -1;
    }
	
	//time complexity O(2^n)
	public static void method6(int n) {
		if(checkInput(n)) {
			recurse(n,1);
		}		
	}
	private static int count = 1;
	
	private static int recurse(int n, int counter) {
		System.out.println("Operation_6 : " + count);
	    count++;
		counter++;
		if (n < 1) {
	        return n; 
	    } else {
	        return recurse(n - 1, counter) + recurse(n - 2, counter);
	    }
	}
	
	private static boolean checkInput(int n) {
		if (n <= 0) {
            System.out.println("Error: n should be greater than 0.");
            return false;
        }
		return true;
	}
	
	public static void main(String[] args) {
		method1(5);  //(25)
		method2(5);  //(125)
		method3(16); //(log 16) = 4
		method4(16); //(16 log 16) = 16*4 = 64
		method5(16); //(log log 16) = (log 4) = 2
		method6(4); // 2^4 = 16
	}
}
