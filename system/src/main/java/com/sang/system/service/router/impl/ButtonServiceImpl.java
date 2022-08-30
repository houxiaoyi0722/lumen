package com.sang.system.service.router.impl;

import com.sang.common.domain.router.entity.Button;
import com.sang.common.domain.router.param.ButtonQry;
import com.sang.common.domain.router.repo.ButtonRepository;
import com.sang.system.service.router.ButtonService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面按钮维护
 * 页面按钮维护
 * 
 * hxy 2022-08-30 17:45:30
 */
@Slf4j
@Service
public class ButtonServiceImpl implements ButtonService {

    @Resource
    private ButtonRepository repository;

    @Override
    public PagedList<Button> buttonList(ButtonQry qry) {
        return repository.getPage(qry);
    }

    @Override
    public Button findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(Button button) {
        button.save();
    }

    @Override
    @Transactional
    public void insert(Button button) {
        button.insert();
    }

    @Override
    @Transactional
    public void update(Button button) {
        button.update();
    }

    @Override
    @Transactional
    public void delete(Button button) {
        repository.delete(button);
    }

    @Override
    @Transactional
    public void saveAll(List<Button> Buttons) {
        repository.saveAll(Buttons);
    }

    @Override
    @Transactional
    public void deleteAll(List<Button> buttons) {
        repository.deleteAll(buttons);
    }
}