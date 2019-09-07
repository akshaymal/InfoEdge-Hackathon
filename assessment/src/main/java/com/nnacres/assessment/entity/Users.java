package com.nnacres.assessment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
public class Users extends BaseEntity {

   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "USERNAME", nullable = false)
   private String username;

   @Column(name = "PASSWORD", nullable = false)
   private String password;
}
