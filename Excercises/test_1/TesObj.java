package test_1;

import java.util.ArrayList;

public class TesObj {
    private String name;
    private ArrayList<Object> array;

    public TesObj() {
        System.out.println("CONSTRUCTOR");
    }

    public TesObj(ArrayList<Object> array, String name) {
        System.out.println("CONSTRUCTOR 2");
        setArray(array);
        setName(name);
    }

    public void setArray(ArrayList<Object> array) {
        this.array = array;
    }

    public ArrayList<Object> getArray() {
        return array;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
