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
    public CustomPage(PageImpl<T> pageImpl) {
        System.out.println(pageImpl);
        this.content=pageImpl.getContent();
        this.page = pageImpl.getNumber()+1;
        this.pageSize=pageImpl.getPageable().getPageSize();
        this.totalElement = pageImpl.getTotalElements();
        this.totalPage = (int) Math.ceil((double) this.totalElement / this.pageSize);
    }
}
