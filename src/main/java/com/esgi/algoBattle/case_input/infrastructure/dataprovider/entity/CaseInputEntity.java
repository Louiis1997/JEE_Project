package com.esgi.algoBattle.case_input.infrastructure.dataprovider.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity(name = "case_input")
@Accessors(chain = true)
@Data
public class CaseInputEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String value;

    @Column(name = "case_id")
    private Long algorithmCaseId;
}
