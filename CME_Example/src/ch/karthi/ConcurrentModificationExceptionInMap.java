package ch.karthi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ConcurrentModificationExceptionInMap {

	public static void main(String[] args) {

		Log<String> log = System.out::println;

		//This will throw ConcurrentModificationException
		//Map<String,String> namesList = new HashMap<>();
		Map<String,String> namesList = new ConcurrentHashMap<>(); //-> If we use this, the CME will not be thrown. 
		namesList.put("hari", "one");
		namesList.put("karthi", "two");
		namesList.put("ganesh", "three");

		//This will throw ConcurrentModificationException
		new Thread(() -> {
			namesList.forEach((k,v) -> {
				sleep(2);
				log.log("reading...");
				log.log(v);
			});
		}).start();

		new Thread(() -> {
			namesList.put("surya", "four");
			log.log("writing going to sleep...");
			sleep(5);
			log.log("writing woke up...");
			namesList.put("harish","five");
		}).start();

		log.log("main thread completed");

	}

	private static void sleep(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	interface Log<T> {
		void log(T str);
	}
}
