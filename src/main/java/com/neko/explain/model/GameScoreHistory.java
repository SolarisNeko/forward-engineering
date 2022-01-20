package com.neko.explain.model;

import com.neko.explain.annotation.NotGenerate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameScoreHistory {

    @NotGenerate
    private Integer id;

    private Integer userId;

    private Integer gameId;

    private Integer score;

    private Date createTime;

    private Boolean deleted;

}
