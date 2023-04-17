package com.bol.kalaha.controller.request;

import com.bol.kalaha.repository.model.Player;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;


@Value
@Validated
public class PlayRequest {

    @Max(value = 5, message = "Pit index should be between 0 and 5 inclusive")
    @Min(value = 0, message = "Pit index should be between 0 and 5 inclusive")
    int pitIndex;

    @NotNull
    Player player;
}
