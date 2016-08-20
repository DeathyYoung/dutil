package com.deathyyoung.common.bean;

import java.util.List;

/**
 * <p>
 * 数据分页
 * 
 */
public class PageBean {

	/**
	 * <p>
	 * PageBean里按钮类型枚举，首页、上一页、下一页、尾页
	 * 
	 * @author <a href="#" target="_blank">Deathy
	 *         Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
	 */
	public enum Button {
		FirstPage, PrePage, NextPage, LastPage
	}

	/** 结果集 */
	private List<Object> list;
	/** 总记录数 */
	private int totalNum;
	/** 总页数 */
	private int maxPage;
	/** 当前页码 */
	private int page;
	/** 分页游标起始位置 */
	private int startIndex;
	/** 每页显示记录数 */
	private int rows;
	/** 分页按钮 */
	private Button button;

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
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum
	 *            the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the maxPage
	 */
	public int getMaxPage() {
		return maxPage;
	}

	/**
	 * @param maxPage
	 *            the maxPage to set
	 */
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the button
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * @param button
	 *            the button to set
	 */
	public void setButton(Button button) {
		this.button = button;
	}

}
