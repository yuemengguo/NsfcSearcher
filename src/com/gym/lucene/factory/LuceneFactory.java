package com.gym.lucene.factory;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gym.dataService.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LuceneFactory {
	//定义索引目录
	private String indexDir = null;
	private static IndexSearcher searcher = null;
	//定义分词器
	private static Analyzer analyzer = null;
	
	//日志目录
	private static String fileName = "d:\\log.txt";
	
	/*
	 * 构造方法，读取配置，初始化索引的保存的位置,创建分词器对象
	 */
	public LuceneFactory() {
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream (Service.class.getClassLoader().getResourceAsStream("config.properties"));
		try {
			prop.load(in);
			indexDir = prop.getProperty("indexDir");
			fileName = prop.getProperty("fileName", "c:\\log.txt");
			analyzer = new IKAnalyzer();
            File file = new File(fileName);  
            if (file.exists()) {  
                 
               file.delete();  
            }    
            file.createNewFile();  
		} catch (IOException e) {
			System.out.println("读取配置信息失败：" + e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @param data
	 * @param sortName
	 * @param fields 建立索引的数据字段
	 * @return
	 * @throws IOException
	 */
	public JSONObject createIndex(JSONObject data, String sortName,String[][] indexFields) throws IOException {
		JSONObject json = new JSONObject();
		//获取索引保存目录
		Directory directory = FSDirectory.open(Paths.get(indexDir, new String[0]));
		//创建IndexWriterConfig
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		
		//获取数据
		JSONArray jsonArray = (JSONArray) data.getJSONArray("items");
		//遍历数据，进行索引的创建
		for(int i=0;i<jsonArray.size();i++) {
			Document doc = new Document();
			JSONObject item = jsonArray.getJSONObject(i);
			
			System.out.println("创建索引："+item.toString());
			WriteDate("创建索引："+item.optString("pro_name","N/A"));
			
			//遍历字段，获取要创建的索引
			for(int j=0; j<indexFields.length; j++) {
				if(indexFields[j][1] == "1") {
					doc.add(new StringField(indexFields[j][0],item.optString(indexFields[j][0],"N/A"),Field.Store.YES));
				}else {
					doc.add(new TextField(indexFields[j][0],item.optString(indexFields[j][0],"N/A"),Field.Store.YES));
				}
			}
			
			//创建排序索引
			doc.add(new SortedDocValuesField(sortName,new BytesRef(item.getString(sortName))));
			
//			String leaderCode = item.optString("leader_code","N/A");
//			String leaderOrganization = item.optString("leader_organization","N/A");
//			doc.add(new TextField("leaderCode",leaderCode, Field.Store.YES));
//			doc.add(new TextField("leaderOrganization",leaderOrganization, Field.Store.YES));
//			
//			//创建排序索引,以机构进行排序
//			doc.add(new SortedDocValuesField("leaderOrganization", new BytesRef(leaderOrganization)));
			
			
			
			
			//将Doc写入到索引中去
			indexWriter.addDocument(doc);
		}
		//提交索引并关闭
	    indexWriter.commit();
	    indexWriter.close();
	    directory.close();
	    if(jsonArray.size() > 0) {
	    	json.put("code", "200");
		    json.put("indexSize", jsonArray.size());
	    }else {
	    	json.put("code", "500");//创建失败
	    	json.put("message", "数据库读取数据失败");
		    json.put("indexSize", jsonArray.size());
	    }
		return json;
	}
	
//	public JSONObject ttt() {
//		JSONObject json = new JSONObject();
//		json.put("ceshi", "ok");
//		return json;
//	}
	
	public JSONObject search(JSONObject request,String[][] indexFields,String[] searchFields) throws IOException, ParseException {
		JSONObject json = new JSONObject();
		TopDocs topDocs = null;
		JSONArray items = new JSONArray();
		String queryStr = request.getString("queryStr");
		int pageNum = request.optInt("pageNum",1);
		int pageSize = request.optInt("pageSize",10);
		String sortName = request.getString("sortName");
		boolean sortType = request.optBoolean("sortType",true);
		int totals = request.optInt("totals",0);
		int totalPage = request.optInt("totalPage",0);
		Directory directory = FSDirectory.open(Paths.get(indexDir, new String[0]));
		IndexReader indexReader = DirectoryReader.open(directory);
		if(searcher == null) {
			searcher = new IndexSearcher(indexReader);	
		}
		
		//使用查询解析器创建Query
		QueryParser parser = new QueryParser("",analyzer);  //允许为空，直接依靠下边的条件进行解析
		//开始解析(拼接查询字符串)
		StringBuffer str = new StringBuffer();
		int count = 0;
		for(int i=0; i<indexFields.length; i++) {
			
			//只加入允许索引的字段
			if(indexFields[i][1] != "1") {
				if(count == 0) {
					if(indexFields[i][1] == "0"){
						str.append(indexFields[i][0]+":\""+queryStr + "\"~0");
					}else {
						str.append(indexFields[i][0]+":\""+queryStr + "\"");
					} 
				}else {
					if(indexFields[i][1] == "0"){
						str.append(" OR " + indexFields[i][0]+":\""+queryStr + "\"~0");
					}else {
						str.append(" OR " + indexFields[i][0]+":\""+queryStr + "\"");
					} 
				}
				count++;
			}
		}
		queryStr = str.toString();
////		queryStr = "014cb87c9003df97b5e37fc8b71b6f8";
//		//~2用来设置分词之间的间隔为2，
//		queryStr = "content:\"" + queryStr + "\"~1" + " OR leaderCode:\"" + queryStr + "\"~1";  
		
		
		Query query = parser.parse(queryStr);
		
		if(totals == 0) {
			//从索引中获取前十的文档
			topDocs = searcher.search(query, pageSize);
			totals = topDocs.totalHits;
			totalPage = totals/pageSize + 1;
			System.out.println("------" + totals);
		}
		//如果超出页码范围，将不再进行查询
		if(pageNum <= totalPage) {
			//定义排序
			Sort sort = new Sort(new SortField(sortName,Type.STRING, sortType)); 
			if(totals > pageSize) { 
				//超过一页的大小之后，进行分页查询
				ScoreDoc docsOffset = getLastScoreDoc(pageNum,pageSize,query,searcher, sort);
				topDocs = searcher.searchAfter(docsOffset, query, pageSize, sort);
			}else {
				topDocs = searcher.search(query, pageSize, sort);
			}
		
			//处理结果
			ScoreDoc[] socreDocs = topDocs.scoreDocs;
			items = addHit2JSON(socreDocs, searcher,searchFields);
		}
		//处理返回信息
		JSONObject page = new JSONObject();
		page.put("pageSize", pageSize);
		page.put("pageNum", pageNum);
		page.put("totals", totals);
		page.put("totalPage", totalPage);
		json.put("page", page);
		json.put("items", items);
		json.put("code", 200);
		return json;
	}
	
	/**
	 * 获取当前分页的最后一个的DocId
	 * @param pageNum
	 * @param pageSize
	 * @param query
	 * @param searcher
	 * @param sort
	 * @return
	 * @throws IOException
	 */
	private ScoreDoc getLastScoreDoc(int pageNum,int pageSize, Query query, IndexSearcher searcher, Sort sort) throws IOException {
		if(pageNum == 1) {
			//如果是第一页的话，无需进行查询了，直接返回null
			return null;
		}
		//不是第一页的话，使用search()全查，以至于获取上一页的最后一个ScoreDoc的documentId;
		int num = pageSize * (pageNum - 1);
		TopDocs tds = searcher.search(query, num, sort);
		return tds.scoreDocs[num-1];
	}
	
	/**
	 * 处理scoreDocs数据，提取信息，转换成JSON数组
	 * @param scoreDocs
	 * @param searcher
	 * @return
	 * @throws IOException
	 */
	private JSONArray addHit2JSON(ScoreDoc[] scoreDocs, IndexSearcher searcher,String[] searchFields) throws IOException {
		JSONArray jsonArray = new JSONArray();
		
		for(int i=0; i<scoreDocs.length; i++) {
			int docId = scoreDocs[i].doc;
			Document document = searcher.doc(docId);
			JSONObject item = new JSONObject();
			for(int j=0; j<searchFields.length; j++) {
				item.put(searchFields[j], document.get(searchFields[j]));
			}
			jsonArray.add(item);
		}
		return jsonArray;
	}
	
	 /** 
     * 写入创建索引的日志
     */  
    private void WriteDate(String content) {
       SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
       String time = sf.format(new Date());
       try{  
           File file = new File(fileName);  
           if (!file.exists()) {  
                 
               System.out.println("日志文件丢失"); 
           }    
           BufferedWriter output = new BufferedWriter(new FileWriter(file,true));    
           output.write(time + "\t"+ content + "\r\n");  
           output.close();  
       } catch (Exception ex) {  
           System.out.println(ex.getMessage());  
       }     
    }  
}
