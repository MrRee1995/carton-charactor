package com.ai.kids.cartoncharactor.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CartonPage {

    @Id
    private long id;
    private long pateId;
    private String txtContent;
    private String imgUrl;
    private String voiceUrl;
}
