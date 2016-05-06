package com.deathyyoung.lucene.util;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.ReaderUtil;

/**
 * 
 * @author <a href="#" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Mar 9, 2015
 */
@SuppressWarnings("unchecked")
public class LuceneUtil {

	/** index's directory */
	private Directory dir;
	/** IndexSearcher */
	private IndexSearcher searcher;
	/** IndexReader */
	private IndexReader reader;

	/**
	 * <p>
	 * 查询方式
	 * 
	 * @author <a href="#" target="_blank">Deathy
	 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
	 */
	public static enum QueryMethod {
		Term, Wildcard
	};

	/**
	 * <p>
	 * 构造函数
	 *
	 * @param indexPath
	 *            索引路径
	 */
	public LuceneUtil(String indexPath) {
		try {
			dir = FSDirectory.open(new File(indexPath));
			reader = IndexReader.open(dir);
			searcher = new IndexSearcher(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 获取字段信息
	 *
	 * @return <code>FieldInfos</code>
	 */
	public FieldInfos getFieldInfos() {
		return ReaderUtil.getMergedFieldInfos(reader);
	}

	/**
	 * <p>
	 * 获取字段名称
	 *
	 * @return 字段名称
	 */
	public String[] getFieldNames() {
		FieldInfos fis = getFieldInfos();
		String[] fieldNames = new String[fis.size()];
		Iterator<FieldInfo> iter = fis.iterator();
		int i = 0;
		while (iter.hasNext()) {
			FieldInfo fi = iter.next();
			fieldNames[i++] = fi.name;
		}
		return fieldNames;
	}

	/**
	 * <p>
	 * 获取所有文档
	 *
	 * @return 所有文档
	 */
	public List<Document> getAllDocs() {
		List<Document> list = new LinkedList<Document>();
		int count = reader.numDocs();
		try {
			for (int i = 0; i < count; i++) {
				Document doc = reader.document(i);
				list.add(doc);
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * <p>
	 * 将所有文档转换为List，每个文档转换为一个Map
	 *
	 * @param docs
	 *            所有文档
	 * @return List
	 */
	public List<Map<String, String>> getListFromDocs(List<Document> docs) {
		String[] fieldNames = getFieldNames();
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		for (int i = 0; i < docs.size(); i++) {
			Document doc = docs.get(i);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (int fn = 0; fn < fieldNames.length; fn++) {
				map.put(fieldNames[fn], doc.get(fieldNames[fn]));
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * <p>
	 * 根据指定字段的指定值进行查询，结果为一个列表
	 *
	 * @param key
	 *            字段
	 * @param value
	 *            值
	 * @return 文档列表
	 */
	public List<Document> queryTerm(String key, String value) {
		return queryTerm(Integer.MAX_VALUE, new SimpleEntry<String, String>(
				key, value));
	}

	/**
	 * <p>
	 * 查询前topN个文档，如果不够topN个，则有几个显示几个
	 *
	 * @param topN
	 *            前N个
	 * @param key
	 *            字段
	 * @param value
	 *            值
	 * @return 文档列表
	 */
	public List<Document> queryTerm(int topN, String key, String value) {
		return queryTerm(topN, new SimpleEntry<String, String>(key, value));
	}

	/**
	 * 查询文档
	 *
	 * @param entry
	 *            查询条件
	 * @return 文档列表
	 */
	public List<Document> queryTerm(Entry<String, String> entry) {
		return queryTerm(Integer.MAX_VALUE, entry);
	}

	/**
	 * <p>
	 * 查询文档
	 *
	 * @param map
	 *            查询条件
	 * @return 文档列表
	 */
	public List<Document> queryTerm(Map<String, String> map) {
		return queryTerm(Integer.MAX_VALUE, map);
	}

	/**
	 * 查询文档
	 *
	 * @param topN
	 *            前N个
	 * @param entry
	 *            查询条件
	 * @return 文档列表
	 */
	public List<Document> queryTerm(int topN, Entry<String, String> entry) {
		return queryTerms(topN, entry);
	}

	/**
	 * 查询文档
	 *
	 * @param topN
	 *            前N个
	 * @param map
	 *            查询条件
	 * @return 文档列表
	 */
	public List<Document> queryTerm(int topN, Map<String, String> map) {
		return queryTerms(topN, map);
	}

	public List<Document> queryTerms(Entry<String, String>... terms) {
		return queryTerms(Integer.MAX_VALUE, terms);
	}

	public List<Document> queryTerms(Set<Entry<String, String>> terms) {
		return queryTerms(Integer.MAX_VALUE, terms);
	}

	public List<Document> queryTerms(Map<String, String> terms) {
		return queryTerms(Integer.MAX_VALUE, terms);
	}

	public List<Document> queryTerms(int topN, Entry<String, String>... terms) {
		return query(QueryMethod.Term, topN, terms);
	}

	public List<Document> queryTerms(int topN, Set<Entry<String, String>> terms) {
		return query(QueryMethod.Term, topN, terms);
	}

	public List<Document> queryTerms(int topN, Map<String, String> terms) {
		return query(QueryMethod.Term, topN, terms);
	}

	public List<Document> queryWildcard(String key, String value) {
		return queryWildcard(Integer.MAX_VALUE,
				new SimpleEntry<String, String>(key, value));
	}

	public List<Document> queryWildcard(int topN, String key, String value) {
		return queryWildcard(topN, new SimpleEntry<String, String>(key, value));
	}

	public List<Document> queryWildcard(Map<String, String> term) {
		return queryWildcards(Integer.MAX_VALUE, term);
	}

	public List<Document> queryWildcard(Entry<String, String> term) {
		return queryWildcards(Integer.MAX_VALUE, term);
	}

	public List<Document> queryWildcard(int topN, Entry<String, String> term) {
		return queryWildcards(topN, term);
	}

	public List<Document> queryWildcard(int topN, Map<String, String> term) {
		return queryWildcards(topN, term);
	}

	public List<Document> queryWildcards(Set<Entry<String, String>> terms) {
		return queryWildcards(Integer.MAX_VALUE, terms);
	}

	public List<Document> queryWildcards(Entry<String, String>... terms) {
		return queryWildcards(Integer.MAX_VALUE, terms);
	}

	public List<Document> queryWildcards(Map<String, String> terms) {
		return queryWildcards(Integer.MAX_VALUE, terms);
	}

	public List<Document> queryWildcards(int topN,
			Entry<String, String>... terms) {
		return query(QueryMethod.Wildcard, topN, terms);
	}

	public List<Document> queryWildcards(int topN,
			Set<Entry<String, String>> terms) {
		return query(QueryMethod.Wildcard, topN, terms);
	}

	public List<Document> queryWildcards(int topN, Map<String, String> terms) {
		return query(QueryMethod.Wildcard, topN, terms);
	}

	public List<Document> query(QueryMethod qm, int topN,
			Entry<String, String>... entries) {
		List<Document> docs = new LinkedList<Document>();
		TopDocs matches = null;
		try {
			BooleanQuery bq = new BooleanQuery();
			switch (qm) {
			case Term:
				for (int i = 0; i < entries.length; i++) {
					bq.add(new TermQuery(new Term(entries[i].getKey(),
							entries[i].getValue())), BooleanClause.Occur.SHOULD);
				}
				break;
			case Wildcard:
				for (int i = 0; i < entries.length; i++) {
					bq.add(new WildcardQuery(new Term(entries[i].getKey(),
							entries[i].getValue())), BooleanClause.Occur.SHOULD);
				}
				break;
			default:
				break;
			}
			matches = searcher.search(bq, topN);
			for (int i = 0; i < matches.scoreDocs.length; i++) {
				docs.add(reader.document(matches.scoreDocs[i].doc));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docs;
	}

	public List<Document> query(QueryMethod qm, int topN,
			Set<Entry<String, String>> entrySet) {
		List<Document> docs = new LinkedList<Document>();
		TopDocs matches = null;
		try {
			BooleanQuery bq = new BooleanQuery();
			switch (qm) {
			case Term:
				for (Entry<String, String> entry : entrySet) {
					bq.add(new TermQuery(new Term(entry.getKey(), entry
							.getValue())), BooleanClause.Occur.SHOULD);
				}
				break;
			case Wildcard:
				for (Entry<String, String> entry : entrySet) {
					bq.add(new WildcardQuery(new Term(entry.getKey(), entry
							.getValue())), BooleanClause.Occur.SHOULD);
				}
				break;
			default:
				break;
			}
			matches = searcher.search(bq, topN);
			for (int i = 0; i < matches.scoreDocs.length; i++) {
				docs.add(reader.document(matches.scoreDocs[i].doc));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docs;
	}

	public List<Document> query(QueryMethod qm, int topN,
			Map<String, String> map) {
		return query(qm, topN, map.entrySet());
	}

	public int find(String fld, String key) {
		return find(fld, key, Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * Query query = new WildcardQuery(new Term("Title", "*互动*"));
	 * <p>
	 * lu.find(query);
	 *
	 */
	public int find(Query query) {
		return find(query, Integer.MAX_VALUE);
	}

	public int find(Query query, int topN) {
		TopDocs matches;
		try {
			matches = searcher.search(query, topN);
			return matches.scoreDocs.length;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int find(String fld, String key, int topN) {
		Query wQuery = new WildcardQuery(new Term(fld, "*" + key + "*"));
		return find(wQuery, topN);
	}

	public String[] query(String fld, String key) {
		return query(fld, key, Integer.MAX_VALUE);
	}

	public String[] query(String fld, String key, int topN) {
		Query wQuery = new WildcardQuery(new Term(fld, "*" + key + "*"));
		return query(wQuery, topN);
	}

	public String[] query(Query query, int topN) {
		String[] result = null;
		try {
			TopDocs matches = searcher.search(query, topN);
			result = new String[matches.scoreDocs.length];
			for (int i = 0; i < matches.scoreDocs.length; i++) {
				result[i] = reader.document(matches.scoreDocs[i].doc).get(
						"Title");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>
	 * 查询strs里哪些包含content
	 *
	 * @param content
	 *            查询内容
	 * @param strs
	 *            带查询数组
	 * @return 包含content的字符串数组
	 */
	public String[] contain(String content, String[] strs) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].contains(content)) {
				list.add(strs[i]);
			}
		}
		return list.toArray(new String[0]);
	}

	/**
	 * <p>
	 * Get the longest common substring
	 *
	 * @param content
	 *            content
	 * @param strs
	 *            strs
	 * @return the longest common substring
	 */
	public String getMaxMatch(String content, String[] strs) {
		int maxLength = -1;
		String result = "";
		for (int i = 0; i < strs.length; i++) {
			String temp = longestCommonSubstring(content, strs[i]);
			if (maxLength < temp.length()) {
				maxLength = temp.length();
				result = temp;
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Get the longest common substring of <code>first</code> and
	 * <code>second</code>
	 *
	 * @param first
	 *            first
	 * @param second
	 *            second
	 * @return the longest common substring
	 */
	public String longestCommonSubstring(String first, String second) {
		String tmp = "";
		String max = "";
		for (int i = 0; i < first.length(); i++) {
			for (int j = 0; j < second.length(); j++) {
				for (int k = 1; (k + i) <= first.length()
						&& (k + j) <= second.length(); k++) {
					if (first.substring(i, k + i).equals(
							second.substring(j, k + j))) {
						tmp = first.substring(i, k + i);
					} else {
						if (tmp.length() > max.length())
							max = tmp;
						tmp = "";
					}
				}
				if (tmp.length() > max.length())
					max = tmp;
				tmp = "";
			}
		}
		return max;
	}

}
