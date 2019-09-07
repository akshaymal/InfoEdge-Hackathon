package com.nnacres.assessment.entity;

import com.nnacres.assessment.enums.ContestStatus;
import com.nnacres.assessment.enums.ContestType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTEST")
@ToString(doNotUseGetters = true)
public class Contest extends BaseEntity {
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "OPEN_CONTEST")
    private boolean openContest;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ContestStatus status;

    @Column(name = "DURATION")
    private int duration;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContestType type;

    @Column(name = "IS_RANDOM_QUESTION_ORDER")
    private boolean isRandomQuestionOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    private Set<ContestQuestion> contestQuestions;
}
