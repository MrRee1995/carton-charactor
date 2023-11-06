package com.ai.kids.cartoncharactor.repository;

import com.ai.kids.cartoncharactor.entity.CartonBook;

import java.util.List;

public interface CartonBookRepository {
    CartonBook queryCartonBook(long bookId);
    
    List<CartonBook> queryCartonBookList(int cursor, int size);
    
    boolean insertCartonBook(CartonBook cartonBook);
    
    boolean delCartonBook(long bookId);
}
