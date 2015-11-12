package org.defence.tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 12.11.15.
 */
public class ActionLogger {
	private static ActionLogger instance = new ActionLogger();
	private static final String DIR = System.getProperty("user.dir") + "/logs";
	private static File logDir = new File(DIR);

	static {
		if (!logDir.exists()) {
			logDir.mkdir();
		}
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

	private ActionLogger() {
	}

	public static ActionLogger getInstance() {
		return instance;
	}

	private static void checkLogDirExistence() {
		if (!logDir.exists()) {
			logDir.mkdir();
		}
	}

	public static boolean out(String msg) {
		checkLogDirExistence();

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy");
		String dateFormat = format.format(now);

		String fileName = DIR + "/" + dateFormat + ".txt";
		String newRecord = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(new Date()) + ":\t" + msg + "\r\n";

		File dayLog = new File(fileName);

		if (!dayLog.exists()) {
			FileWriter writer = null;
			try {
				dayLog.createNewFile();
				writer = new FileWriter(fileName);
				writer.write(newRecord);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			RandomAccessFile writer = null;
			try {
				writer = new RandomAccessFile(fileName, "rw");
				writer.seek(dayLog.length());
				writer.write(("\r\n" + newRecord).getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}


		/*RandomAccessFile writer = null;
		try {
			writer = new RandomAccessFile(logFile, "rw");
			writer.seek(logFile.length());

			writer.write((msg + dateFormat.format(new Date()) + "\t" + msg + "\r\n").getBytes("Cp1251"));
//			writer.wri("\r\n" + dateFormat.format(new Date()) + "\t" + msg);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/

		return true;
	}
}
