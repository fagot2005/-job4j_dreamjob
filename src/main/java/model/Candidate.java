package model;

public class Candidate {
    private String name;
    private String rezume;

    public Candidate(String name, String rezume) {
        this.name = name;
        this.rezume = rezume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRezume() {
        return rezume;
    }

    public void setRezume(String rezume) {
        this.rezume = rezume;
    }
}
