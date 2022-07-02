package com.packet.gatway.manage.predicates;

import lombok.Data;

/**
 * @Data 为类提供读写功能，从而不用写get、set方法
 */
@Data
class TokenConfig
{
    private String token;
}
