package com.sang.common.domain.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.ebean.Model;
import io.ebean.annotation.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel extends Model {

    /**
     * id 主键
     */
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @GeneratedValue(generator = "SnowIdGenerator")
    private Long id;

    /**
     * 版本
     */
    @Version
    private Long version;

    /**
     * 创建时间
     */
    @WhenCreated
    private Date whenCreated;

    /**
     * WhoCreated
     */
    @WhoCreated
    private String createdBy;

    /**
     * WhoModified
     */
    @WhoModified
    private String modifiedBy;

    /**
     * 更新时间
     */
    @WhenModified
    private Date whenModified;

    /**
     * SoftDelete
     */
    @SoftDelete
    private boolean deleted;
}
