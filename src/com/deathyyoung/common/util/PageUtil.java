package com.deathyyoung.common.util;

import com.deathyyoung.common.bean.PageBean.Button;

/**
 * <p>
 * 分页封装及业务的处理类
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class PageUtil {

	/**
	 * <p>
	 * 判断分页按钮位置的方法
	 *
	 * @param index
	 *            当前页起始index
	 * @param rows
	 *            每页显示的记录数 （固定不变），当前页的起始位置，pageNum总记录数，pagebutton分页按钮
	 * @param totalNum
	 *            总记录数
	 * @param button
	 *            按钮
	 * @return int类型某个按钮的分页起始位置
	 */
	public static int getStartIndex(int index, int rows, int totalNum,
			Button button) {
		int startIndex = 0;
		switch (button) {
		case FirstPage:
			startIndex = 0;
			break;
		case PrePage:
			startIndex = index - rows;
			break;
		case NextPage:
			startIndex = index + rows;
			break;
		case LastPage:
			// 举例算法如果每页显示5条记录 startIndex=((i+4)/5-1)*5;
			// 总页数i+4/5
			// 在-1*5最后一起的起始位置
			startIndex = ((totalNum + (rows - 1)) / rows - 1) * rows;
			break;
		default:
			startIndex = 0;
			break;
		}
		return startIndex;
	}

	/**
	 * @param page
	 *            查询的页码
	 * @param rows
	 *            每页显示的记录数
	 * @return int类型分页起始位置
	 */
	public static int getStartIndex(int page, int rows) {
		return page * rows;
	}

	/**
	 * @param totalNum
	 *            总记录数
	 * @param rows
	 *            每页显示的记录数
	 * @return int类型最大页码
	 */
	public static int getMaxPage(int totalNum, int rows) {
		return (totalNum + rows - 1) / rows;
	}
}
