package com.executor.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;




class CustomRejectHandler implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("Task rejected: " +r.toString());
		
	}
	
}

class CustomThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
		
		Thread th= new Thread(r);
		
		th.setPriority(Thread.NORM_PRIORITY);
		th.setDaemon(false);
		
		
		return th;
	}
	
}

public class Main {

	public static void main(String[] args) {
		ThreadPoolExecutor tpex= new ThreadPoolExecutor(2, 4,10 , 
				TimeUnit.MINUTES, new ArrayBlockingQueue<>(2),new CustomThreadFactory(),
				new CustomRejectHandler());
		
		for(int i=0;i<8;i++) {
			tpex.submit(()->{
				try {
				Thread.sleep(5000);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("Task processed by : " +Thread.currentThread().getName());
			});
		}
		tpex.shutdown();
	}

}
