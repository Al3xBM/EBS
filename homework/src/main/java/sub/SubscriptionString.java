package sub;

public class SubscriptionString implements ISubscriptionField {
    private final String value;
    private final String operator;

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
