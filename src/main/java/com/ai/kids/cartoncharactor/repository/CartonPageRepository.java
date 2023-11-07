package com.ai.kids.cartoncharactor.repository;

import com.ai.kids.cartoncharactor.entity.CartonBook;
import com.ai.kids.cartoncharactor.entity.CartonPage;

import java.util.List;

public interface CartonPageRepository  {

    List<CartonPage> queryCartonPages(List<Long> pageIds);

    CartonPage queryCartonPage(long pageId);

    boolean insertCartonPage(CartonPage cartonPage);

    boolean delCartonPage(long pageId);
}
