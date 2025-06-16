package com.sang.common.domain.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.ebean.Model;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

    protected BaseModel(BaseModelBuilder<?, ?> b) {
        this.id = b.id;
        this.version = b.version;
        this.whenCreated = b.whenCreated;
        this.createdBy = b.createdBy;
        this.modifiedBy = b.modifiedBy;
        this.whenModified = b.whenModified;
        this.deleted = b.deleted;
    }

    public static BaseModelBuilder<?, ?> builder() {
        return new BaseModelBuilderImpl();
    }

    public static abstract class BaseModelBuilder<C extends BaseModel, B extends BaseModelBuilder<C, B>> {
        private Long id;
        private Long version;
        private Date whenCreated;
        private String createdBy;
        private String modifiedBy;
        private Date whenModified;
        private boolean deleted;

        public B id(Long id) {
            this.id = id;
            return self();
        }

        public B version(Long version) {
            this.version = version;
            return self();
        }

        public B whenCreated(Date whenCreated) {
            this.whenCreated = whenCreated;
            return self();
        }

        public B whenCreated(String createdBy) {
            this.createdBy = createdBy;
            return self();
        }

        public B modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return self();
        }

        public B whenModified(Date whenModified) {
            this.whenModified = whenModified;
            return self();
        }

        public B deleted(boolean deleted) {
            this.deleted = deleted;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        @Override
        public String toString() {
            return "BaseModelBuilder{" +
                    "id=" + id +
                    ", version=" + version +
                    ", whenCreated=" + whenCreated +
                    ", createdBy='" + createdBy + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", whenModified=" + whenModified +
                    ", deleted=" + deleted +
                    '}';
        }
    }

    private static final class BaseModelBuilderImpl extends BaseModelBuilder<BaseModel, BaseModelBuilderImpl> {
        private BaseModelBuilderImpl() {
        }

        protected BaseModelBuilderImpl self() {
            return this;
        }

        public BaseModel build() {
            return new BaseModel(this);
        }
    }

}
