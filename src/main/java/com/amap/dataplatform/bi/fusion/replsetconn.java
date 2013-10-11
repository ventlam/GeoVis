package com.amap.dataplatform.bi.fusion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class replsetconn {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public void geoIndex()
	{
		
	}
	public static void findInCircle(DBCollection collection )
	{
		
		
		System.out.println("findWithinCircle\n----------------------\n");
		List<Object> circle = new ArrayList<Object>();
		//中心点
		circle.add(new Double[]{116.0, 39.0});
		//半径
		circle.add(10);
		
		BasicDBObject geoquery = new BasicDBObject("coordinates",new BasicDBObject(com.mongodb.QueryOperators.WITHIN,new BasicDBObject(com.mongodb.QueryOperators.CENTER, circle)));
		//System.out.println("geoquery"+query);
		//BasicDBObject geoquery = new BasicDBObject();
		//DBCursor cursor = collection.find(geoquery);
		BasicDBObject query = new BasicDBObject("name","北京");
		DBCursor cursor = collection.find(query);
		 try {
			   while(cursor.hasNext()) {
			       System.out.println(cursor.next());
			   }
			} finally {
			   cursor.close();
			}
	}
	/*public static void printOutputs(BasicDBObject query,DBCollection collection )
	{
	 DBCursor cursor = collection.find(query);
	 //List<BasicDBList> outputs = new ArrayList<BasicDBList>();
	 try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}

	 for (int y = 9; y >= 0; y--)
	 {
	 String s = "";
	 for (int x = 0; x < 10; x++)
	 {
	 boolean found = false;
	 for (BasicDBList obj : outputs)
	 {
	 double xVal = (Double) obj.get(0);
	 double yVal = (Double) obj.get(1);
	 if (yVal == y && xVal == x)
	 {
	 found = true;
	 }
	 }
	 if(found) {
	 s = s + " @";
	 } else {
	 s = s + " +";
	 }
	 }
	 System.out.println(s);
	 }
	}*/
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		 try {
			 //add mongodb servers list
			addrs.add( new ServerAddress( "mongodb0.amap.com" , 27017 ) );
			addrs.add( new ServerAddress( "mongodb1.amap.com" , 27017 ) );
			addrs.add( new ServerAddress( "mongodb2.amap.com" , 27017 ) );
			//construct mongo connection
			Mongo mongo = new Mongo( addrs );
			//set up write concern, w>=2 means sync two server?
			WriteConcern concern = new WriteConcern(2);
			mongo.setWriteConcern(concern);
			//set slave ok;
			mongo.slaveOk();
			//get fusion db.
			String db = "fusion";
			String col ="place_1374204082003";
			DB monDb = mongo.getDB(db);
		    //get java7k collection	
			DBCollection mycol =  monDb.getCollection(col);
/*			QueryBuilder qb = new QueryBuilder();
			QueryBuilder.start("the_geom");
			qb.near(116.0, 39.0, 1);
			DBCursor cursor = mycol.find(qb.get());
			 try {
				   while(cursor.hasNext()) {
				       System.out.println(cursor.next());
				   }
				} finally {
				   cursor.close();
				}*/
			findInCircle(mycol);
			//mycol.ensureIndex(new BasicDBObject("coordinates","2d"),"geoindex");
			List<DBObject> list = mycol.getIndexInfo();

			for (DBObject o : list) {
			   System.out.println(o);
			}
			//parsing csv with opencsv driver
			/*CSVReader reader = new CSVReader(new FileReader("D:/7k.csv"), ',', '\'', 1);
			String [] nextLine;
			//read each line  into mongodb 
			//TODO initate it as hashmap to increase insert speed.
			 while ((nextLine = reader.readNext()) != null) {
				   //construct a  bson obejct.
			    	BasicDBObject doc = new BasicDBObject();
			    	//only get 2 columns 
			    	doc.append("x", nextLine[2]).append("y", nextLine[1]);
			    	if(nextLine.length<36)
			    	{  
			    		System.out.println(nextLine.length);
			    	
			    	}
			       //insert document into collection
			    	mycol.insert(doc);
			    }
			  */
			// mycol.insert(obj);
			// System.out.println("total records:"+mycol.find().count());
			
			 //close connection
			 mongo.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

}
