package com.akhambir.rps.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class BaseUtils {

  public static Timestamp getSqlTimestamp() {
    var now = LocalDateTime.now(ZoneOffset.UTC);
    return Timestamp.valueOf(now);
  }

}
