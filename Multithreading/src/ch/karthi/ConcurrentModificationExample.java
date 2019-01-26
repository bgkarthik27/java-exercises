package ch.karthi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcurrentModificationExample {

	public static void main(String[] args) {
		
		List<String> namesList = new ArrayList<>();
//		List<String> namesList = new CopyOnWriteArrayList<>(); -> will not throw CME
		namesList.add("john");
		namesList.add("joe");
		
		new Thread(() ->  {
			try {
				namesList.forEach(str -> {
					System.out.println(str);
					sleep(1);
				});
			} catch (Exception e) {
				System.out.println("Reading Thread " +e);
			}
		}).start();
		
		new Thread(() ->  {
			try {
				sleep(1);
				namesList.add("karthi");
			} catch (Exception e) {
				System.out.println("Writing Thread " +e);
			}
		}).start();

	}

	private static void sleep(long secs) {
		try {
			TimeUnit.SECONDS.sleep(secs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
