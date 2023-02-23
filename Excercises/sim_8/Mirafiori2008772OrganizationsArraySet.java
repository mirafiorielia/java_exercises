import java.util.Scanner;

public class Mirafiori2008772OrganizationsArraySet extends ArraySet {
    public void add(Object x) {
        if (!(x instanceof String)) {
            throw new IllegalArgumentException();
        }
        super.add(x);
    }

    public String[] sortOrganizations() {
        for (int i = 0; i < vSize; i++) {
            for (int j = 0; j < vSize; j++) {
                
            }
        }
    }
}
