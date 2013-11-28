/*
 * @author ventlam
 * @email  ventlcc@gmail.com
 * @blog   www.ventlam.org
 */
package com.amap.dataplatform.bi.fusion;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/*
 * connect to mongodb at 10.2.134.108 without rs.
 */
public class SimpleCon {

	public SimpleCon(String db, int port) {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			MongoClient moClient = new MongoClient("10.2.134.108", 27017);
			String db = "geovis";
			String col = "province";
			String userCol = "newuser-gis-201309";
			DB monDb = moClient.getDB(db);
			// get all collection
			Set<String> colls = monDb.getCollectionNames();

			for (String s : colls) {
				System.out.println(s);
			}
			// get province collection
			DBCollection mycol = monDb.getCollection(col);
			DBCollection newcol = monDb.getCollection(userCol);
			List<DBObject> list = mycol.getIndexInfo();

			for (DBObject o : list) {
				System.out.println(o);
			}
			/*
			 * DBObject myDoc = mycol.findOne(); System.out.println(myDoc);
			 */
			DBCursor cursor = mycol.find();
			DBCursor cursor1 = newcol.find();
			/*
			 * try { while (cursor.hasNext()) {
			 * System.out.println(cursor.next()); } } finally { cursor.close();
			 * }
			 */
			//
			BasicDBObject query = new BasicDBObject("id", 29);
			BasicDBObject uquery = new BasicDBObject("x", 123.591167);
			cursor1 = newcol.find(uquery);

			try {
				while (cursor1.hasNext()) {
					System.out.println(cursor1.next());
				}
			} finally {
				cursor1.close();
			}
	/*		cursor = mycol.find(query);

			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}
			} finally {
				cursor.close();
			}*/

			// now use a range query to get a larger subset
			query = new BasicDBObject("id", new BasicDBObject("$gt", 31)); 
			cursor = mycol.find(query);
			//DBObject fujianprovince = new BasicDBObject();
			try {
				while (cursor.hasNext()) {
					// fujianprovince = cursor.next();
					System.out.println(cursor.next());
					/*BasicDBObject spatialquery = new BasicDBObject("id", 29);*/
				}
			} finally {
				cursor.close();
			} //

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
