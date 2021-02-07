package com.springwebsite.setting;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {

    private int min;

    private int max;

    private int prevPage;

    private int nextPage;

    private int pageTotalCount;

    private int currentPage;

    public Paging(int contentTotalCount, int currentPage, int contentCountPerPage, int paginationCount) {

        this.currentPage = currentPage;

        if(contentTotalCount == 0) {          // pagination 0번 항목 제거
            contentTotalCount = 1;
        }

        pageTotalCount = contentTotalCount / contentCountPerPage;

        if (contentTotalCount % contentCountPerPage > 0) {
            pageTotalCount++;
        }

        min = (((currentPage - 1) / contentCountPerPage) * contentCountPerPage) + 1;
        max = min + paginationCount - 1;

        if (max > pageTotalCount) {
            max = pageTotalCount;
        }

        prevPage = min - 1;
        if (prevPage == 0) {
            prevPage = 1;
        }
        nextPage = max + 1;
        if(nextPage > pageTotalCount) {
            nextPage = pageTotalCount;
        }
    }
}
