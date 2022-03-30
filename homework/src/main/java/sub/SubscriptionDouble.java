package sub;

public class SubscriptionDouble implements ISubscriptionField {
    private Double value;
    private String operator;
    private String fieldName;

    public SubscriptionDouble(Double value, String operator, String fieldName)
    {
        this.value = value;
        this.operator = operator;
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "SubscriptionDouble{" +
                "value=" + value +
                ", operator='" + operator + '\'' +
                '}';
    }

    public Double getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }

    public String getFieldName() {
        return fieldName;
    }
}
