
public class IDLListTest {
	public static void main(String[] args) {
		testIntegerInput();
		testStringInput();
	}

	private static void testIntegerInput() {
		IDLList<Integer> integerList = new IDLList<>();
		integerList.add(22);
		integerList.add(16);
		integerList.add(25);
		integerList.add(0);
		integerList.add(null);
		System.out.println("size of list : " + integerList.size() + " 	list contents : " + integerList.toString());

		integerList.remove();
		integerList.append(5);
		System.out.println("size of list : " + integerList.size() + " 	list contents : " + integerList.toString());
		integerList.add(4, 6);
		System.out.println("size of list : " + integerList.size() + " 	list contents : " + integerList.toString());

		integerList.removeAt(3);
		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());

		integerList.removeAt(10);
		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());

		integerList.remove(1);
		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());

		System.out.println("Get ele at head : " + integerList.getHead());
		System.out.println("Get ele at tail : " + integerList.getLast());

		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());
		integerList.removeLast();
		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());

		System.out.println("returns the ele at given index : " + integerList.get(1));
		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());

		System.out.println("returns the ele at head : " + integerList.getHead());
		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());
		System.out.println("returns the ele at tail : " + integerList.getLast());
		System.out.println("size of list : " + integerList.size() + "	list contents : " + integerList.toString());
	}

	private static void testStringInput() {
		IDLList<String> strList = new IDLList<>();
		strList.add("alpha");
		strList.add("beta");
		strList.add("gamma");
		strList.add("delta");
		strList.add("epsilon");
		System.out.println("size of list : " + strList.size() + " 	list contents : " + strList.toString());

		strList.remove();
		strList.append("zeta");
		System.out.println("size of list : " + strList.size() + " 	list contents : " + strList.toString());
		strList.add(4, "eta");
		System.out.println("size of list : " + strList.size() + " 	list contents : " + strList.toString());

		strList.removeAt(3);
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());

		strList.removeAt(10);
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());

		strList.remove("theta");
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());

		strList.append("gamma");
		System.out.println("size of list : " + strList.size() + " 	list contents : " + strList.toString());

		strList.remove("gamma");
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());

		System.out.println("Get ele at head : " + strList.getHead());
		System.out.println("Get ele at tail : " + strList.getLast());

		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());
		strList.removeLast();
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());

		System.out.println("returns the ele at given index : " + strList.get(1));
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());

		System.out.println("returns the ele at head : " + strList.getHead());
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());
		System.out.println("returns the ele at tail : " + strList.getLast());
		System.out.println("size of list : " + strList.size() + "	list contents : " + strList.toString());
	}
}
