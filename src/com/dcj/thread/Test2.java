package com.dcj.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class Test2 {
	@Test
	public void t() {
		EvenChecker.test(new EvenGenerator());
	}
}

/**
 * 抽象类
 * 产出整数的产生器
 * @author chengjun
 * 
 */
abstract class IntGenerator {
	private volatile boolean canceled = false;

	public abstract int next();

	// Allow this to be canceled:
	public void cancel() {
		canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}
}

/**
 * 多线程的类
 * 检查是否为偶数
 * @author chengjun
 *
 */
class EvenChecker implements Runnable {
	private IntGenerator generator;
	private final int id;

	public EvenChecker(IntGenerator g, int ident) {
		generator = g;
		id = ident;
	}

	public void run() {
		while (!generator.isCanceled()) {
			int val = generator.next();
			if (val % 2 != 0) {
				System.out.println(val + " not even!");
				generator.cancel(); // Cancels all EvenCheckers
			}
		}
	}

	// Test any type of IntGenerator:
	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++)
			exec.execute(new EvenChecker(gp, i));
		exec.shutdown();
	}

	// Default value for count:
	public static void test(IntGenerator gp) {
		test(gp, 10);
	}
}

/**
 * 产生偶数的生成器
 * @author chengjun
 *
 */
class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;

	public int next() {
		++currentEvenValue; // Danger point here!
		++currentEvenValue;
		return currentEvenValue;
	}

}