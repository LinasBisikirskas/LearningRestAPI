package com.LearningRestAPI.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Buildings")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String owner;
    private int squareMeters;
    private int buildingValue;
    private String buildingType;
}