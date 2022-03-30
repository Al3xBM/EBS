import pub.Publication;
import pub.PublicationGenerator;
import sub.Subscription;
import sub.SubscriptionGenerator;
import sub.SubscriptionGeneratorSetup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        try(InputStream input = App.class.getClassLoader().getResourceAsStream("input.properties")){
            Properties prop = new Properties();
            prop.load(input);
            PublicationGenerator publicationGenerator = new PublicationGenerator();
            SubscriptionGeneratorSetup subscriptionGeneratorSetup = new SubscriptionGeneratorSetup(prop);
            SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(subscriptionGeneratorSetup);

            for(Publication pub : publicationGenerator.generatePublications(Integer.parseInt(prop.getProperty("publicationsNumber")))){
                System.out.println(pub);
            }

            for(Subscription sub : subscriptionGenerator.generateSubscriptions()){
                System.out.println(sub);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
