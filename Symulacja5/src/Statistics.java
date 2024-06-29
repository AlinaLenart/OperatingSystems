public class Statistics {
    private int queries;
    private int migrations;
    private int overloads;

    public Statistics() {
        this.queries = 0;
        this.migrations = 0;
        this.overloads = 0;
    }

    public void incrementQueries() {
        queries++;
    }

    public void incrementMigrations() {
        migrations++;
    }
    public void incrementOverloads() {
        overloads++;
    }

    public int getQueries() {
        return queries;
    }

    public int getMigrations() {
        return migrations;
    }
    public int getOverloads() {
        return overloads;
    }
}
