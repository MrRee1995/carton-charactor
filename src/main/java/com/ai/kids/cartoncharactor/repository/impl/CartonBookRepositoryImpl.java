package com.ai.kids.cartoncharactor.repository.impl;

import com.ai.kids.cartoncharactor.entity.CartonBook;
import com.ai.kids.cartoncharactor.repository.CartonBookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.ai.kids.cartoncharactor.utils.ObjectMapperUtils.fromJSON;

@Repository
public class CartonBookRepositoryImpl implements CartonBookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartonBookRepositoryImpl.class);

    private static final String TABLE_NAME = "carton_book";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    RowMapper<CartonBook> ROW_MAPPER = (r, c) -> {
        CartonBook book = new CartonBook();
        book.setId(r.getLong("id"));
        book.setBookId(r.getLong("book_id"));
        book.setIntroduction(r.getString("introduction"));
        book.setTittle(r.getString("tittle"));
        book.setRoleId(r.getLong("role_id"));
        book.setFrontCoverUrl(r.getString("front_cover_url"));
        return book;
    };

    @Override
    public CartonBook queryCartonBook(long bookId) {
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE book_id = :book_id";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("book_id", bookId);
            return jdbcTemplate.queryForObject(sql, paramMap, ROW_MAPPER);
        } catch (Throwable th) {
            LOGGER.error("query carton book err!", th);
            return null;
        }
    }

    @Override
    public List<CartonBook> queryCartonBookList(int cursor, int size) {
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id >= :id LIMIT :size";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("id", cursor)
                    .addValue("size", size);
            return jdbcTemplate.query(sql, paramMap, ROW_MAPPER);
        } catch (Throwable th) {
            LOGGER.error("query carton book err!", th);
            return List.of();
        }
    }

    @Override
    public boolean insertCartonBook(CartonBook cartonBook) {
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " SET book_id = :book_id, title = :tittle, introduction = :introduction, " +
                    "role_id = :role_id, front_cover_url = :front_cover_url";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("book_id", cartonBook.getBookId())
                    .addValue("title", cartonBook.getTittle())
                    .addValue("introduction", cartonBook.getIntroduction())
                    .addValue("role_id", cartonBook.getRoleId())
                    .addValue("front_cover_url", cartonBook.getFrontCoverUrl());
            return jdbcTemplate.update(sql, paramMap) > 0;
        } catch (Throwable th) {
            LOGGER.error("insert carton book err!", th);
            return false;
        }
    }

    @Override
    public boolean delCartonBook(long bookId) {
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE book_id = :book_id LIMIT 1";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("book_id", bookId);
            return jdbcTemplate.update(sql, paramMap) > 0;
        } catch (Throwable th) {
            LOGGER.error("del carton book err!", th);
            return false;
        }
    }

}
