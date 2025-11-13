package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class IntegerSet  {
  private List<Integer> set = new ArrayList<Integer>();

  public void clear() {
    set.clear();
  }

  public int length() {
    return set.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IntegerSet)) return false;
    IntegerSet other = (IntegerSet) o;
    return this.set.size() == other.set.size()
        && this.set.containsAll(other.set)
        && other.set.containsAll(this.set);
  }

  public boolean contains(int value) {
    return set.contains(value);
  }

  public int largest()  {
    if (set.isEmpty()) {
      throw new IllegalStateException("Set is empty");
    }
    return Collections.max(set);
  }

  public int smallest()  {
    if (set.isEmpty()) {
      throw new IllegalStateException("Set is empty");
    }
    return Collections.min(set);
  }

  public void add(int item) {
    if (!set.contains(item)) {
      set.add(item);
    }
  }

  public void remove(int item) {
    set.remove(Integer.valueOf(item));
  }

  public void union(IntegerSet other) {
    for (Integer value : other.set) {
      if (!this.set.contains(value)) {
        this.set.add(value);
      }
    }
  }

  public void intersect(IntegerSet other) {
    set.retainAll(other.set);
  }

  public void diff(IntegerSet other) {
    set.removeAll(other.set);
  }

  public void complement(IntegerSet other) {
    List<Integer> result = new ArrayList<>(other.set);
    result.removeAll(this.set);
    this.set = result;
  }

  public boolean isEmpty() {
    return set.isEmpty();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < set.size(); i++) {
      sb.append(set.get(i));
      if (i < set.size() - 1) sb.append(", ");
    }
    sb.append("]");
    return sb.toString();
  }
}
