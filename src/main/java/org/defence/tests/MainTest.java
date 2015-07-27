package org.defence.tests;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by root on 24.07.15.
 */

public class MainTest {
    public static void showQueue(Queue<Integer> queue) {
        System.out.println(queue);
    }


    public static void main(String[] args) {
        Queue<Integer> list = new PriorityQueue<Integer>();
        list.add(5);
        showQueue(list);

        list.add(2);
        showQueue(list);

        list.add(1);
        showQueue(list);

        list.add(4);
        showQueue(list);

        System.out.println(list);
    }
}