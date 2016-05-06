package com.deathyyoung.web.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;

/**
 * <p>
 * Need jsoup-1.8.1.jar.
 * 
 * @author <a href="#" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Mar 2, 2015
 */
public class HtmlUtil {

	/**
	 * 截取字符串长字，保留HTML格式
	 * 
	 * @param content
	 * @param len
	 *            字符长度
	 * @author jimmy
	 */
	public static String truncateHTML(String content, int len) {
		Document dirtyDocument = Jsoup.parse(content);
		Element source = dirtyDocument.body();
		Document clean = Document.createShell(dirtyDocument.baseUri());
		Element dest = clean.body();
		truncateHTML(source, dest, len);
		return dest.outerHtml();
	}

	/**
	 * 使用Jsoup预览
	 * 
	 * @param source
	 *            需要过滤的
	 * @param dest
	 *            过滤后的对象
	 * @param len
	 *            截取字符长度
	 * @author jimmy
	 */
	private static void truncateHTML(Element source, Element dest, int len) {
		List<Node> sourceChildren = source.childNodes();
		for (Node sourceChild : sourceChildren) {
			if (sourceChild instanceof Element) {
				Element sourceEl = (Element) sourceChild;
				Element destChild = createSafeElement(sourceEl);
				int txt = dest.text().length();
				if (txt >= len) {
					break;
				} else {
					len = len - txt;
				}
				dest.appendChild(destChild);
				truncateHTML(sourceEl, destChild, len);
			} else if (sourceChild instanceof TextNode) {
				int destLeng = dest.text().length();
				if (destLeng >= len) {
					break;
				}
				TextNode sourceText = (TextNode) sourceChild;
				int txtLeng = sourceText.getWholeText().length();
				if ((destLeng + txtLeng) > len) {
					int tmp = len - destLeng;
					String txt = sourceText.getWholeText().substring(0, tmp);
					TextNode destText = new TextNode(txt, sourceChild.baseUri());
					dest.appendChild(destText);
					break;
				} else {
					TextNode destText = new TextNode(sourceText.getWholeText(),
							sourceChild.baseUri());
					dest.appendChild(destText);
				}
			}
		}
	}

	/**
	 * 按原Element重建一个新的Element
	 * 
	 * @param sourceEl
	 * @return
	 * @author jimmy
	 */
	private static Element createSafeElement(Element sourceEl) {
		String sourceTag = sourceEl.tagName();
		Attributes destAttrs = new Attributes();
		Element dest = new Element(Tag.valueOf(sourceTag), sourceEl.baseUri(),
				destAttrs);
		Attributes sourceAttrs = sourceEl.attributes();
		for (Attribute sourceAttr : sourceAttrs) {
			destAttrs.put(sourceAttr);
		}
		return dest;
	}

	/**
	 * @param content
	 *            全文
	 * @param len
	 *            长度
	 * @return 预览字符串
	 */
	public static String getPreview(String content, int len) {
		String subContent = content.substring(0, len - 2) + "……";
		String previewTemp = truncateHTML(subContent, len);
		return previewTemp.substring(8, previewTemp.length() - 8);
	}
}
