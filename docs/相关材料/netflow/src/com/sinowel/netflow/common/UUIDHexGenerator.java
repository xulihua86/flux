package com.sinowel.netflow.common;

import java.net.InetAddress;

public class UUIDHexGenerator {

	private String sep = "";

	private static final int IP;

	private static short counter = (short) 0;

	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private static UUIDHexGenerator uuidgen = null;

	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	static synchronized UUIDHexGenerator getInstance() {
		if (uuidgen == null)
			uuidgen = new UUIDHexGenerator();
		return uuidgen;
	}

	private static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	private String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	private int getJVM() {
		return JVM;
	}

	private synchronized short getCount() {
		if (counter < 0) {
			counter = 0;
		}
		return counter++;
	}

	private int getIP() {
		return IP;
	}

	private short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	private int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(format(getJVM())).append(sep).append(format(getHiTime())).append(sep)
				.append(format(getLoTime())).append(sep).append(format(getCount())).toString();
	}
}
