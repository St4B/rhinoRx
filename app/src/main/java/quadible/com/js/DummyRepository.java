package quadible.com.js;

public class DummyRepository implements IQueryExecutor {

    @Override
    public String[][] query(String select) {
        return new String[][] {
                {"My", "name", "is", "Popi"},
                {"like", "my", "grandmother's", "Kaliopi"}
        };
    }
}
