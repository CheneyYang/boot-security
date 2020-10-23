package com.iooiee.common;

/**
 * Description:
 *
 * @Author: Yanggc
 * DateTime: 10/23 12:24
 */
public class PageResult<T> extends Result<T> {


    private Long total;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currentPage;

    private PageResult() {
    }

    public PageResult(Long total, Integer pageSize, Integer totalPage, Integer currentPage) {
        this.total = total;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }


    public static <T> PageResult<T> success() {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setResultCode(ResultCodeEnum.SUCCESS);

        return pageResult;
    }

    public static <T> PageResult<T> failure(ResultCodeEnum resultCodeEnum) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setResultCode(resultCodeEnum);
        return pageResult;
    }





    /**
     *     getter setter
     */
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
