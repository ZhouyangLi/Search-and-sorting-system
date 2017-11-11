package proj1;

/* TODO: Implement and properly document
 * the Record class class. 
 * You may change the types of the data fields, if you wish. 
 * 
 * The Comparator classes are provided for your convenience. 
 * 
 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/*
 * This class contains object record and methods needed for records.
 * @author Joanna Klukowska
 * @author Zhouyang Li
 */
public class Record {
		
	private String camis;
	private String dba;
	private String boro;
	private String building;
	private String street;
	private String zipcode;
	private String phone;
	private String cuisineDescription;
	private Date inspectionDate;
	private String action;
	private String violationCode;
	private String violationDescription;
	private String criticalFlag;
	private int score;
	private String grade;
	private Date gradeDate;
	private Date recordDate;
	private String inspectionType;
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
	static SimpleDateFormat dateFormatp = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Converts the ArrayList of a line of 
	 * input data into Record. Stores data 
	 * into Record's data field.
	 * 
	 * @param entries the ArrayList of a line
	 * 		  of input data.
	 * 
	 * @author Zhouyang Li
	 */
	
	public Record ( ArrayList<String> entries ) {
		// Convert data in entries into the form of record
		camis = entries.get(0);
		
		dba = entries.get(1);
		
		boro = entries.get(2);
		
		building = entries.get(3);
		
		street = entries.get(4);
		
		zipcode = entries.get(5);
		
		phone = entries.get(6);
		
		cuisineDescription = entries.get(7);
		
		String inspectionDateString = entries.get(8);
		try {
			if (inspectionDateString.equals(""))
				inspectionDate = null;
			else	
			inspectionDate = dateFormat.parse(inspectionDateString);
		}
		catch(ParseException e){
			System.err.println(
					"ParseException: " + e.getMessage());
		}
		
		action = entries.get(9);
		
		violationCode = entries.get(10);
		
		violationDescription = entries.get(11);
		
		criticalFlag = entries.get(12);
		
		String scoreString = entries.get(13);
		try { 
			// Assign a unique score to records without scores to avoid error
			// and help future comparison
			if (scoreString.equals(""))
				score = -100;
			else
				score = Integer.parseInt(scoreString);
		}
		catch(NumberFormatException nfe) {
			System.err.println(
					"NumberFormatExcption: " + nfe.getMessage());
		}
		
		
		grade = entries.get(14);
		
		String gradeDateString = entries.get(15);
		try {
			if (gradeDateString.equals(""))
				gradeDate = null;
			else
				gradeDate = dateFormat.parse(gradeDateString);
		}
		catch(ParseException e) {
			System.err.println(
					"ParseException: " + e.getMessage());
		}
		
		String recordDateString = entries.get(16);
		try{
			if (recordDateString.equals(""))
				recordDate = null;
			else
				recordDate = dateFormat.parse(recordDateString);
		}
		catch(ParseException e) {
			System.err.println(
					"ParseException: " + e.getMessage());
		}
		
		inspectionType = entries.get(17);
		
	}

