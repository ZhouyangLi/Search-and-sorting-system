package proj1;

/* TODO: Implement and properly document
 * this class. 
 */


import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class contains elements in the from of record from inspections
 * data in the input file, with methods needed for these data.
 * 
 * @author Zhouyang Li
 *
 */
@SuppressWarnings("serial")
public class MyArrayList extends ArrayList<Record> {
	
	/**
	 * Sort the given MyArrayList in ascending order based on the key given.
	 * 
	 * @param key a String based on which the MyArrayList is sorted.
	 * @author Zhouyang Li
	 */
	public void sort(String key) {
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
		
		// use selection sort to sort these MyArrayList
		for (int i=0; i<this.size()-1; i++) {
			int minIndex = i;
			for (int j=i+1; j<size(); j++) {
				if (myComp.compare(this.get(minIndex) ,this.get(j)) > 0){
					minIndex = j;
				}
			}
			if (minIndex != i) {
				Record tempRecord = this.get(i);
				this.set(i, get(minIndex));
				this.set(minIndex, tempRecord);
				}
		}
		
	}
	
	/**
	 * Choose a comparator based on the key given to merge sort
	 * the given MyArrayList in ascending order.
	 * @param key a String based on which the MyArrayList is sorted.
	 * @author Zhouyang Li
	 */
	public void sortFaster(String key) {
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
		this.sort(myComp);
	}
	
	/**
	 * A wrapper method for user to call the real recursive merge
	 * sort method.
	 * @param comp the specific comparator to use for sorting.
	 * @author Zhouyang Li
	 */
	public void sort(Comparator<? super Record> comp) {
		sortRec(comp, 0, this.size()-1);
	}
	
	/**
	 * A recursive merge sort method that sort the MyArrayList with
	 * the specific comparator.
	 * @param comp the specific comparator to use for sorting.
	 * @param firstIndex the first index of the MyArrayList.
	 * @param lastIndex the last index of the MyArrayList.
	 * @author Zhouyang Li
	 */
	private void sortRec(Comparator<? super Record> comp, int firstIndex, int lastIndex) {
		if (firstIndex < lastIndex) {
			// split the MyArrayList
			int mid = (firstIndex + lastIndex)/2;
			sortRec(comp, firstIndex, mid);
			sortRec(comp, mid+1, lastIndex);
			merge(comp, firstIndex, mid, mid+1, lastIndex);
		}
	}
	
	/**
	 * Merge 2 sorted parts of MyArrayList together.
	 * @param comp the specific comparator to use for sorting.
	 * @param leftFirst the first index of one sorted part of the MyArrayList. 
	 * @param leftLast the last index of one sorted part of the MyArrayList.
	 * @param rightFirst the first index of the other sorted part of the MyArrayList.
	 * @param rightLast the last index of the other sorted part of the MyArrayList.
	 * @author Zhouyang Li
	 */
	public void merge(Comparator<? super Record> comp, int leftFirst, 
			int leftLast, int rightFirst, int rightLast) {
		MyArrayList temp = new MyArrayList();
		int indexLeft = leftFirst;
		int indexRight = rightFirst;
		// merge the MyArrayList
		while (indexLeft <= leftLast && indexRight <= rightLast) {
			if (comp.compare(this.get(indexLeft) ,this.get(indexRight)) < 0) {
				temp.add(this.get(indexLeft));
				indexLeft ++;
			}
			else {
				temp.add(this.get(indexRight));
				indexRight ++;
			}
		}
		while (indexLeft <= leftLast) {
			temp.add(this.get(indexLeft));
			indexLeft++;
		}
		while (indexRight <= rightLast) {
			temp.add(this.get(indexRight));
			indexRight++;
		}
		int k = 0;
		for (int i=leftFirst; i<=rightLast; i++) {
			Record mergeRecord = temp.get(k++);
			this.set(i, mergeRecord);
		}
	}
	/**
	 * Return a string of inspection data within the given MyArrayList
	 * in the format that specified for allRecords.
	 * 
	 * @return a string of inspection data within the given MyArrayList
	 *		   in the format that specified for allRecords.
	 * @author Zhouyang Li
	 */
	public String AllToString ( ) {
		String s = "";
		for(int i=0; i<this.size(); i++) {
			s += (this.get(i)).AllToString() + "\n";
			
		}
		return s;
	}
	
	/**
	 * Return a string of inspection data within the given MyArrayList
	 * in the format that specified for mostRecentRecords.
	 * 
	 * @return a string of inspection data within the given MyArrayList
	 * 		   in the format that specified for mostRecentRecords.
	 * @author Zhouyang Li
	 */
	public String toString() {
		
		String s = "";
		for(int i=0; i<this.size(); i++) {
			
			s += (this.get(i)).toString() + "\n";
		}
		return s;
	}

}
