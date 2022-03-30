package helpers;

import java.util.*;

public class FieldGenerator {
    public static List<String> companies = Arrays.asList("Google", "Bing", "Yahoo", "Fluentis", "Mind", "Amazon", "Conduent", "Continental", "Bitdefender");
    public static List<Date> dates = Arrays.asList(new Date(2022, 3, 30), new Date(2021, 2, 11), new Date(2022, 4, 1),
            new Date(2011, 4, 20), new Date(2000, 0, 1));
    public static List<String> operators = Arrays.asList("=", ">", "<", ">=", "<=", "!=");
    public static Random rng = new Random();
    public static int rangeMin = 90;
    public static int rangeMax = 99;

    public static double getRandomDouble(){
        return rangeMin + (rangeMax - rangeMin) * rng.nextDouble();
    }

    public static String getRandomCompany(){
        return companies.get(rng.nextInt(companies.size()));
    }

    public static Date getRandomDate(){
        return dates.get(rng.nextInt(dates.size()));
    }

    public static String getRandomOperator(){
        return operators.get(rng.nextInt(operators.size()));
    }
}
