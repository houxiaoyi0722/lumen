package com.sang.common.domain.auth.authorization.user.repo;

import com.sang.common.domain.auth.authorization.user.entity.User;
import com.sang.common.domain.auth.authorization.user.entity.query.QUser;
import com.sang.common.domain.auth.authorization.user.param.UserQry;
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


    private final QUser qUser = QUser.alias();


    public PagedList<User> userList(UserQry userQry) {
        return new QUser()
                .select(qUser.id, qUser.userName, qUser.name, qUser.enabled, qUser.whenCreated, qUser.whenModified)
                .setFirstRow(userQry.getStartPosition())
                .setMaxRows(userQry.getEndPosition())
                .orderBy().whenCreated.asc()
                .findPagedList();
    }

    public UserDetails loadUserByUsername(String username) {
        return new QUser().userName.eq(username).findOne();
    }

    public void resetPassWord(User user) {
        new QUser().id.eq(user.getId()).asUpdate().set(qUser.userName.toString(),user.getUsername()).set(qUser.password.toString(),user.getPassword()).update();
    }

    public User userinfo(String username) {
        QUser alias = QUser.alias();
        return new QUser().select(alias.userName,alias.userGroup,alias.name, alias.roles, alias.userExt.avatar)
                .userName.eq(username)
                .findOne();
    }
}
