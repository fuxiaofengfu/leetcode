package com.f.leetcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Foo {

	Lock lock1 = new ReentrantLock();
	Condition c1 = lock1.newCondition();
	Condition c2 = lock1.newCondition();

	public Foo() {

	}

	public void first(Runnable printFirst) throws InterruptedException {
		// printFirst.run() outputs "first". Do not change or remove this line.
		printFirst.run();
		lock1.lock();
		c1.signal();
		lock1.unlock();
	}

	public void second(Runnable printSecond) throws InterruptedException {
		lock1.lock();
		c1.await();
		// printSecond.run() outputs "second". Do not change or remove this line.
		printSecond.run();
		c2.signal();
		lock1.unlock();
	}

	public void third(Runnable printThird) throws InterruptedException {
		lock1.lock();
		c2.await();
		lock1.unlock();
		// printThird.run() outputs "third". Do not change or remove this line.
		printThird.run();
	}

	public static void main(String[] args) {
		final Foo foo = new Foo();
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		threadPool.execute(() -> {
			try {
				foo.first(() -> System.out.println("first"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(() -> {
			try {
				foo.third(() -> System.out.println("third"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		threadPool.execute(() -> {
			try {
				foo.second(() -> System.out.println("second"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}



}
