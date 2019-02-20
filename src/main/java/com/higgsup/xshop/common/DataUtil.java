package com.higgsup.xshop.common;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class DataUtil {

  public static String removeAccent(String input) {
    String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(temp).replaceAll("");
  }
}
