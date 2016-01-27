package test;

import com.sun.org.apache.xpath.internal.Expression;

public class SuperClass {
	public void test() {
		System.out.println("SuperClass:test");
	}
	
	@Override
	public void finalize(){
		//System.out.println("SuperClass:finalize");
	//	Expression e=new Expression("");
	}
}