	/**
	 * Return a string of the specific inspection
	 * data in the format that specified for allRecords.
	 * 
	 * @return a string of the specific inspection
	 * 		   data in the format that specified for allRecords.
	 * @author Zhouyang Li
	 */
	public String AllToString ( ) {
		
		String inspectionDateP = "";
		try{
			if (! (inspectionDate == null))
				inspectionDateP  = dateFormat.format(this.inspectionDate);
		}
		catch(NullPointerException npe) {
			System.err.println(
					"NullPointerException: " + npe.getMessage());
		}
		
		String scoreP = "";
		if (score != -100)
			scoreP = Integer.toString(score);
		
		String gradeDateP = "";
		try{
			if (! (gradeDate == null))
				gradeDateP = dateFormat.format(this.gradeDate);
		}
		catch(NullPointerException npe) {
			System.err.println(
					"NullPointerException: " + npe.getMessage());
		}
		
		String recordDateP = "";
		try{
			if (! (recordDate == null));
				recordDateP = dateFormat.format(this.recordDate);
		}
		catch(NullPointerException npe) {
			System.err.println(
					"NullPointerException: " + npe.getMessage());
		}
		
		return "\"" + this.camis + "\",\"" + this.dba + 
				"\",\"" + this.boro + "\",\"" + this.building + 
				"\",\"" + this.street + "\",\"" + this.zipcode + 
				"\",\"" + this.phone + "\",\"" + this.cuisineDescription + 
				"\",\"" + inspectionDateP + "\",\"" + this.action + 
				"\",\"" + this.violationCode + "\",\"" + this.violationDescription + 
				"\",\"" + this.criticalFlag + "\",\"" + scoreP + 
				"\",\"" + this.grade + "\",\"" +gradeDateP + 
				"\",\"" + recordDateP + "\",\"" +
				this.inspectionType + "\"";
	}
	
	/**
	 * Return a string of the specific inspection
	 * data in the format that specified for mostRecentRecords.
	 * 
	 * @return a string of the specific inspection
	 * 		   data in the format that specified for 
	 * 		   mostRecentRecords.
	 * @author Zhouyang Li
	 */
	public String toString() {
		
		String myCamis = String.format("%10s", this.camis);
		
		String myDba = this.getFixedLengthString(dba);
		
		String myAddress = this.getFixedLengthString(this.getAddress());
		
		String myCuisineDescription = this.getFixedLengthString(cuisineDescription);
		
		// make sure data without inspection date get printed in the right format
		String myInspectionDate = "          ";
		if(! inspectionDate.equals(null))
			myInspectionDate = dateFormatp.format(this.inspectionDate);
		
		String myScore = "   ";
		if (score != -100)
			myScore = String.format("%3d", this.score);
		
		return myCamis + "\t" + myDba + "\t" +
				myAddress + "\t" + myCuisineDescription
				+ "\t" + myInspectionDate + "\t" + 
				myScore;
		
	}
	
	/**
	 * Fit String s in the width of 20 characters, truncates
	 * it if its longer than 20 characters.
	 * 
	 * @param s a String needs modification to fit 
	 * 		  in width of 20 characters.
	 * @return a String of width of 20 characters.
	 * @author Zhouyang Li
	 */
	public String getFixedLengthString(String s) {
		
		if (s == null)
			s = "";
		if (s.length() > 20)
			s = s.substring(0, 20);
		return String.format("%20s", s);
	}

	/**
	 * Return the name of the business of the Record.
	 * 
	 * @return name of the business of the Record.
	 * @author Zhouyang Li
	 */
	public String getDba() {
		
		return dba;
	}
	
	/**
	 * Return the inspection date of the Record.
	 * 
	 * @return the inspection date of the Record.
	 * @author Zhouyang Li
	 */
	public Date getInspectionDate() {
		
		return inspectionDate;
	}

	/**
	 * Return the camis in the Record.
	 * 
	 * @return the camis in the Record.
	 * @author Zhouyang Li
	 */
	public String getCamis() {
		
		return camis;
	}

	/**
	 * Return the cuisine description of the restaurant in the Record.
	 * 
	 * @return the cuisine description of the restaurant in the Record.
	 * @author Zhouyang Li
	 */
	public String getCuisineDescription() {
		
		return cuisineDescription;
	}

	/**
	 * Return the score of the inspection in the Record.
	 * 
	 * @return the score of the inspection in the Record.
	 * @author Zhouyang Li
	 */
	public int getScore() {
		
		return score;
	}
	
	/**
	 * Return the address of the restaurant in the Record.
	 * 
	 * @return the address of the restaurant in the Record.
	 * @author Zhouyang Li
	 */
	public String getAddress() {
		
		return building + " " + street;
	}
	/**
	 * Return the inspection date of the record.
	 * 
	 * @return the inspection date of the record.
	 * @author Zhouyang Li
	 */
	public Date getDate() {
		return inspectionDate;
	}
	
