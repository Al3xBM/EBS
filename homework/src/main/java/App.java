import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pub.Publication;
import pub.PublicationGenerator;
import sub.Subscription;
import sub.SubscriptionGenerator;
import sub.SubscriptionGeneratorSetup;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

public class App {
    public static final String PUBLICATION_PATH         =   "PUBLICATION-";
    public static final String SUBSCRIPTON_PATH         =   "SUBSCRIPTON-";
    public static int SUBSCRIPTION_CHUNK_SIZE;
    public static int THREADS_NUMBER;

    public static void main(String[] args) throws Exception {
        while (true) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("enter an integer");
            int myint = keyboard.nextInt();

            if (args.length > 0) {
                try {
                    THREADS_NUMBER = Integer.parseInt(args[0]);
                    if (THREADS_NUMBER <= 0)
                        throw new Exception("Number of threads must be greater than 0.");
                    SUBSCRIPTION_CHUNK_SIZE = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.err.println("Argument 0" + args[0] + " must be an integer.");
                    System.err.println("Argument " + args[1] + " must be an integer.");
                    System.exit(1);
                }
            } else
                throw new Exception("No command line args. Please insert.");

            System.out.println("Hello World!");
            System.out.println("Threads number = " + THREADS_NUMBER);
            System.out.println("Multi-threaded? " + (THREADS_NUMBER != 1 ? "yes" : "no"));
            if (THREADS_NUMBER != 1)
                System.out.println("Subscription chunk size = " + SUBSCRIPTION_CHUNK_SIZE);

            try (InputStream input = App.class.getClassLoader().getResourceAsStream("input.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                PublicationGenerator publicationGenerator = new PublicationGenerator();
                SubscriptionGeneratorSetup subscriptionGeneratorSetup = new SubscriptionGeneratorSetup(prop);
                SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(subscriptionGeneratorSetup, THREADS_NUMBER, SUBSCRIPTION_CHUNK_SIZE);

                // publications
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (Publication pub : publicationGenerator.generatePublications(Integer.parseInt(prop.getProperty("publicationsNumber")))) {
                    sb.append(gson.toJson(pub)).append(",\n");
                }
                sb.delete(sb.length() - 2, sb.length()).append("]\n");

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(PUBLICATION_PATH + UUID.randomUUID().toString() + ".json"), StandardCharsets.UTF_16))) {
                writer.write(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

                // subscription
                gson = new GsonBuilder().setPrettyPrinting().create();
                sb = new StringBuilder();
                sb.append("[");
                for (Subscription sub : subscriptionGenerator.generateSubscriptions()) {
                    sb.append(gson.toJson(sub)).append(",\n");
                    if (sub.getCompany() == null && sub.getValue() == null && sub.getDrop() == null && sub.getVariation() == null && sub.getDate() == null)
                        break;
                }
                sb.delete(sb.length() - 2, sb.length()).append("]\n");

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(SUBSCRIPTON_PATH + UUID.randomUUID().toString() + ".json"), StandardCharsets.UTF_16))) {
                writer.write(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
