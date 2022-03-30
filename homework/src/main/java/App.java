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
import java.util.UUID;

public class App {
    public static final String PUBLICATION_PATH = "PUBLICATION-";
    public static final String SUBSCRIPTON_PATH = "SUBSCRIPTON-";

    public static void main(String[] args) {
        System.out.println("Hello World!");

        try (InputStream input = App.class.getClassLoader().getResourceAsStream("input.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PublicationGenerator publicationGenerator = new PublicationGenerator();
            SubscriptionGeneratorSetup subscriptionGeneratorSetup = new SubscriptionGeneratorSetup(prop);
            SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(subscriptionGeneratorSetup, true);

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

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
