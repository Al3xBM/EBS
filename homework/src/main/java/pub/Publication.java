package pub;

import java.util.Date;

public class Publication {
    private String company;
    private double value;
    private double drop;
    private double variation;
    private Date date;

    public Publication(String company, double value, double drop, double variation, Date date){
        this.company = company;
        this.value = value;
        this.drop = drop;
        this.variation = variation;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "company='" + company + '\'' +
                ", value=" + value +
                ", drop=" + drop +
                ", variation=" + variation +
                ", date=" + date +
                '}';
    }

    public String getCompany() {
        return company;
    }

    public double getValue() {
        return value;
    }

    public double getDrop() {
        return drop;
    }

    public double getVariation() {
        return variation;
    }

    public Date getDate() {
        return date;
    }
}
