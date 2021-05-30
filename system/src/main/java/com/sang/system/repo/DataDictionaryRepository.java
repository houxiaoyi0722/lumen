package com.sang.system.repo;

import com.sang.system.domain.entity.DataDictionary;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

@Repository
public class DataDictionaryRepository extends BeanRepository<Long, DataDictionary> {

    /**
     * Create with the given bean type and Database instance.
     * <p>
     * Typically users would extend BeanRepository rather than BeanFinder.
     * </p>
     * <pre>{@code
     *
     *   @Inject
     *   public CustomerRepository(Database server) {
     *     super(Customer.class, server);
     *   }
     *
     * }</pre>
     *
     * @param type   The bean type
     * @param server The Database instance typically created via Spring factory or equivalent
     */
    protected DataDictionaryRepository(Class<DataDictionary> type, Database server) {
        super(type, server);
    }
}
