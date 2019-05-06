package part1;

import java.util.Objects;

public class Beer {
    private String name;
    private int alcoholPercentage;
    private String country;

    public Beer() {
    }

    public Beer(String name, int alcoholPercentage, String country) {
        this.name = name;
        this.alcoholPercentage = alcoholPercentage;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public int getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlcoholPercentage(int alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return alcoholPercentage == beer.alcoholPercentage &&
                name.equals(beer.name) &&
                country.equals(beer.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alcoholPercentage, country);
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", alcoholPercentage=" + alcoholPercentage +
                ", country='" + country + '\'' +
                '}';
    }


}
