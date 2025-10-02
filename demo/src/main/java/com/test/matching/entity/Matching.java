package com.test.matching.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 auto_increment 사용 시
    private Long id;
}
