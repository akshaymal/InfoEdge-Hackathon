package com.nnacres.assessment.entity;

import java.util.Locale;
import java.util.Set;
import javax.persistence.*;

import com.nnacres.assessment.enums.DifficultyLevel;
import com.nnacres.assessment.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "QUESTION")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true, exclude = {"options", "testCases", "category"})
public class Question extends BaseEntity{

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;
    
    @Column(name = "CATEGORY_ID", nullable = false)
    private Long categoryId;

    @Column(name = "TEXT", columnDefinition = "text",nullable = false)
    private String text;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column(name = "DIFFICULTY_LEVEL", nullable = false)
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.EAGER)
    private Set<Option> options;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<TestCase> testCases;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<ContestQuestion> contestQuestions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "QUESTION_CATEGORY", joinColumns = { @JoinColumn(name = "QUESTION_ID") }, inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID") })
    private Set<Category> category;
}
