package com.nnacres.assessment.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class Category extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

}
