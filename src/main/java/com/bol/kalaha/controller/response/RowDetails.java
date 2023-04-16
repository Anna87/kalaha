package com.bol.kalaha.controller.response;

import com.bol.kalaha.repository.model.Cell;
import com.bol.kalaha.repository.model.Row;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RowDetails {
    List<CellDetails> cells;
    int kalahaStones;

    public static RowDetails from(Row row){
        return RowDetails.builder()
                  .kalahaStones(row.getKalahaStones())
                  .cells(row.getCells().stream().map(cell -> new CellDetails(cell.getStones())).toList())
                  .build();
    }
}
