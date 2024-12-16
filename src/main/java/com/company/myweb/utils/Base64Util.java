package com.company.myweb.utils;

import java.util.Base64;

public class Base64Util {
    public static byte[] convertBase64(String encodedString) {
        return Base64.getDecoder().decode(encodedString);
    }

    public static String decodedBased64(String originalString) {
        return Base64.getEncoder().encodeToString(originalString.getBytes());
    }
}
