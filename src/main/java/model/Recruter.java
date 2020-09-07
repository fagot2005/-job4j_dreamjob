package model;

public class Recruter {
    private String name;
    private String vacancy;

    public Recruter(String name, String vacancy) {
        this.name = name;
        this.vacancy = vacancy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }
}
