package com.sang.common.domain.auth.authentication.user.repo;

import cn.hutool.core.util.StrUtil;
import com.sang.common.domain.auth.authorization.role.entity.query.QRole;
import com.sang.common.domain.auth.authentication.user.entity.User;
import com.sang.common.domain.auth.authentication.user.entity.query.QUser;
import com.sang.common.domain.auth.authentication.user.entity.query.QUserGroup;
import com.sang.common.domain.auth.authentication.user.param.UserQry;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
    private final QRole qRole = QRole.alias();
    private final QUserGroup qUserGroup = QUserGroup.alias();


    public PagedList<User> userList(UserQry userQry) {
        QUser select = new QUser()
                .select(qUser.id, qUser.username, qUser.name,
                        qUser.enabled, qUser.whenCreated, qUser.whenModified
                )
                .roles.fetch(qRole.id)
                .userGroup.fetch(qUserGroup.id);

        if (StrUtil.isNotBlank(userQry.getName())) {
            select.name.eq(userQry.getName());
        }

        if (StrUtil.isNotBlank(userQry.getUsername())) {
            select.username.eq(userQry.getUsername());
        }

        if (userQry.getEnabled() != null) {
            select.enabled.eq(userQry.getEnabled());
        }

        if (userQry.getRoles() != null) {
            select.roles.id.eq(userQry.getRoles());
        }

        if (userQry.getUserGroup() != null) {
            select.userGroup.id.eq(userQry.getUserGroup());
        }

        return select
                .setFirstRow(userQry.getStartPosition())
                .setMaxRows(userQry.getEndPosition())
                .orderBy().whenCreated.asc()
                .findPagedList();
    }

    public UserDetails loadUserByUsername(String username) {
        return new QUser().username.eq(username).findOne();
    }

    public void resetPassWord(User user) {
        new QUser().id.eq(user.getId()).asUpdate()
                .set(qUser.password.toString(),user.getPassword())
                .set(qUser.whenModified.toString(), new Date())
                .update();
    }

    public User userinfo(String username) {
        QUser alias = QUser.alias();
        return new QUser().select(alias.username,alias.userGroup,alias.name, alias.roles, alias.userExt)
                .username.eq(username)
                .findOne();
    }

    /**
     * 更新用户信息(不包括密码)
     * @param user
     */
    public void updateUserInfo(User user) {
        int update = new QUser()
                .id.eq(user.getId())
                .asUpdate()
                .set(qUser.name.toString(), user.getName())
                .set(qUser.username.toString(), user.getUsername())
                .set(qUser.enabled.toString(), user.getEnabled())
                .set(qUser.roles.toString(), user.getRoles())
                .set(qUser.userGroup.toString(), user.getUserGroup())
                .update();
    }
}
