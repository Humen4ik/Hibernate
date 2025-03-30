package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.TrainingType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainingtype")
public class TrainingTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingType trainingType;


}
