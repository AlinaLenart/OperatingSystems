public class Statistics {
    private int queries;
    private int migrations;

    public Statistics() {
        this.queries = 0;
        this.migrations = 0;
    }

    public void incrementQueries() {
        queries++;
    }

    public void incrementMigrations() {
        migrations++;
    }

    public int getQueries() {
        return queries;
    }

    public int getMigrations() {
        return migrations;
    }
}
