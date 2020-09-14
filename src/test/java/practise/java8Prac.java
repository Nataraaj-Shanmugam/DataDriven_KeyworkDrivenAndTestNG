package practise;

@FunctionalInterface
interface test{
	
	abstract int testMethod1(int a);
	
}




public class java8Prac {
	public static void main(String[] args) {
		test i = a->a*2;
		System.out.println(i.testMethod1(9));
	}

}
