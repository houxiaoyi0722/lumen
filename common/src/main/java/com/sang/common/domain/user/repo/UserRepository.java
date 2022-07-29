package com.sang.common.domain.user.repo;

import com.sang.common.domain.user.entity.User;
import com.sang.common.domain.user.entity.query.QUser;
import com.sang.common.domain.user.param.UserQry;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * @author hxy
 * @date 2022/1/7 15:07
 **/
@Repository
public class UserRepository extends BeanRepository<Long, User> {

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
     * @param server The Database instance typically created via Spring factory or equivalent
     */
    protected UserRepository(Database server) {
        super(User.class, server);
    }


    public PagedList<User> userList(UserQry userQry) {
        QUser qUser = QUser.alias();
        return new QUser()
                .deleted.isFalse()
                .setFirstRow(userQry.getStartPosition())
                .setMaxRows(userQry.getEndPosition())
                .orderBy().id.asc()
                .findPagedList();
    }

    public UserDetails loadUserByUsername(String username) {
        return new QUser().deleted.isFalse().userName.eq(username).findOne();
    }
}
