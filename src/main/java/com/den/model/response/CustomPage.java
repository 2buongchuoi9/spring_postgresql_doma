package com.den.model.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Data
public class CustomPage<T> {
    private List<T> content;
    private int page;
    private int pageSize;
    private long totalElement;
    private int totalPage;
    public CustomPage(Page<T> pageImpl) {
        this.content=pageImpl.getContent();
        this.page = pageImpl.getPageable().getPageNumber()+1;
        this.pageSize=pageImpl.getPageable().getPageSize();
        this.totalElement = pageImpl.getTotalElements();
        this.totalPage = pageImpl.getTotalPages();
    }
}
