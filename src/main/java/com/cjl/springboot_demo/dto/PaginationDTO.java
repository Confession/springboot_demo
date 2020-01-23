package com.cjl.springboot_demo.dto;

import com.cjl.springboot_demo.mapper.QuestionMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOS;
    private boolean showPre;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer totalPage;
    //当前页
    private Integer page;
    //展示的页码列表
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        //页码列表 以当前页码为中心，两边不超过gap个页码
        Integer gap = 3;
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= gap; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        //是否展示上一页
        if (page == 1) {
            showPre = false;
        } else showPre = true;
        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else showNext = true;
        //是否展示回到第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else showFirstPage = true;
        //是否展示回到最后一页页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else showEndPage = true;
    }
}
