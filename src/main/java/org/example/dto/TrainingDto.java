package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainingDto {
    private String trainingName;
    private String trainerUsername;
    private String traineeUsername;
    private String dateTime;
    private Long durationMinutes;
    private String type;
}
