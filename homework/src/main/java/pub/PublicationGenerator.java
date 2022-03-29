package pub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PublicationGenerator {
    private Random rng;
    private int rangeMin = 1;
    private int rangeMax = 99;

    public PublicationGenerator() {
        rng = new Random();
    }

    public List<Publication> generatePublications(){
        List<Publication> publications = new ArrayList<>();

        for (int i = 0; i < 10; ++i)
            publications.add(generatePublication());

        return publications;
    }

    public Publication generatePublication(){
        return new Publication(
                getRandomCompany(),
                rangeMin + (rangeMax - rangeMin) * rng.nextDouble(),
                rangeMin + (rangeMax - rangeMin) * rng.nextDouble(),
                rangeMin + (rangeMax - rangeMin) * rng.nextDouble(),
                getRandomDate()
        );
    }

    public String getRandomCompany(){
        return "DummyCompany";
    }

    public Date getRandomDate(){
        return new Date();
    }
}
