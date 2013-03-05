/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package org.rropen.seat.arrangement.model;

import java.util.HashMap;
import java.util.Map;



/**
 * @author Ray
 * @version $Revision: $
 */
public class ExamRoom {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String name;
	
	private Student[][] examinees;
	
	private Map<String, int[]> seatsByNo = new HashMap<String, int[]>();
	
	private int row;
	
	private int column;
	
	/**
     * @param name
     */
    public ExamRoom(String name, int row, int column) {
	    this.name = name;
	    this.row = row;
	    this.column = column;
	    examinees = new Student[this.row][this.column];
    }

	public String getName() {
    	return name;
    }

	public Student[][] getExaminee() {
    	return examinees;
    }
	
	protected void setExaminee(Student student, int row, int column){
		this.examinees[row][column] = student;
		seatsByNo.put(student.getId(), new int[]{row, column});
	}
	
	public int getSeatsByNo(String studentNo) {
    	return seatsByNo.get(studentNo)[0]  + seatsByNo.get(studentNo)[1] * this.row + 1;
    }
	
	public int getRow() {
    	return row;
    }

	public int getColumn() {
    	return column;
    }
	
}
