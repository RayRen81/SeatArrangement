/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package org.rropen.seat.arrangement;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.rropen.seat.arrangement.core.Arrange;
import org.rropen.seat.arrangement.io.ExcelIO;
import org.rropen.seat.arrangement.model.Class;
import org.rropen.seat.arrangement.model.ExamRoom;

/**
 * @author Ray
 * @version $Revision: $
 */
public class Main {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	/**
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
     * 
     */
	public static void main(String[] args) throws BiffException, IOException, RowsExceededException, WriteException {
		List<Class> classes = new ExcelIO().getStudentsInClasses(new File("excel/in/students.xls"));
		List<ExamRoom> rooms = new Arrange().arrangeExamSeat(classes, 12, 6, 5);
		new ExcelIO().generateStudentsWithRoomSeatPerClass(new File("excel/out/studentsPerClass.xls"), classes);
		new ExcelIO().generateExamineeListPerRoom(new File("excel/out/examineePerRoom.xls"), rooms);
    }
	
}
