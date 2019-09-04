package com.nnacres.assessment.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Vishal on 16/12/17.
 */

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
