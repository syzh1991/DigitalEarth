package org.gfg.hbase.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.gfg.hbase.HBaseUtil;

public class DataProcessor implements Runnable {
	public DataProcessor(int id,int x, int y, int level, int m, int n) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.level = level;
		this.m = m;
		this.n = n;

	}

	public static final String CF = "Test_Test";
	public static final String QUALIFIER = "20130806";

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		HBaseUtil hBaseUtil = new HBaseUtil();
		for (int i = x; i < x + m; i++) {
			for (int j = y; j < y + n; j++) {
				String rowKey = "" + level + "\\" + i + "\\" + i + "_" + j;
				byte[] temp = hBaseUtil.getImage(HBaseUtil.TABLE_NAME, rowKey,
						CF, QUALIFIER);
				//System.out.println(rowKey);
				try {
					//FileOutputStream os = new FileOutputStream("D:\\0\\"+ id+"_"+level + "_" + i + "_" + j);
					FileOutputStream os = new FileOutputStream("D:\\0\\"
							+ id);
					os.write(temp);
					os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		
		try {
			FileWriter fileWriter=new FileWriter("d:\\test.txt",true);
			fileWriter.write(String.valueOf(end-start)+"\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int x;
	private int y;
	private int level;
	private int m;
	private int n;
	private int id;
}
