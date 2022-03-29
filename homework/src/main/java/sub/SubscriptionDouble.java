package sub;

public class SubscriptionDouble {
    private Double value;
    private String operator;

    public SubscriptionDouble(Double value, String operator)
    {
        this.value = value;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "SubscriptionDouble{" +
                "value=" + value +
                ", operator='" + operator + '\'' +
                '}';
    }
}
