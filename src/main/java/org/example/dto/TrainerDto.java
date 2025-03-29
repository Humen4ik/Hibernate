package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerDto {
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String specialization;
    private Long userId;
}
