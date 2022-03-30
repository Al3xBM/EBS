package sub;

import helpers.FieldGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubscriptionGenerator {
    private final SubscriptionGeneratorSetup setup;
    private final List<ISubscriptionField> subscriptionFields = new ArrayList<>();
    private final List<Subscription> subscriptions;
    private final boolean threaded;

    public SubscriptionGenerator(SubscriptionGeneratorSetup setup, boolean threaded) {
        this.setup = setup;
        this.threaded = threaded;

        subscriptions = IntStream.rangeClosed(0, setup.getSubscriptionsNumber())
                .mapToObj(i -> new Subscription())
                .collect(Collectors.toList());
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

    public List<Subscription> generateSubscriptions() throws InterruptedException {
        long realExecTime = System.nanoTime();
        double totalExecTime = 0.;
        if (threaded) {
            List<Thread> threads = new ArrayList<>();
            threads.add(new Thread(this::generateCompanies, "companyThread"));
            threads.add(new Thread(this::generateValues, "valuesThread"));
            threads.add(new Thread(this::generateVariation, "variationThread"));
            threads.add(new Thread(this::generateDrop, "dropThread"));
            threads.add(new Thread(this::generateDate, "dateThread"));

            for (Thread thread : threads) {
                long startTime = System.nanoTime();
                thread.start();
                totalExecTime += (System.nanoTime() - startTime) / 1000000.0;
                System.out.println("Execution time of thread " + thread.getName() + " = " + (System.nanoTime() - startTime) / 1000000.0);
            }

            for (Thread thread : threads)
                thread.join();
        } else {
            long startTime = System.nanoTime();

            generateCompanies();
            generateValues();
            generateVariation();
            generateDrop();
            generateDate();

            totalExecTime += (System.nanoTime() - startTime) / 1000000.0;
        }
        System.out.println("Total generation time = " + totalExecTime);

        Collections.shuffle(subscriptionFields);
        generateSubscriptionThreaded();
        System.out.println("Real generation time = " + (System.nanoTime() - realExecTime) / 1000000.0);
        return subscriptions;
    }

    private void generateCompanies() {
        double threshold = setup.getCompanyEqualFields();
        for (int i = 0; i < setup.getCompanyFields(); i++)
            subscriptionFields.add(generateSubscription("company", i < threshold));
    }

    private void generateValues() {
        double threshold = setup.getValueEqualFields();
        for (int i = 0; i < setup.getValueFields(); i++)
            subscriptionFields.add(generateSubscription("value", i < threshold));
    }

    private void generateDrop() {
        double threshold = setup.getDropEqualFields();
        for (int i = 0; i < setup.getDropFields(); i++)
            subscriptionFields.add(generateSubscription("drop", i < threshold));
    }

    private void generateVariation() {
        double threshold = setup.getVariationEqualFields();
        for (int i = 0; i < setup.getVariationFields(); i++)
            subscriptionFields.add(generateSubscription("variation", i < threshold));
    }

    private void generateDate() {
        double threshold = setup.getDateEqualFields();
        for (int i = 0; i < setup.getDateFields(); i++)
            subscriptionFields.add(generateSubscription("date", i < threshold));
    }

    private void generateSubscription() {
        int index = 0;
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
    }

    private void generateSubscriptionThreaded() {
        long startTime = System.nanoTime();
        if (threaded) {
            new Thread(this::generateSubscription).start();
        } else {
            generateSubscription();
        }
        System.out.println("Subscription generation time = " + (System.nanoTime() - startTime) / 1000000.0);
    }
}
