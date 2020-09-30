package springboot.parameters;

/**
 * Created by ilya on Sep, 2020
 */
public enum Periods {
    DAILY("TIME_SERIES_DAILY"), WEEKLY("TIME_SERIES_WEEKLY"), MONTHLY("TIME_SERIES_MONTHLY"), YEARLY("TIME_SERIES_YEARLY");

    private String period;

    private Periods(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return this.period;
    }
}
