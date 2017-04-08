package com.dgq.Task;

import java.util.PriorityQueue;

public class SequenceNumber {
	
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>(){
		protected Integer initialValue() {
			return 0;
		};
	};	
	
	public static class TestClient extends Thread{
		private SequenceNumber sn;
		
		public TestClient(SequenceNumber sn){
			this.sn = sn;
		}
		
		@Override
		public void run() {
			for(int i = 0; i < 3; i++){
				System.out.println(Thread.currentThread().getName()+"-----"+sn.getNextNum());
			}
		}
	}
	
	public int getNextNum(){
		seqNum.set(seqNum.get()+1);
		
		return seqNum.get();
	}
	
	/**
	 * 基于优先队列【priorityQueue】的排序，默认是按小到大，从大到小，需要实现comparator接口的compareTo方法:
	 * PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
	 * @param nums
	 */
	public static void sortByPriorityQueue(int nums[]){
		
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		
		for (int i = 0; i < nums.length; i++) {
			queue.add(nums[i]);
		}
		
		int i = 0;
		while(!queue.isEmpty()){
			
			nums[i++] = queue.poll();
		}
	}
	
	
	public static void main(String[] args) {
		SequenceNumber number = new SequenceNumber();
		
		TestClient testClient = new TestClient(number);
		TestClient testClient1 = new TestClient(number);
		TestClient testClient2 = new TestClient(number);
		
		
		testClient.start();
		testClient1.start();
		testClient2.start();
		
	}
}
