package farm.model;

public enum Season {
    SPRING("spring"),
    SUMMER("summer"),
    AUTUMN("autumn"),
    WINTER("winter");

    public static final int DAYS_PER_SEASON = 15;

    private final String name;

    Season(String name) {
        this.name = name;
    }

    public static Season forDay(int day) {
        if (day < 1) {
            throw new IllegalArgumentException("Day must be positive.");
        }
        int completedSeasons = (day - 1) / DAYS_PER_SEASON;
        Season[] seasons = values();
        int seasonIndex = completedSeasons % seasons.length;
        return seasons[seasonIndex];
    }

    public int growthBonusFor(Season preferredSeason) {
        if (this == preferredSeason) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
