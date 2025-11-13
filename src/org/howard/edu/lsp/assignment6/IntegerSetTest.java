package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IntegerSetTest {

  @Test
  public void testAddContainsLength() {
    IntegerSet set = new IntegerSet();
    assertTrue(set.isEmpty());
    set.add(1);
    set.add(2);
    set.add(2);
    assertEquals(2, set.length());
    assertTrue(set.contains(1));
    assertFalse(set.contains(3));
  }

  @Test
  public void testClear() {
    IntegerSet set = new IntegerSet();
    set.add(10);
    set.add(20);
    set.clear();
    assertTrue(set.isEmpty());
  }

  @Test
  public void testEquals() {
    IntegerSet a = new IntegerSet();
    IntegerSet b = new IntegerSet();
    a.add(1);
    a.add(2);
    b.add(2);
    b.add(1);
    assertTrue(a.equals(b));
  }

  @Test
  public void testLargestSmallest() {
    IntegerSet set = new IntegerSet();
    set.add(5);
    set.add(20);
    set.add(1);
    assertEquals(20, set.largest());
    assertEquals(1, set.smallest());
  }

  @Test
  public void testLargestSmallestThrows() {
    IntegerSet set = new IntegerSet();
    assertThrows(IllegalStateException.class, () -> set.largest());
    assertThrows(IllegalStateException.class, () -> set.smallest());
  }

  @Test
  public void testRemove() {
    IntegerSet set = new IntegerSet();
    set.add(1);
    set.add(2);
    set.add(3);
    set.remove(2);
    assertFalse(set.contains(2));
  }

  @Test
  public void testUnion() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1);
    s1.add(2);
    s2.add(2);
    s2.add(3);
    s1.union(s2);
    assertEquals(3, s1.length());
  }

  @Test
  public void testIntersect() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1);
    s1.add(2);
    s1.add(3);
    s2.add(2);
    s2.add(5);
    s1.intersect(s2);
    assertEquals(1, s1.length());
    assertTrue(s1.contains(2));
  }

  @Test
  public void testDiff() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1);
    s1.add(2);
    s1.add(3);
    s2.add(2);
    s1.diff(s2);
    assertFalse(s1.contains(2));
  }

  @Test
  public void testComplement() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1);
    s1.add(2);
    s2.add(1);
    s2.add(2);
    s2.add(3);
    s2.add(4);
    s1.complement(s2);
    assertTrue(s1.contains(3));
    assertTrue(s1.contains(4));
  }

  @Test
  public void testToString() {
    IntegerSet set = new IntegerSet();
    set.add(5);
    set.add(10);
    assertEquals("[5, 10]", set.toString());
  }
}
