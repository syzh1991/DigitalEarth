package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestCompiler {
	private static void compilejava() throws Exception {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int results = compiler.run(null, null, null, "HelloWorld.java");
		System.out.println((results == 0) ? "编译成功" : "编译失败");
		// 在程序中运行test
		Runtime run = Runtime.getRuntime();
		Process p = run.exec("java HelloWorld");
		BufferedInputStream in = new BufferedInputStream(p.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String s;
		while ((s = br.readLine()) != null)
			System.out.println(s);
	}

	public static void main(String args[]) throws Exception {
		compilejava();
	}

}
