package sub;

public class Subscription {
    private SubscriptionString company;
    private SubscriptionDouble value;
    private SubscriptionDouble drop;
    private SubscriptionDouble variation;
    private SubscriptionDate date;

    public Subscription(SubscriptionString company, SubscriptionDouble value, SubscriptionDouble drop, SubscriptionDouble variation, SubscriptionDate date)
    {
        this.company = company;
        this.value = value;
        this.drop = drop;
        this.variation = variation;
        this.date = date;
    }

    @Override
    public String toString() {
        String stringContent = "";

        if(company != null)
            stringContent += "company='" + company.toString() + '\'';

        if(value != null)
            stringContent += " value=" + value.toString();

        if(drop != null)
            stringContent += " drop=" + drop.toString();

        if(variation != null)
            stringContent += " variation=" + variation.toString();

        if(date != null)
            stringContent += " date=" + date.toString();

        return "Subscription{" +
                stringContent +
                '}';
    }

    public SubscriptionString getCompany() {
        return company;
    }

    public SubscriptionDouble getValue() {
        return value;
    }

    public SubscriptionDouble getDrop() {
        return drop;
    }

    public SubscriptionDouble getVariation() {
        return variation;
    }

    public SubscriptionDate getDate() {
        return date;
    }

}
