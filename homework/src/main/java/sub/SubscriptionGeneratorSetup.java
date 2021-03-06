package sub;

import java.util.Properties;

public class SubscriptionGeneratorSetup {
    private final int subscriptionsNumber;
    private final int companyFields;
    private final int valueFields;
    private final int dropFields;
    private final int variationFields;
    private final int dateFields;
    private final int companyEqualFields;
    private final int valueEqualFields;
    private final int dropEqualFields;
    private final int variationEqualFields;
    private final int dateEqualFields;

    public SubscriptionGeneratorSetup(Properties properties){
        subscriptionsNumber = Integer.parseInt(properties.getProperty("subscriptionsNumber"));
        companyFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("companyWeight")));
        valueFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("valueWeight")));
        dropFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("dropWeight")));
        variationFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("variationWeight")));
        dateFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("dateWeight")));
        companyEqualFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("companyEqualWeight")), companyFields);
        valueEqualFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("valueEqualWeight")), valueFields);
        dropEqualFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("dropEqualWeight")), dropFields);
        variationEqualFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("variationEqualWeight")), variationFields);
        dateEqualFields = calculateNumberOfFields(Double.parseDouble(properties.getProperty("dateEqualWeight")), dateFields);
    }

    private int calculateNumberOfFields(double weight){
        return (int) Math.ceil(subscriptionsNumber * weight);
    }

    private int calculateNumberOfFields(double weight, int totalNumber) {
        return (int) Math.ceil(totalNumber * weight);
    }

    public int getSubscriptionsNumber() {
        return subscriptionsNumber;
    }

    public double getCompanyFields() {
        return companyFields;
    }

    public double getValueFields() {
        return valueFields;
    }

    public double getDropFields() {
        return dropFields;
    }

    public double getVariationFields() {
        return variationFields;
    }

    public double getDateFields() {
        return dateFields;
    }

    public double getCompanyEqualFields() {
        return companyEqualFields;
    }

    public double getValueEqualFields() {
        return valueEqualFields;
    }

    public double getDropEqualFields() {
        return dropEqualFields;
    }

    public double getVariationEqualFields() {
        return variationEqualFields;
    }

    public double getDateEqualFields() {
        return dateEqualFields;
    }
}
