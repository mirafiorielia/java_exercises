import java.util.*;

public class ArraySet implements Set {
   protected Object[] v;
   protected int vSize;
   private static int INITSIZE = 1;

   // costruttore
   public ArraySet() {
      v = new Object[INITSIZE];
      vSize = 0;
   }

   // metodi di container
   public void makeEmpty() {
      vSize = 0;
   }

   public boolean isEmpty() {
      return (vSize == 0);
   }

   public void add(Object x) // prestazioni O(n)
   {
      if (contains(x)) { // O(n) ricerca sequenziale
         return;
      }
      if (vSize == v.length) {
         v = resize(2 * vSize);
      }
      v[vSize] = x;
      vSize++;
   }

   public boolean contains(Object x) // prestaz. O(n)
   {
      for (int i = 0; i < vSize; i++) {
         if (v[i].equals(x)) {
            return true;
         }
      }
      return false;
   }

   public Object[] toArray() // metodo sovrascritto
   {
      Object[] x = new Object[vSize];
      System.arraycopy(v, 0, x, 0, vSize);
      return x;
   }

   protected Object[] resize(int newLength) // metodo ausiliario
   {
      Object[] newv = new Object[newLength];
      System.arraycopy(v, 0, newv, 0, v.length);
      return newv;
   }

}