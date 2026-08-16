package org.ide.andriod;

import java.io.File;
import java.util.Random;

public class ProjectIdGenerator {

	public static String generate(String dataDir) {
		Random random = new Random();
		String id;
		int serial = 0;
		do {
			int base = 10000000 + random.nextInt(90000000);
			serial++;
			id = String.valueOf(base) + (serial > 1 ? String.valueOf(serial) : "");
			id = id.substring(0, Math.min(id.length(), 8));
		} while (exists(dataDir, id));
		return id;
	}

	private static boolean exists(String dataDir, String id) {
		File f = new File(dataDir, id);
		return f.exists();
	}
}