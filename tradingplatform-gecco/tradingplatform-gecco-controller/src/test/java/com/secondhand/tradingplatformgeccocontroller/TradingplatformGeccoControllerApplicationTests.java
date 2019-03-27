package com.secondhand.tradingplatformgeccocontroller;

import com.alibaba.druid.pool.DruidDataSource;
import com.secondhand.tradingplatformcommon.pojo.BusinessSelectItem;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradingplatformGeccoControllerApplicationTests {

    private static DruidDataSource dataSource;

    private static JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() {

    }

    public static void init(){
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/myshtp?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void insertElectricAppliance(com.secondhand.tradingplatformgeccocontroller.electricAppliance.DangDetail dangDetail){
        try {
            if (jdbcTemplate == null) {
                init();
            }

            Integer cover = 0;

            if (dangDetail.getCover() != null) {
                int tempSeparate = dangDetail.getCover().lastIndexOf("\\");
                if (tempSeparate == -1) {
                    tempSeparate = dangDetail.getCover().lastIndexOf("/") + 1;
                }
                String name = dangDetail.getCover().substring(tempSeparate, dangDetail.getCover().indexOf(".jpg"));
                cover = jdbcTemplate.queryForObject("SELECT MAX(id) FROM `c_business_annex`", Integer.TYPE) + 1;
                jdbcTemplate.update("INSERT INTO c_business_annex(name, type, extension, size, path, md5, content_type, uuid, description, deleted) VALUES('" +
                        name + "','" +
                        "" + "','" +
                        "jpg" + "','" +
                        0 + "','" +
                        "G:/data/file/tradingplatform/2019-03-24/" + name + ".jpg" + "','" +
                        "" + "','" +
                        "image/jpeg" + "','" +
                        ToolUtil.getUUID() + "','" +
                        "" + "','" +
                        '\0' +
                        "')");
            }
            jdbcTemplate.update("INSERT INTO c_business_electric_appliance(uuid, power, back_check_status, not_pass_reason, back_check_time, details, type, model, brand, user_id, comment_num, star, price, cover, title, description, deleted ) VALUES('" +
                    ToolUtil.getUUID() + "','" +
                    ToolUtil.getClawer(dangDetail.getPower()) + "','" +
                    BusinessSelectItem.BACK_STATUS_EXAMINATION_PASSED + "','" +
                    "" + "','" +
                    ToolUtil.getDateTime() + "','" +
                    ToolUtil.getClawer(dangDetail.getDetails().getDetail()) + "','" +
                    ToolUtil.getClawer(dangDetail.getType()) + "','" +
                    ToolUtil.getClawer(dangDetail.getModel()) + "','" +
                    ToolUtil.getClawer(dangDetail.getBrand()) + "','" +
                    0 + "','" +
                    dangDetail.getCommentNum() + "','" +
                    dangDetail.getStar() + "','" +
                    dangDetail.getPrice() + "','" +
                    (cover == 0 ? "" : Long.valueOf(cover).toString()) + "','" +
                    ToolUtil.getClawer(dangDetail.getTitle()) + "','" +
                    ToolUtil.getClawer(dangDetail.getClassification()) + "','" +
                    '\0' +
                    "')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertBookLibrary(com.secondhand.tradingplatformgeccocontroller.bookLibrary.DangDetail dangDetail){
        try {
        if (jdbcTemplate == null){
            init();
        }

        Integer cover = 0;

        if (dangDetail.getCover() != null) {
            int tempSeparate = dangDetail.getCover().lastIndexOf("\\");
            if (tempSeparate == -1){
                tempSeparate = dangDetail.getCover().lastIndexOf("/") + 1;
            }
            String name = dangDetail.getCover().substring(tempSeparate, dangDetail.getCover().indexOf(".jpg"));
            cover = jdbcTemplate.queryForObject("SELECT MAX(id) FROM `c_business_annex`", Integer.TYPE) + 1;
            jdbcTemplate.update("INSERT INTO c_business_annex(name, type, extension, size, path, md5, content_type, uuid, description, deleted) VALUES('" +
                    name + "','" +
                    "" + "','" +
                    "jpg" + "','" +
                    0 + "','" +
                    "G:/data/file/tradingplatform/2019-03-26/" + name + ".jpg" + "','" +
                    "" + "','" +
                    "image/jpeg" + "','" +
                    ToolUtil.getUUID() + "','" +
                    "" + "','" +
                    '\0' +
                    "')");
        }
        if (!ToolUtil.strIsEmpty(dangDetail.getAuthorHref())){
            dangDetail.setAuthor(dangDetail.getAuthorHref());
        } else {
            dangDetail.setAuthor(dangDetail.getAuthor().substring(3, dangDetail.getAuthor().length()));
        }
        String detail = dangDetail.getDetails().getDetail().replaceAll("'", "\\\\'");
        jdbcTemplate.update("INSERT INTO c_business_book_library(uuid, back_check_status, not_pass_reason, back_check_time, details, classification, isbn, suited, enfold, paper, format, published_time, publishing_house, author, comment_num, star, price, cover, title, description, deleted) VALUES('" +
                ToolUtil.getUUID() + "','" +
                BusinessSelectItem.BACK_STATUS_EXAMINATION_PASSED + "','" +
                "" + "','" +
                ToolUtil.getDateTime() + "','" +
                ToolUtil.getClawer(detail) + "','" +
                0 + "','" +
                ToolUtil.getClawer(dangDetail.getIsbn()) + "','" +
                (dangDetail.getSuited().equals("否") ? '\0' : '\1') + "','" +
                ToolUtil.getClawer(dangDetail.getEnfold()) + "','" +
                ToolUtil.getClawer(dangDetail.getPaper()) + "','" +
                ToolUtil.getClawer(dangDetail.getFormat()) + "','" +
                ToolUtil.getClawer(dangDetail.getPublishedTime()) + "','" +
                ToolUtil.getClawer(dangDetail.getPublishingHouse()) + "','" +
                ToolUtil.getClawer(dangDetail.getAuthor()) + "','" +
                dangDetail.getCommentNum() + "','" +
                (dangDetail.getStar() == null ? 0 : dangDetail.getStar()) + "','" +
                dangDetail.getPrice() + "','" +
                (cover == 0 ? "" : Long.valueOf(cover).toString()) + "','" +
                ToolUtil.getClawer(dangDetail.getTitle()) + "','" +
                ToolUtil.getClawer(dangDetail.getClassification()) + "','" +
                '\0' +
                "')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertSportsSpecial(com.secondhand.tradingplatformgeccocontroller.sportsSpecial.DangDetail dangDetail){
        try {
            if (jdbcTemplate == null){
                init();
            }

            Integer cover = 0;

            if (dangDetail.getCover() != null) {
                int tempSeparate = dangDetail.getCover().lastIndexOf("\\");
                if (tempSeparate == -1){
                    tempSeparate = dangDetail.getCover().lastIndexOf("/") + 1;
                }
                String name = dangDetail.getCover().substring(tempSeparate, dangDetail.getCover().indexOf(".jpg"));
                cover = jdbcTemplate.queryForObject("SELECT MAX(id) FROM `c_business_annex`", Integer.TYPE) + 1;
                jdbcTemplate.update("INSERT INTO c_business_annex(name, type, extension, size, path, md5, content_type, uuid, description, deleted) VALUES('" +
                        name + "','" +
                        "" + "','" +
                        "jpg" + "','" +
                        0 + "','" +
                        "G:/data/file/tradingplatform/2019-03-26/" + name + ".jpg" + "','" +
                        "" + "','" +
                        "image/jpeg" + "','" +
                        ToolUtil.getUUID() + "','" +
                        "" + "','" +
                        '\0' +
                        "')");
            }

            jdbcTemplate.update("INSERT INTO c_business_sports_special(uuid, details, classification, back_check_status, not_pass_reason, back_check_time, pattern, brand, user_id, star, comment_num, price, cover, title, description, deleted) VALUES('" +
                    ToolUtil.getUUID() + "','" +
                    ToolUtil.getClawer(dangDetail.getDetails().getDetail()) + "','" +
                    0 + "','" +
                    BusinessSelectItem.BACK_STATUS_EXAMINATION_PASSED + "','" +
                    "" + "','" +
                    ToolUtil.getDateTime() + "','" +
                    ToolUtil.getClawer(dangDetail.getPattern()) + "','" +
                    ToolUtil.getClawer(dangDetail.getBrand()) + "','" +
                    0 + "','" +
                    dangDetail.getStar() + "','" +
                    dangDetail.getCommentNum() + "','" +
                    dangDetail.getPrice() + "','" +
                    (cover == 0 ? "" : Long.valueOf(cover).toString()) + "','" +
                    ToolUtil.getClawer(dangDetail.getTitle()) + "','" +
                    ToolUtil.getClawer(dangDetail.getClassification()) + "','" +
                    '\0' +
                    "')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertDigitalSquare(com.secondhand.tradingplatformgeccocontroller.digitalSquare.DangDetail dangDetail){
        try {
            if (jdbcTemplate == null){
                init();
            }

            Integer cover = 0;

            if (dangDetail.getCover() != null) {
                int tempSeparate = dangDetail.getCover().lastIndexOf("\\");
                if (tempSeparate == -1){
                    tempSeparate = dangDetail.getCover().lastIndexOf("/") + 1;
                }
                String name = dangDetail.getCover().substring(tempSeparate, dangDetail.getCover().indexOf(".jpg"));
                cover = jdbcTemplate.queryForObject("SELECT MAX(id) FROM `c_business_annex`", Integer.TYPE) + 1;
                jdbcTemplate.update("INSERT INTO c_business_annex(name, type, extension, size, path, md5, content_type, uuid, description, deleted) VALUES('" +
                        name + "','" +
                        "" + "','" +
                        "jpg" + "','" +
                        0 + "','" +
                        "G:/data/file/tradingplatform/2019-03-27/" + name + ".jpg" + "','" +
                        "" + "','" +
                        "image/jpeg" + "','" +
                        ToolUtil.getUUID() + "','" +
                        "" + "','" +
                        '\0' +
                        "')");
            }

            jdbcTemplate.update("INSERT INTO c_business_digital_square(uuid, details, classification, back_check_status, not_pass_reason, back_check_time, pattern, brand, user_id, star, comment_num, price, cover, title, description, deleted) VALUES('" +
                    ToolUtil.getUUID() + "','" +
                    ToolUtil.getClawer(dangDetail.getDetails().getDetail()) + "','" +
                    0 + "','" +
                    BusinessSelectItem.BACK_STATUS_EXAMINATION_PASSED + "','" +
                    "" + "','" +
                    ToolUtil.getDateTime() + "','" +
                    ToolUtil.getClawer(dangDetail.getPattern()) + "','" +
                    ToolUtil.getClawer(dangDetail.getBrand()) + "','" +
                    0 + "','" +
                    dangDetail.getStar() + "','" +
                    dangDetail.getCommentNum() + "','" +
                    dangDetail.getPrice() + "','" +
                    (cover == 0 ? "" : Long.valueOf(cover).toString()) + "','" +
                    ToolUtil.getClawer(dangDetail.getTitle()) + "','" +
                    ToolUtil.getClawer(dangDetail.getClassification()) + "','" +
                    '\0' +
                    "')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void initDataSource(){
        if (dataSource == null){
            init();
        }
    }

    @PreDestroy
    public void closeDataSource(){
        if (dataSource != null){
            dataSource.close();
        }
    }
}
