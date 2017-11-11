package proj1;

import java.util.LinkedList;
import java.util.List;


/**
 * This class stores all records of the same name in a list, with methods 
 * needed to manage these lists.
 * @author Joanna Klukowska
 * @author Zhouyang Li
 *
 */

public class DbaList implements Comparable<DbaList> {

	String dba;
	List<Record> list;
	
	/**
	 * Creates a DbaList of dba of the record and include the record into the
	 * DbaList.
	 * @param r The record the DbaList supposed to include.
	 * @throws IllegalArgumentException The record the Dbalist supposed to include
	 * is null.
	 * @author Joanna Klukowska
	 */
	public DbaList(Record r) throws IllegalArgumentException {
		if ( r == null ) throw new IllegalArgumentException ("Error: cannot create " 
				+ "DbaList with a null Record object.");
		dba = r.getDba();
		list = new LinkedList<Record>();
		list.add(0,r);
	}
	
	/**
	 * Creates an empty DbaList of the given dba.
	 * @param dba the dba the DbaList supposed to have.
	 * @author Joanna Klukowska
	 */
	public DbaList(String dba) {
		this.dba = dba;
		list = new LinkedList<Record>();
	}
	
	/**
	 * Return the dba of the DbaList.
	 * @return the dba of the DbaList.
	 * @author Joanna Klukowska
	 */
	public String getDba() {
		return dba;
	}
	
	/**
	 * Return the list of records in the DbaList.
	 * @return the list of records in the DbaList.
	 * @author Joanna Klukowska
	 */
	public List<Record> getList(){
		return list;
	}
	
	/**
	 * Add the record r to the DbaList if the record r has the same dba as
	 * the DbaList. 
	 * @param r the record that is supposed to be added to the DbaList.
	 * @return true if the record r has the same dba as the DbaList and is 
	 * added to the DbaList; false if the record r doesn't have the same dba
	 * and is not added to the DbaList.
	 * @author Joanna Klukowska
	 */
	public boolean add (Record r) {
		if (r.getDba().equalsIgnoreCase(dba)) {
			list.add(0,r);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Compare this DbaList to another DbaList based on their dba.
	 * @author Joanna Klukowska
	 */
	public int compareTo(DbaList other) {
		return this.dba.compareTo(other.dba);
	}

}