package models;

import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import java.util.List;

public class DebugEMcreator {

    @Test
    public void EMTest(){
        String persistenceUnitName = "Hibernate-Persistence";

        EntityManagerFactory emf = null;
        PersistenceProviderResolver resolver = PersistenceProviderResolverHolder.getPersistenceProviderResolver();
        System.out.println("resolver : " + resolver);

        List<PersistenceProvider> providers = resolver.getPersistenceProviders();
        System.out.println("providers : " + providers.size());

        for (PersistenceProvider provider : providers) {
            System.out.println("provider name: " + provider);
            emf = provider.createEntityManagerFactory(persistenceUnitName, null);
            if (emf != null) {
                break;
            }
        }
        if (emf == null) {
            throw new PersistenceException("No Persistence provider for EntityManager named " + persistenceUnitName);
        }
    }
}
