package org.gfg.hbase.test;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Concurrency {
	public static void main(String[] args) {
		File file = new File("d:\\test.txt");
		file.delete();
		
		int num = 450;
		int n = 5;
		ExecutorService executor = Executors.newFixedThreadPool(num);
		int levels[] = { 1, 2, 3, 4, 5, 6 };
		for (int i = 0; i < num; i++) {
			Random random = new Random(System.currentTimeMillis());
			executor.execute(new DataProcessor(i, 0, 0, levels[random
					.nextInt(6)], n, n));
		}
		executor.shutdown();

		try {
			executor.awaitTermination(30000, TimeUnit.MINUTES);
		} catch (InterruptedException ex) {
			System.out
					.println("Interrupted or timed out while awaiting termination");
		}
		// System.out.println((System.currentTimeMillis() -begin_time));
	}
}
