package com.nyaxs.shypicture.bean;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchCondition {

    public SearchCondition() {
        this.rankPage = 1;
        this.rankPerPage = 5;

        this.type = "rank";
        this.content = "illust";
        this.rankMode = "weekly";
        LocalDate localDate = LocalDate.now().minusDays(2);
        this.date = localDate.toString();
        this.searchMode = "tag";
        this.order = "desc";
        this.period = "all";
        this.searchPage = 1;
        this.searchPerPage = 5;

    }

    private String type;
    /*
    1.type = illust
    当type=illust时，返回作品信息（使用id指定具体作品）
    2.type = member_illust
    当type=member_illust时，返回作者的所有作品信息（使用id指定具体作者）
     */

    private int pictureId;
    private int authorId;

    /*
    3.type = rank
            当type=rank时，返回排行榜信息，排行榜信息的请求比较复杂，需要紧跟五个具体参数完成请求,每个参数之间用“&”符号连接。
            1.content=all为默认情况，content（排行榜内容）所有可取值如下：all综合/illust插画/manga漫画/ugoira动图
            2.mode=weekly为默认情况，mode（排行榜类型）所有可取值如下：
                daily日榜/weekly周榜/monthly月榜/rookie新人/original原创/male男性向/female女性向/......and more
            3.page:指定返回页数,默认1
            4.perPage:指定每页返回数量,默认20
            5.date:指定日期(yyyy-MM-dd),默认昨天
     */
    private String content;
    private String rankMode;
    private int rankPage;
    private int rankPerPage;
    private String date;


    /*
    4.type=search,搜索操作,6个参数
        1.word:搜索关键字
        2.searchMode=tag是默认情况，mode的所有可取值如下：text标题或描述/caption描述/tag非精确标签/exact_tag精确标签
        3.order:desc默认，按日期倒叙/asc，日期正序
        4:period=all为默认情况，period的所有可取值如下：all所有/day一天之内/week一周之内/month一月之内
        5:page:指定返回页数,默认1
        6:perPage:指定每页返回数量,默认20
     */
    private String word;
    private String searchMode;
    private String order;
    private String period;
    private int searchPage;
    private int searchPerPage;

    /*
    5.type=tags
    https://api.imjad.cn/pixiv/v1/?type=tags 返回所有热门标签
     */


}
