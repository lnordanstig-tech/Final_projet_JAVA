package farm.animals;

public enum AnimalType {
    ASHWINDER,
    HIPPOGRIFF,
    SHEEP;

    public Animal createAnimal() {
        switch (this) {
            case ASHWINDER:
                return new Ashwinder();
            case HIPPOGRIFF:
                return new Hippogriff();
            case SHEEP:
                return new Sheep();
            default:
                throw new IllegalStateException("Unknown Hogwart animal type.");
        }
    }
}
