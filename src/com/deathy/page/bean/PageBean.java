package com.deathy.page.bean;

import java.util.List;

/**
 * <p>
 * 数据分页
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class PageBean {
	/** 结果集 */
	private List<Object> list;
	/** 总记录数 */
	private int maxNum;
	/** 总页数 */
	private int maxPageNum;
	/** 当前页数 */
	private int pageNum;
	/** 分页游标起始位置 */
	private int firstPage;
	/** 每页显示记录数 */
	private int pageRows;
	/** 分页按钮 */
	private int pageButton;

	/**
	 * @return the list
	 */
	public List<Object> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Object> list) {
		this.list = list;
	}

	/**
	 * @return the maxNum
	 */
	public int getMaxNum() {
		return maxNum;
	}

	/**
	 * @param maxNum
	 *            the maxNum to set
	 */
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	/**
	 * @return the maxPageNum
	 */
	public int getMaxPageNum() {
		return maxPageNum;
	}

	/**
	 * @param maxPageNum
	 *            the maxPageNum to set
	 */
	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the firstPage
	 */
	public int getFirstPage() {
		return firstPage;
	}

	/**
	 * @param firstPage
	 *            the firstPage to set
	 */
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	/**
	 * @return the pageRows
	 */
	public int getPageRows() {
		return pageRows;
	}

	/**
	 * @param pageRows
	 *            the pageRows to set
	 */
	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	/**
	 * @return the pageButton
	 */
	public int getPageButton() {
		return pageButton;
	}

	/**
	 * @param pageButton
	 *            the pageButton to set
	 */
	public void setPageButton(int pageButton) {
		this.pageButton = pageButton;
	}

}
