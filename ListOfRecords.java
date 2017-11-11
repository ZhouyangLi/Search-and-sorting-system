package proj1;

/* TODO: Implement and properly document
 * this class. 
 */


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;



/**
 * This class represents a collection of records of all inspections
 * data in the input file, with methods needed for the collection.
 * 
 * @author Zhouyang Li
 *
 */
public class ListOfRecords {

	public MyArrayList list;
	public bstOfRecordsByDBA bstlist;
	public Hashtable<String, PriorityQueue<Record>> htOfPq;
	public Hashtable<String, MyArrayList> htOfNa;
	public PriorityQueue<Record> pqOfDate;
	public int size = 0;
	
	
	/**
	 * Constructor.
	 * Initializes a MyArrayList, a binary search tree by records' dba,
	 * a hashtable of priority queue, a hashtable of MyArrayList, and a 
	 * priority queue of records to store all the records.
	 */
	public ListOfRecords() {
		list = new MyArrayList();
		bstlist = new bstOfRecordsByDBA();
		htOfPq = new Hashtable<String, PriorityQueue<Record>>();
		htOfNa = new Hashtable<String, MyArrayList>();
		Comparator<Record> compD = new RecordComparatorByDate();
		pqOfDate = new PriorityQueue<Record>(compD);
	}
	
	/**
	 * Adds the record converted from a specified ArrayList
	 * to the list, the binary search tree by records' dba,
	 * the hashtable of priority queue, the hashtable of MyArrayList, 
	 * and the priority queue of records.
	 * @param words ArrayList to be converted and then adds to this list,
	 * the binary search tree by records' dba,
	 * the hashtable of priority queue, the hashtable of MyArrayList, 
	 * and the priority queue of records.
	 */
	
	public void add(ArrayList<String> words) {
		Record newRecord = new Record(words);
		list.add(newRecord);
		
		bstlist.add(newRecord);
		
		//the key for htOfNa is the combination of name and address
		String nameAndAddress = (newRecord.getDba() + newRecord.getAddress())
				.toUpperCase();
		if (htOfNa.get(nameAndAddress) == null) {
			MyArrayList myList = new MyArrayList();
			myList.add(newRecord);
			htOfNa.put(nameAndAddress, myList);
		}
		else {
			htOfNa.get(nameAndAddress).add(newRecord);
		}
		
		String myZipcode = newRecord.getZipcode();
		Comparator<Record> compS = new RecordComparatorByScore();
		if (htOfPq.get(myZipcode) == null) {
			PriorityQueue<Record> myPq = new PriorityQueue<Record>(compS);
			myPq.add(newRecord);
			htOfPq.put(myZipcode, myPq);
		}
		else {
			htOfPq.get(myZipcode).add(newRecord);
		}
		
		pqOfDate.add(newRecord);
		
		size ++;
	}
	
	/**
	 * Return a String of records in the format required
	 * by sortAll.
	 * 
	 * @return a String of records that in the format required
	 * by sortAll.
	 * @author Zhouyang Li
	 */

	public String toString() {
		
		return list.AllToString();
	}

	/**
	 * Sorts the collection of records based on the given key. 
	 * 
	 * @param key a String based on which the records are sorted.
	 * @author Zhouyang Li
	 */
	public void sort(String key) {
		
		list.sort(key);
	}

	/**
	 * Merge sort the collection of records based on the given key.
	 * @param key a String based on which the records are sorted.
	 * @author Zhouyang Li
	 */
	public void sortFaster(String key){
		list.sortFaster(key);
	}
	
	/**
	 * Return a MyArrayList of records that have the name specified in
	 * the given key.
	 * 
	 * @param key the name of restaurants to be found.
	 * @return records that have the name specified in the given key.
	 * @author Zhouyang Li
	 */
	public MyArrayList findByName(String key) {
		
		
		MyArrayList results = new MyArrayList();
	
		DbaList myResults = bstlist.get(key);
		if (myResults != null) {
			for (Record r : myResults.getList()) {
				results.add(r);
			}
		}
		
		/*
		 * for (int i=0; i<list.size(); i++) {
			// add records in the MyArrayList of inspection data that have the given
			// name key to myResults
			Record myRecord = list.get(i);
			
			if ( myRecord.getDba().equalsIgnoreCase(key)) {
				
				results.add(myRecord);	
			}
			
		}
		*/
		
		return results;
		
	}

