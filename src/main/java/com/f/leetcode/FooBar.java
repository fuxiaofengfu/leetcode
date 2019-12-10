package com.f.leetcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class FooBar {

	Semaphore semaphore1 = new Semaphore(0);
	Semaphore semaphore2 = new Semaphore(0);
	private int n;

	public FooBar(int n) {
		this.n = n;
	}

	public void foo(Runnable printFoo) throws InterruptedException {

		for (int i = 0; i < n; i++) {
			// printFoo.run() outputs "foo". Do not change or remove this line.
			semaphore1.release();
			printFoo.run();
			semaphore2.acquire();
		}
	}

	public void bar(Runnable printBar) throws InterruptedException {

		for (int i = 0; i < n; i++) {
			semaphore1.acquire();
			// printBar.run() outputs "bar". Do not change or remove this line.
			printBar.run();
			semaphore2.release();
		}
	}


	public static void main(String[] args) {
		final FooBar foo = new FooBar(5);
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		threadPool.execute(() -> {
			try {
				foo.foo(() -> System.out.println("foo"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(() -> {
			try {
				foo.bar(() -> System.out.println("bar"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
