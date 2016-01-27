package org.gfg.hbase;

public class MyTest extends Thread implements Runnable {
	public void run() {
		System.out.println("k");
	}
	public static void main(String[] args) {
		Thread t = new Thread(new MyTest());
		t.run();
	}
}
