package ch.karthi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class ConcurrentModificatonExceptionInList {

	public static void main(String[] args) {

		Log<String> log = System.out::println;

		//This will throw ConcurrentModificationException
		List<String> namesList = new ArrayList<>();
		//List<String> namesList = new CopyOnWriteArrayList<>(); -> If we use this, the CME will not be thrown. 
		namesList.add("hari");
		namesList.add("karthi");
		namesList.add("ganesh");

		//This will throw ConcurrentModificationException
		new Thread(() -> {
			namesList.forEach(str -> {
				sleep(2);
				log.log("reading...");
				log.log(str);
			});
		}).start();

		new Thread(() -> {
			namesList.add("surya");
			log.log("writing going to sleep...");
			sleep(5);
			log.log("writing woke up...");
			namesList.add("harish");
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
