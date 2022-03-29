package sub;

import java.util.Date;

// this and the other 2 subscription classes could be merged
// into a single class, where value is of type Object
public class SubscriptionDate {
    private Date value;
    private String operator;

    public SubscriptionDate(Date value, String operator)
    {
        this.value = value;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "SubscriptionDate{" +
                "value=" + value +
                ", operator='" + operator + '\'' +
                '}';
    }
}
