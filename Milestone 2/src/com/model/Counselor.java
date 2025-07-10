class Counselor {
    private String name;
    private String specialization;
    private boolean availability;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return this.specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void changeAvailability(boolean availability) {
        switch (this.availability) {
            case true -> this.availability = false;
            case false -> this.availability = true
        }
    }
}