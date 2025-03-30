package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingTypeEntity trainingType;

    @NotNull
    @Column(nullable = false)
    private String trainingName;

    @NotNull
    @Column(nullable = false)
    private Date trainingDate;

    @NotNull
    @Column(nullable = false)
    private Integer duration;

}