	/**
	 * Return a MyArrayList of record(s) of inspection with specified name key1 and 
	 * address key2, the number of records returned depends on dateFlag: if it is "first",
	 * then return the first record with the specified name and address; if it is "last",
	 * then return the last record with the specifies name and address; if it is 
	 * "all", then return all records with the specified name and address.
	 * 
	 * Why change in code is beneficial: instead of traversing through out the whole
	 * collection of records, the updated code find the certain location where all the
	 * records we want are stored, and don't need to find through other unwanted records.
	 * So the new code are more efficient than the old one.
	 * 
	 * @param key1 the name of inspection to be found.
	 * @param key2 the address of inspection to be found, in the format of building
	 * 		  + " " + street.
	 * @param dateFlag determine the record(s) to be return, can be first, last, or all.
	 * @return record(s) of inspection with specified name key1 and address key2,
	 * 		   the number of records returned depends on dateFlag: if it is "first", then
	 * 		   return the first record with the specified name and address; if it is "last",
	 *		   then return the last record with the specifies name and address; if it is 
	 *		   "all", then return all records with the specified name and address.
	 * @author Zhouyang Li
	 */
	public MyArrayList findByNameAddress(String key1, String key2, String dateFlag) {
		MyArrayList results = new MyArrayList();
		String myNAndA = (key1 + key2).toUpperCase();
		
		MyArrayList myResults = htOfNa.get(myNAndA);
		myResults.sortFaster("date");
		// add the first inspected record that has the given name key1 and address
		// key2 to results
		if (dateFlag.equalsIgnoreCase("first")) {
			
			Record firstRecord = myResults.get(0);
			
			results.add(firstRecord);	
		}
		
		// add the last inspected record that has the given name key1 and address
		// key2 to results
		else if (dateFlag.equalsIgnoreCase("last")) {
			
			int myResultsSize = myResults.size();
			
			Record lastRecord = myResults.get(myResultsSize - 1);
			
			results.add(lastRecord);	
		}
		
		// add all inspected records that have the given name key1 and address
		// key2 to results
		else if (dateFlag.equalsIgnoreCase("all")) {
			
			results = myResults; 
		}
		
		
		return results;
		
			

		/*
		// sort the data by date first to meet the command for "first" or "last" record 
		this.sort("DATE");

		MyArrayList myResults = new MyArrayList();
		
		MyArrayList results = new MyArrayList();
		
		for (int i=0; i<size; i++) {
			
			// add records in the MyArrayList of inspection data that have the given
			// name key1 and given address key2 to myResults
			Record myRecord = list.get(i);
			String myDba = myRecord.getDba();
			String myAddress = myRecord.getAddress();
			if ( myDba.equalsIgnoreCase(key1) && 
					myAddress.equalsIgnoreCase(key2)) {
				
				myResults.add(myRecord);
			}
		}
		
		// add the first inspected record that has the given name key1 and address
		// key2 to results
		if (dateFlag.equalsIgnoreCase("first")) {
			
			Record firstRecord = myResults.get(0);
			
			results.add(firstRecord);	
		}
		
		// add the last inspected record that has the given name key1 and address
		// key2 to results
		else if (dateFlag.equalsIgnoreCase("last")) {
			
			int myResultsSize = myResults.size();
			
			Record lastRecord = myResults.get(myResultsSize - 1);
			
			results.add(lastRecord);	
		}
		
		// add all inspected records that have the given name key1 and address
		// key2 to results
		else if (dateFlag.equalsIgnoreCase("all")) {
			
			results = myResults; 
		}
		
		
		return results;
		*/
	}
	
	/**
	 * Return a MyArrayList of records of inspection that are inspected 
	 * in the first numOfDates of inspection. Records in each date are sorted in
	 * ascending order of date.
	 * 
	 * Why change in code beneficial: In the old code, we need to go through the 
	 * whole collection of records to sort them by date to perform further finding
	 * process. However, in the new code, the structure of the collection is already
	 * sorted by date. So we don't need to sort them again or visit the unwanted records,
	 * both of which take some time. So the new code is more efficient.
	 * 
	 * @param numOfDates the number of inspection days that the 
	 * 		  returned records should be in.
	 * @return records of inspection that are inspected in the first
	 * 		   numOfDates of inspection. Records in each date are sorted in
	 * 		   ascending order of date.
	 * @author Zhouyang Li
	 */

