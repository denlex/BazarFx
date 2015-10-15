package org.defence.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 10/15/15.
 */
class Container {
	private List<Integer> contains = new ArrayList<>();

	public List<Integer> getContains() {
		return contains;
	}

	public void setContains(List<Integer> contains) {
		this.contains = contains;
	}
}

public class Test {

	public static void foo(Container container) {
		container.getContains().add(5);
	}

	public static void main(String[] args) {
		Container container = new Container();
		container.getContains().add(1);
		container.getContains().add(2);
		container.getContains().add(3);

		foo(container);

		System.out.println(Arrays.toString(container.getContains().toArray()));
	}
}
