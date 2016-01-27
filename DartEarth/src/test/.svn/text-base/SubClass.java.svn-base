package test;

public class SubClass extends SuperClass {
	
	//private SubInnerClass c=new SubInnerClass();
	
	public class SubInnerClass{
		private SubClass c=new SubClass();
	}
	
	@Override
	public void test() {
		System.out.println("SubClass:test");
	}
	
	@Override
	public void finalize(){
		System.out.println("SubClass:finalize");
	}

	public static void main(String[] str) {
//		SuperClass c = new SubClass();
//		c.test();
//		System.out.println(c.getClass());
//		c=null;
//		
//		System.gc();
		String a="student";
		String b="student";
		System.out.println(a==b);
		System.out.println(a.equals(b));
		a=new String("student");
		b=new String("student");
		System.out.println(a==b);
		System.out.println(a.equals(b));
	}
}
