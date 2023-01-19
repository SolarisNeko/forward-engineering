package com.neko233.forward.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author SolarisNeko
 * Date on 2022-04-13
 */
@Data
@AllArgsConstructor
@Builder
public class Email {

    int id;
    String username;
    String lastLoginDate;

}
