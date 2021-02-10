package io.gitee.xujiayao147.ideeaProject.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SHA256 {

	public static String getSHA256(String str) {
		MessageDigest messageDigest;
		String encodeStr;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
			encodeStr = byteToHex(messageDigest.digest());

			return encodeStr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String byteToHex(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder();
		String temp;

		for (byte tempByte : bytes) {
			temp = Integer.toHexString(tempByte & 0xFF);

			if (temp.length() == 1)
				stringBuilder.append("0");

			stringBuilder.append(temp);
		}

		return stringBuilder.toString();
	}
}
