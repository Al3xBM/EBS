package sub;

public class SubscriptionString implements ISubscriptionField {
    private String value;
    private String operator;

    public SubscriptionString(String value, String operator)
    {
        this.value = value;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "SubscriptionString{" +
                "value='" + value + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
