package farm.core;

import farm.model.Season;

public final class Calendar {
    private final int maximumDays;
    private int completedDays;

    public Calendar(int maximumDays) {
        if (maximumDays < 1) {
            throw new IllegalArgumentException("The day limit must be positive.");
        }
        this.maximumDays = maximumDays;
    }

    public int getDay() {
        if (isFinished()) {
            return maximumDays;
        }
        return completedDays + 1;
    }

    public int getCompletedDays() {
        return completedDays;
    }

    public int getMaximumDays() {
        return maximumDays;
    }

    public Season getSeason() {
        return Season.forDay(getDay());
    }

    public boolean isFinished() {
        return completedDays >= maximumDays;
    }

    public void advance() {
        if (!isFinished()) {
            completedDays++;
        }
    }
}
