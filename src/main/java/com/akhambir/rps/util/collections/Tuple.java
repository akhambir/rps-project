package com.akhambir.rps.util.collections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tuple<T> {
  private T v1;
  private T v2;

  public static <T> Tuple<T> of(T t1, T t2) {
    return new Tuple<>(t1, t2);
  }
}
