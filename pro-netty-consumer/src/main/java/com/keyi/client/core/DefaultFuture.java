package com.keyi.client.core;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.keyi.client.param.ClientRequest;
import com.keyi.client.param.Response;

public class DefaultFuture {

	public final static ConcurrentHashMap<Long, DefaultFuture> allDefaultFuture = new ConcurrentHashMap<>();
	final Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private Response response;
	private long timeout = 2 * 60 * 1000;
	private long startTime = System.currentTimeMillis();

	public Response getResponse() {
		return response;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public DefaultFuture(ClientRequest request) {
		allDefaultFuture.put(request.getId(), this);

	}

	// 主线程获得数据，首先要等待资源
	public Response get() {
		lock.lock();
		try {
			while (!done()) {
				condition.await();
			}
		} catch (Exception e) {

		} finally {
			lock.unlock();
		}

		return this.response;
	}

	public Response get(long time) {
		lock.lock();
		try {
			while (!done()) {
				condition.await(time, TimeUnit.SECONDS);
				if (System.currentTimeMillis() - startTime > time) {
					System.out.println("请求超时！");
					break;
				}
			}
		} catch (Exception e) {

		} finally {
			lock.unlock();
		}

		return this.response;
	}

	public static void receive(Response response) {
		DefaultFuture df = allDefaultFuture.get(response.getId());
		if (df != null) {
			Lock lock = df.lock;
			lock.lock();
			try {
				df.setResponse(response);
				df.condition.signal();
				allDefaultFuture.remove(df);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	private boolean done() {
		if (this.response != null) {
			return true;
		}
		return false;

	}

	// 清理线程
	static class FutureThread extends Thread {

		@Override
		public void run() {
			Set<Long> ids = allDefaultFuture.keySet();
			for (Long id : ids) {
				DefaultFuture df = allDefaultFuture.get(id);
				if (df == null) {
					allDefaultFuture.remove(df);
				} else {
					// 链路超时
					if (df.getTimeout() < (System.currentTimeMillis() - df.getStartTime())) {
						Response resp = new Response();
						resp.setId(id);
						resp.setCode("33333");
						resp.setMsg("链路请求超时");
						receive(resp);
					}
				}
			}
		}

	}
	
	static {
		FutureThread futureThread = new FutureThread();
		futureThread.setDaemon(true);
		futureThread.start();
	}

}
