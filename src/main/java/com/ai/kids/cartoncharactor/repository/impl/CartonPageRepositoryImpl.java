package com.ai.kids.cartoncharactor.repository.impl;

import com.ai.kids.cartoncharactor.entity.CartonPage;
import com.ai.kids.cartoncharactor.repository.CartonPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartonPageRepositoryImpl implements CartonPageRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartonPageRepositoryImpl.class);

    private static final String TABLE_NAME = "carton_page";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    RowMapper<CartonPage> ROW_MAPPER = (r, c) -> {
        CartonPage page = new CartonPage();
        page.setId(r.getLong("id"));
        page.setPageId(r.getLong("page_id"));
        page.setTxtContent(r.getString("txt_content"));
        page.setImgUrl(r.getString("img_url"));
        page.setVoiceUrl(r.getString("voice_url"));
        return page;
    };

    @Override
    public List<CartonPage> queryCartonPages(List<Long> pageIds) {
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE page_id IN (page_id)";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("page_id", pageIds);
            return jdbcTemplate.query(sql, paramMap, ROW_MAPPER);
        } catch (Throwable th) {
            LOGGER.error("query carton pages err!", th);
            return List.of();
        }
    }

    @Override
    public CartonPage queryCartonPage(long pageId) {
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE page_id = :page_id";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("page_id", pageId);
            return jdbcTemplate.queryForObject(sql, paramMap, ROW_MAPPER);
        } catch (Throwable th) {
            LOGGER.error("query carton page err!", th);
            return null;
        }
    }

    @Override
    public boolean insertCartonPage(CartonPage cartonPage) {
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " SET page_id = :page_id, txt_content = :txt_content, img_url = :img_url, " +
                    "voice_url = :voice_url";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("page_id", cartonPage.getPageId())
                    .addValue("txt_content", cartonPage.getTxtContent())
                    .addValue("img_url", cartonPage.getImgUrl())
                    .addValue("voice_url", cartonPage.getVoiceUrl());
            return jdbcTemplate.update(sql, paramMap) > 0;
        } catch (Throwable th) {
            LOGGER.error("insert carton page err!", th);
            return false;
        }
    }

    @Override
    public boolean delCartonPage(long pageId) {
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE page_id = :page_id LIMIT 1";
            MapSqlParameterSource paramMap = new MapSqlParameterSource("page_id", pageId);
            return jdbcTemplate.update(sql, paramMap) > 0;
        } catch (Throwable th) {
            LOGGER.error("del carton page err!", th);
            return false;
        }
    }
}
