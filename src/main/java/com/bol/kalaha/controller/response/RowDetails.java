package com.bol.kalaha.controller.response;

import com.bol.kalaha.repository.model.Row;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RowDetails {
    List<PitDetails> pits;
    int kalahaStones;

    public static RowDetails from(Row row){
        return RowDetails.builder()
                  .kalahaStones(row.getKalahaStones())
                  .pits(row.getPits().stream().map(pit -> new PitDetails(pit.getStones())).toList())
                  .build();
    }
}
