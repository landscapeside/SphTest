package com.landside.sphtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MockFileReader {

  public static String getFileAsString(InputStream inputStream) throws
      IOException {
    StringBuilder sbJson = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        sbJson.append(line);
      }
    } finally {
      try {
        reader.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return sbJson.toString();
  }
}
