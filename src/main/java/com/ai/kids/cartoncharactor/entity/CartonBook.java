package com.ai.kids.cartoncharactor.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class CartonBook {

    @Id
    private long id;
    private long bookId;
    private String tittle;
    private String introduction;
    private String frontCoverUrl;
    private long roleId;
    private List<Long> pages;
}
