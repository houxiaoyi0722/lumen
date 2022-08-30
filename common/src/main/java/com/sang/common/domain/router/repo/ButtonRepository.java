package com.sang.common.domain.router.repo;

import com.sang.common.domain.router.entity.Button;
import com.sang.common.domain.router.param.ButtonQry;
import com.sang.common.domain.router.entity.query.QButton;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

/**
 * 页面按钮维护
 * 页面按钮维护
 * Repository
 * hxy 2022-08-30 17:45:30
 */
@Repository
public class ButtonRepository extends BeanRepository<Long, Button> {

    protected ButtonRepository(Database server) {
        super(Button.class, server);
    }

    public PagedList<Button> getPage(ButtonQry qry) {
        QButton alice = QButton.alias();

        return new QButton()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


}
