package com.sang.domain;

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

@Getter
@Setter
@MappedSuperclass
public class BaseModel extends Model {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @GeneratedValue(generator = "SnowIdGenerator")
    private Long id;

    @Version
    private Long version;

    @WhenCreated
    private Instant whenCreated;

    @WhoCreated
    private String createdBy;

    @WhoModified
    private String modifiedBy;

    @WhenModified
    private Instant whenModified;

    private boolean deleted;
}