	/**
	 * Return the zipcode of the restaurant in the Record.
	 * 
	 * @return the zipcode of the restaurant in the Record.
	 * @author Zhouyang Li
	 */
	public String getZipcode() {
		
		return zipcode;
	}
	
}


/**
 * Defines Comparator object for the objects of type
 * record. The objects are compared by their unique
 * camis number. 
 * 
 * @author Joanna Klukowska
 * @author Zhouyang Li
 *
 */
class RecordComparatorByCamis implements Comparator<Record>{
	public int compare(Record arg0, Record arg1) { 
		int compareResult = arg0.getCamis().compareTo( arg1.getCamis() ) ;
		if ( compareResult == 0 ) {
			return  arg0.getInspectionDate().compareTo( arg1.getInspectionDate() );
		}
		else 
			return compareResult;
	}	
}



/**
 * Defines Comparator object for the objects of type
 * record. The objects are compared by the name of the business;
 * ties are resolved based on the unique camis number. 
 * 
 * @author Joanna Klukowska
 * @author Zhouyang Li
 *
 */
class RecordComparatorByDBA implements Comparator<Record>{
	
	public int compare(Record arg0, Record arg1) { 
		int compareResult = arg0.getDba().compareToIgnoreCase(arg1.getDba() ) ; 
		if ( compareResult == 0 ) {
			compareResult = arg0.getCamis().compareTo( arg1.getCamis() );
				if  ( compareResult == 0 ) {
					return arg0.getInspectionDate().compareTo( arg1.getInspectionDate() );
				}
				else
					return compareResult;
		}
		else 
			return compareResult;
	}	
}


/**
 * Defines Comparator object for the objects of type
 * record. The objects are compared by the type of cuisine;
 * ties are resolved based on the unique camis number. 
 * 
 * @author Joanna Klukowska
 * @author Zhouyang Li
 *
 */
class RecordComparatorByCuisine implements Comparator<Record>{
	
	public int compare(Record arg0, Record arg1) { 
		int compareResult = arg0.getCuisineDescription().compareToIgnoreCase(
				arg1.getCuisineDescription() ) ; 
		if ( compareResult == 0 ) {
			compareResult = arg0.getCamis().compareTo( arg1.getCamis() );
			if  ( compareResult == 0 ) {
				return arg0.getInspectionDate().compareTo( arg1.getInspectionDate() );
			}
			else
				return compareResult;
		}
		else 
			return compareResult;
	}	
}


/**
 * Defines Comparator object for the objects of type
 * record. The objects are compared by inspection scores;
 * ties are resolved based on the unique camis number. 
 * 
 * @author Joanna Klukowska
 * @author Zhouyang Li
 *
 */
class RecordComparatorByScore implements Comparator<Record>{
	
	public int compare(Record arg0, Record arg1) { 
		int compareResult = arg0.getScore() - arg1.getScore() ; 
		if ( compareResult == 0 ) {
			compareResult = arg0.getCamis().compareTo( arg1.getCamis() );
			if  ( compareResult == 0 ) {
				return arg0.getInspectionDate().compareTo( arg1.getInspectionDate() );
			}
			else
				return compareResult;
		}
		else 
			return compareResult;
	}	
}


/**
 * Defines Comparator object for the objects of type
 * record. The objects are compared by inspection date;
 * ties are resolved based on the unique camis number. 
 * 
 * @author Joanna Klukowska
 *
 */
class RecordComparatorByDate implements Comparator<Record>{
	
	public int compare(Record arg0, Record arg1) { 
		int compareResult = arg0.getInspectionDate().compareTo( arg1.getInspectionDate() ); 
		if ( compareResult == 0 ) {
			return  arg0.getCamis().compareTo( arg1.getCamis() );
		}
		else 
			return compareResult;
	}	
}











