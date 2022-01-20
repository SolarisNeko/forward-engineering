package com.neko.orm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameScore {

    private Integer userId;

    private Integer gameId;

    private Integer bestScore;

    private Integer totalPlayTimes;

    private Date date;

}
