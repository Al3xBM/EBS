package sub;

import helpers.FieldGenerator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubscriptionGenerator {
    private final SubscriptionGeneratorSetup setup;
    private final List<ISubscriptionField> subscriptionFields = new ArrayList<>();
    private static List<Subscription> subscriptions;
    private final boolean threaded;
    private final int threads;
    private final int chunk_size;
    private static int index = 0;
    private static double subscriptionGenerationTime = 0;

    public SubscriptionGenerator(SubscriptionGeneratorSetup setup, int threads, int chunk_size) {
        this.setup = setup;
        this.threaded = threads > 1;
        this.threads = threads;
        this.chunk_size = chunk_size;

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

    public List<Subscription> generateSubscriptions() {
        long realExecTime = System.nanoTime();
        generateCompanies();
        generateValues();
        generateVariation();
        generateDrop();
        generateDate();

        double totalExecTime = (System.nanoTime() - realExecTime) / 1000000.0;
        System.out.println("Fields generation time = " + totalExecTime);

        Collections.shuffle(subscriptionFields);
        generateSubscription();
        System.out.println("Main thread execution time = " + (System.nanoTime() - realExecTime) / 1000000.0);
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
        long exec = System.nanoTime();
        int subscriptionsLength = subscriptionFields.size();
        if (threaded) {
            System.out.println("Threads number = " + (subscriptionsLength / chunk_size + 1));
            ExecutorService executor = Executors.newFixedThreadPool(subscriptionsLength / chunk_size + 1);
            try {
                for (int subsIndex = 0; subsIndex < subscriptionsLength; subsIndex += chunk_size) {
                    final int start = subsIndex;
                    final Runnable runnable = new ExecutorWorker(subscriptionFields, subsIndex, chunk_size);
                    executor.submit(runnable);
                }
            } finally {
                executor.shutdown();
            }
        } else {
            new ExecutorWorker(subscriptionFields, 0, subscriptionsLength).run();
        }
        System.out.println("Subscription generation time = " + (System.nanoTime() - exec) / 1000000.0);
    }

    private static class ExecutorWorker implements Runnable {
        private final List<ISubscriptionField> subscriptionFields;
        private final int offset;
        private final int toProcess;

        public ExecutorWorker(List<ISubscriptionField> subscriptionFields, int offset, int toProcess) {
            this.subscriptionFields = subscriptionFields;
            this.offset = offset;
            this.toProcess = toProcess;
        }

        @Override
        public void run() {
            long exec = System.nanoTime();
            int end = offset + toProcess;
            if (end > subscriptionFields.size())
                end = subscriptionFields.size();

//            System.out.println("start = " + offset + "; to process = " + end);
            for (int j = offset; j < end; j++) {
                ISubscriptionField subscriptionField = subscriptionFields.get(j);

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
//            double execTime = (System.nanoTime() - exec) / 1000000.0;
//			System.out.println("Chunk exec time = " + execTime);
        }
    }
}

/**
 //            List<Thread> threads = new ArrayList<>();
 //            threads.add(new Thread(this::generateCompanies, "companyThread"));
 //            threads.add(new Thread(this::generateValues, "valuesThread"));
 //            threads.add(new Thread(this::generateVariation, "variationThread"));
 //            threads.add(new Thread(this::generateDrop, "dropThread"));
 //            threads.add(new Thread(this::generateDate, "dateThread"));
 //
 //            for (Thread thread : threads) {
 //                long startTime = System.nanoTime();
 //                thread.start();
 //                totalExecTime += (System.nanoTime() - startTime) / 1000000.0;
 //                System.out.println("Execution time of thread " + thread.getName() + " = " + (System.nanoTime() - startTime) / 1000000.0);
 //            }
 //
 //            for (Thread thread : threads)
 //                thread.join();
 */