	public MyArrayList findByDate(int numOfDates) {	
		
		MyArrayList results = new MyArrayList();
		Date curDate = pqOfDate.peek().getDate();
		int counter = 1;
		while (pqOfDate.peek() != null) {
			
			if (results.size() >= this.size){
				break;
			}
			
			else if (pqOfDate.peek().getDate().equals(curDate) == false) {
				counter ++;
				curDate = pqOfDate.peek().getDate();
			}
			
			if(counter <= numOfDates) {
				Record curRecord = pqOfDate.poll();
				results.add(curRecord);
			}
			else
				break;
		}
		
		for (Record r : results){
			pqOfDate.add(r);
		}
		return results;
		
		/*
		MyArrayList results = new MyArrayList();
		
		this.sort("DATE");
		
		
		for (int i=0; i<numOfDates; i++) {
			
			// the size of results of the first i days is the index 
			// of the first record that is inspected at the first i+1 days
			int currentNumOfRecords = results.size();
			if (currentNumOfRecords >= this.size)
				break;
			
			Date theDate = (list.get(currentNumOfRecords)).getInspectionDate();
			
			for (int j=0; j<size; j++) {
				
				// add records in the MyArrayList of inspection data that are 
				// inspected in theDateto to results
				Record currentRecord = list.get(j);
				
				if (currentRecord.getInspectionDate().equals(theDate)) {
					
					results.add(currentRecord);
				}
			
			}
		
			
		}
		
		results.sort("DATE");
		return results;
		*/
	}

	/**
	 * Return a MyArrayList of records of inspection that with in
	 * the given zipcode with score less or equal to the given score,
	 * sorted in ascending order of score.
	 * 
	 * Why change in code beneficial: In the old code, we need to traverse
	 * through the collection of record to find all the records that satisfied
	 * the restriction on zipcode and score. However, in the new code, all records
	 * of the same zipcode are stored in the same location with a ascendingly sorted
	 * by score structure, so we only need to find the location of the given zipcode
	 * and add records to results until the score of the current record is bigger than
	 * the given score restriction. Thus, with the new code, we don't need to visit
	 * neither records of other zipcode nor records with bigger score than given, and
	 * it saves time. So the new code is more efficient.
	 * 
	 * @param score the limit of score that returned records should score
	 * 		  less or equal to.
	 * @param zipcode the zipcode that the returned records should have.
	 * @return a MyArrayList of records of inspection that with in
	 * 		   the given zipcode with score less or equal to the given score,
	 * 		   sorted in ascending order of score.
	 * @author Zhouyang Li
	 */
	public MyArrayList findByScore(int score, String zipcode) {
		MyArrayList results = new MyArrayList();
		PriorityQueue<Record> myPq = htOfPq.get(zipcode);
		while (myPq.peek().getScore() <= score) {
			results.add(myPq.poll());
		}
		for (Record r : results) {
			myPq.add(r);
		}
		return results;
		
		/*
		 * MyArrayList results = new MyArrayList();
		
		
		
		for (int i=0; i<size; i++) {
			
			// add records in the MyArrayList of inspection data that have scores
			// smaller than given score and have the given zipcode to results
			Record currentRecord = list.get(i);
			String currentZipcode = currentRecord.getZipcode();
			int currentScore = currentRecord.getScore();
			if (zipcode.equals(currentZipcode) && currentScore != -100
					&& score >= currentScore)
				results.add(currentRecord);
			
		}
		results.sort("score");
		return results;
		*/
		
	}
	
	/**
	 * Determines if the ListOfRecords is sorted based on the specific key.
	 * @param key a String based on which the ListOfReocrds should be sorted.
	 * @return true if the ListOfRecords is sorted based on the key,
	 * 		   false if is not sorted based on the key.
	 * @author Zhouyang Li
	 */
	public boolean isSorted(String key) {
		Comparator<Record> myComp = null;
		if (key.equalsIgnoreCase("CAMIS"))
			myComp = new RecordComparatorByCamis();
		else if (key.equalsIgnoreCase("DBA"))
			myComp = new RecordComparatorByDBA();
		else if (key.equalsIgnoreCase("CUISINE"))
			myComp = new RecordComparatorByCuisine();
		else if (key.equalsIgnoreCase("SCORE"))
			myComp = new RecordComparatorByScore();
		else if (key.equalsIgnoreCase("DATE"))
			myComp = new RecordComparatorByDate();
		for (int i=0; i<size-1; i++) {
			// check if the MyArrayList is sorted as wanted
			if (myComp.compare(list.get(i) ,list.get(i+1)) > 0)
				return false;
		}
		return true;
	}

}
