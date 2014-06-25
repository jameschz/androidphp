package com.app.demos.test;

import java.util.ArrayList;
import java.util.List;

public class TestDemoImpl implements TestDemo {
	
	@Override
	public void testArray() {
		int[] array = new int[1000];
		for (int i = 0; i < 1000; i++) {
			array[i] = i;
		}
	}
	
	@Override
	public void testArrayList() {
		List<Integer> arrayList = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			arrayList.add(i, i);
		}
	}
}