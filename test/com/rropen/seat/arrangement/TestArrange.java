/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.rropen.seat.arrangement;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Test;
import org.rropen.seat.arrangement.core.Arrange;
import org.rropen.seat.arrangement.io.ExcelIO;
import org.rropen.seat.arrangement.model.Class;
import org.rropen.seat.arrangement.model.ExamRoom;
import org.rropen.seat.arrangement.model.Student;

/**
 * @author Ray
 * @version $Revision: $
 */
public class TestArrange {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Test
	public void testArrange() throws BiffException, IOException{
		List<Class> classes = new ExcelIO().getStudentsInClasses(new File("excel/in/students.xls"));
		List<ExamRoom> rooms = new Arrange().arrangeExamSeat(classes, 12, 6, 5);
		
		int numOfExaminee = 0;
		for(ExamRoom room : rooms) {
			for(int i = 0; i < 6; i ++) {
				for(int j = 0; j < 5; j++) {
					if(room.getExaminee()[i][j] != null) {
						++numOfExaminee;
					}
				}
			}
		}
		
		int numOfStudent = 0;
		for(Class aClass : classes) {
			numOfStudent += aClass.getNativeStudentsMap().values().size();
		}
		
		assertEquals(numOfStudent, numOfExaminee);
		
		for(Class aClass : classes) {
			for(Student student : aClass.getNativeStudentsMap().values()){
				int seatNo = student.getMyExamRoom().getSeatsByNo(student.getId());
				int col = (seatNo - 1) / 6;
				int row = (seatNo - 1) % 6 ;
				Student examine = student.getMyExamRoom().getExaminee()[row][col];
				assertEquals(student.getId(), examine.getId());
			}
		}
		
	}

}
