package com.bol.kalaha.controller.request;

import com.bol.kalaha.repository.model.Player;
import jakarta.validation.constraints.Size;
import lombok.*;


@Value
public class PlayRequest {

    @Size(min = 1, max = 6)
    int cellIndex;

    Player player;
}
