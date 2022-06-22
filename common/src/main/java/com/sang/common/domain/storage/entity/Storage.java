package com.sang.common.domain.storage.entity;


import com.sang.common.domain.base.entity.BaseModel;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Builder
@Table(name = "STORAGE")
@DbComment("对象存储管理")
public class Storage extends BaseModel {




}
