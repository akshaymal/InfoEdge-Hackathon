package com.nnacres.assessment.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @PrePersist
    public void create() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    @PostPersist
    public void update() {
        this.updatedDate = new Date();
    }

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = false)
    private Date updatedDate;

}
