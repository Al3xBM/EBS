package sub;

import helpers.FieldGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubscriptionGenerator {
    private final SubscriptionGeneratorSetup setup;

    public SubscriptionGenerator(SubscriptionGeneratorSetup setup) {
        this.setup = setup;
    }

    public ISubscriptionField generateSubscription(String field, boolean mustBeEqual) {
        switch (field) {
            case "company":
                return new SubscriptionString(FieldGenerator.getRandomCompany(), mustBeEqual ? "=" : FieldGenerator.getRandomOperator());
            case "date":
                return new SubscriptionDate(FieldGenerator.getRandomDate(), mustBeEqual ? "=" : FieldGenerator.getRandomOperator());
            default:
                return new SubscriptionDouble(FieldGenerator.getRandomDouble(), mustBeEqual ? "=" : FieldGenerator.getRandomOperator(), field);
        }
    }

    public List<Subscription> generateSubscriptions() {
//        List<Subscription> subscriptions = new ArrayList<>();
//        List<SubscriptionString> companyFields = IntStream.rangeClosed(0, setup.getSubscriptionsNumber())
//                                                    .mapToObj(i -> (SubscriptionString)null)
//                                                    .collect(Collectors.toList());
//        List<SubscriptionDouble> valueFields = IntStream.rangeClosed(0, setup.getSubscriptionsNumber())
//                                                    .mapToObj(i -> (SubscriptionDouble)null)
//                                                    .collect(Collectors.toList());
//        List<SubscriptionDouble> dropFields = IntStream.rangeClosed(0, setup.getSubscriptionsNumber())
//                                                    .mapToObj(i -> (SubscriptionDouble)null)
//                                                    .collect(Collectors.toList());
//        List<SubscriptionDouble> varianceFields = IntStream.rangeClosed(0, setup.getSubscriptionsNumber())
//                                                    .mapToObj(i -> (SubscriptionDouble)null)
//                                                    .collect(Collectors.toList());
//        List<SubscriptionDate> dateFields = IntStream.rangeClosed(0, setup.getSubscriptionsNumber())
//                                                    .mapToObj(i -> (SubscriptionDate)null)
//                                                    .collect(Collectors.toList());
//
//        for (int i = 0; i < setup.getCompanyFields(); i++) {
//            if(i < setup.getCompanyEqualFields())
//                companyFields.set(i, (SubscriptionString)generateSubscription("company", true));
//            else
//                companyFields.set(i, (SubscriptionString)generateSubscription("company", false));
//        }
//
//        for (int i = 0; i < setup.getValueFields(); i++) {
//            if(i < setup.getValueEqualFields())
//                valueFields.set(i, (SubscriptionDouble)generateSubscription("value", true));
//            else
//                valueFields.set(i, (SubscriptionDouble)generateSubscription("value", false));
//        }
//
//        for (int i = 0; i < setup.getDropFields(); i++) {
//            if(i < setup.getDropEqualFields())
//                dropFields.set(i, (SubscriptionDouble)generateSubscription("drop", true));
//            else
//                dropFields.set(i, (SubscriptionDouble)generateSubscription("drop", false));
//        }
//
//        for (int i = 0; i < setup.getVariationFields(); i++) {
//            if(i < setup.getVariationEqualFields())
//                varianceFields.set(i, (SubscriptionDouble)generateSubscription("variation", true));
//            else
//                varianceFields.set(i, (SubscriptionDouble)generateSubscription("variation", false));
//        }
//
//        for (int i = 0; i < setup.getDateFields(); i++) {
//            if(i < setup.getDateEqualFields())
//                dateFields.set(i, (SubscriptionDate)generateSubscription("date", true));
//            else
//                dateFields.set(i, (SubscriptionDate)generateSubscription("date", false));
//        }
//
//        Collections.shuffle(companyFields);
//        Collections.shuffle(valueFields);
//        Collections.shuffle(dropFields);
//        Collections.shuffle(varianceFields);
//        Collections.shuffle(dateFields);

//        for (int i = 0; i < setup.getSubscriptionsNumber(); i++)
//            subscriptions.add(new Subscription(companyFields.get(i), valueFields.get(i), dropFields.get(i), varianceFields.get(i), dateFields.get(i)));

        List<ISubscriptionField> subscriptionFields = new ArrayList<>();

        for (int i = 0; i < setup.getCompanyFields(); i++) {
            if (i < setup.getCompanyEqualFields())
                subscriptionFields.add(generateSubscription("company", true));
            else
                subscriptionFields.add(generateSubscription("company", false));
        }

        for (int i = 0; i < setup.getValueFields(); i++) {
            if (i < setup.getValueEqualFields())
                subscriptionFields.add(generateSubscription("value", true));
            else
                subscriptionFields.add(generateSubscription("value", false));
        }

        for (int i = 0; i < setup.getDropFields(); i++) {
            if (i < setup.getDropEqualFields())
                subscriptionFields.add(generateSubscription("drop", true));
            else
                subscriptionFields.add(generateSubscription("drop", false));
        }

        for (int i = 0; i < setup.getVariationFields(); i++) {
            if (i < setup.getVariationEqualFields())
                subscriptionFields.add(generateSubscription("variation", true));
            else
                subscriptionFields.add(generateSubscription("variation", false));
        }

        for (int i = 0; i < setup.getDateFields(); i++) {
            if (i < setup.getDateEqualFields())
                subscriptionFields.add(generateSubscription("date", true));
            else
                subscriptionFields.add(generateSubscription("date", false));
        }

        List<Subscription> subscriptions = IntStream.rangeClosed(0, setup.getSubscriptionsNumber())
                .mapToObj(i -> new Subscription())
                .collect(Collectors.toList());
        int index = 0;
        Collections.shuffle(subscriptionFields);

        for (ISubscriptionField subscriptionField : subscriptionFields) {

            if (subscriptionField instanceof SubscriptionString) {
                if (subscriptions.get(index).getCompany() == null)
                    subscriptions.get(index).setCompany((SubscriptionString) subscriptionField);

            } else if (subscriptionField instanceof SubscriptionDate) {
                if (subscriptions.get(index).getDate() == null)
                    subscriptions.get(index).setDate((SubscriptionDate) subscriptionField);
            } else if (subscriptionField instanceof SubscriptionDouble) {
                switch (((SubscriptionDouble) subscriptionField).getFieldName()) {
                    case "value":
                        subscriptions.get(index).setValue((SubscriptionDouble) subscriptionField);
                        break;
                    case "drop":
                        subscriptions.get(index).setDrop((SubscriptionDouble) subscriptionField);
                        break;
                    case "variation":
                        subscriptions.get(index).setVariation((SubscriptionDouble) subscriptionField);
                        break;
                }
            }

            index = index >= subscriptions.size() - 1 ? 0 : index + 1;
        }

        return subscriptions;
    }
}
