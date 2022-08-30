package com.sang.system.service.router;

import com.sang.common.domain.router.entity.Button;
import com.sang.common.domain.router.param.ButtonQry;
import io.ebean.PagedList;

import java.util.List;

/**
 * 页面按钮维护
 * 页面按钮维护
 * 
 * hxy 2022-08-30 17:45:30
 */
public interface ButtonService {

    PagedList<Button> buttonList(ButtonQry qry);

    Button findOne(Long id);

    void save(Button button);

    void saveAll(List<Button> buttons);

    void insert(Button button);

    void update(Button button);

    void delete(Button button);

    void deleteAll(List<Button> buttons);
}