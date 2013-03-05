/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package org.rropen.seat.arrangement.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.rropen.seat.arrangement.model.Class;
import org.rropen.seat.arrangement.model.ExamRoom;
import org.rropen.seat.arrangement.model.Student;

/**
 * @author Ray
 * @version $Revision: $
 */
public class ExcelIO {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	public List<Class> getStudentsInClasses(File f) throws BiffException, IOException{
		List<Class> classes = new ArrayList<Class>();

		WorkbookSettings workSettings = new WorkbookSettings();
		workSettings.setEncoding("iso-8859-1");
		Workbook workbook = Workbook.getWorkbook(f, workSettings);
		try {
			for (Sheet sheet : workbook.getSheets()) {
				Class aClass = new Class(sheet.getName());
				classes.add(aClass);
				for (int i = 0; i < sheet.getRows(); i++) {
					String studentNo = sheet.getCell(0, i).getContents();
					if (studentNo == null || studentNo.trim().equals("")) {
						continue;
					}
					try {
						Integer.parseInt(studentNo);
					} catch (NumberFormatException e) {
						throw new RuntimeException("Unexpected Cell contents " + studentNo + " in sheet "
						        + sheet.getName());
					}

					String name = sheet.getCell(1, i).getContents();
					String sex = sheet.getCell(2, i).getContents();
					aClass.addStudents(studentNo, name, sex);
				}
			}

			return classes;
		} finally {
			workbook.close();
		}
		
	}
	
	public void generateExamineeListPerRoom(File f, List<ExamRoom> rooms) throws IOException, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(f);
		
		for(int i = 0; i < rooms.size(); i++) {
			WritableSheet sheet = workbook.createSheet(rooms.get(i).getName(), i);
			
			for(int j = 0; j < rooms.get(i).getRow(); j++) {
				for(int k = 0; k < rooms.get(i).getColumn(); k++){
					if(j == 0){
						Label seatCell = new Label(k*6+0, j, "座位号");
						Label noCell = new Label(k*6+1, j, "学号");
						Label nameCell = new Label(k*6+2, j, "班级");
						Label sexCell = new Label(k*6+3, j, "性别");
						Label classCell = new Label(k*6+4, j, "班级");
						
						sheet.addCell(seatCell);
						sheet.addCell(noCell);
						sheet.addCell(nameCell);
						sheet.addCell(sexCell);
						sheet.addCell(classCell);
					}
					
					Student examinee = rooms.get(i).getExaminee()[j][k];
					if(examinee == null) {
						continue;
					}
					Label seatCell = new Label(k*6+0, j+1, String.valueOf(examinee.getMyExamRoom().getSeatsByNo(examinee.getId())));
					Label noCell = new Label(k*6+1, j+1, examinee.getId());
					Label nameCell = new Label(k*6+2, j+1, examinee.getName());
					Label sexCell = new Label(k*6+3, j+1, examinee.getSex());
					Label classCell = new Label(k*6+4, j+1, examinee.getMyClass().getClassName());
					sheet.addCell(seatCell);
					sheet.addCell(noCell);
					sheet.addCell(nameCell);
					sheet.addCell(sexCell);
					sheet.addCell(classCell);
				}
			}
		}
		
		workbook.write();
		workbook.close();
		
	}
	
	public void generateStudentsWithRoomSeatPerClass(File f, List<Class> classes) throws IOException, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(f);
		
		for(int i = 0; i < classes.size(); i++){
			WritableSheet sheet = workbook.createSheet(classes.get(i).getClassName(), i);
			int j = 0;
			for(String studentNo : classes.get(i).getNativeStudentsMap().keySet()){
				if(j == 0) {
					Label noCell = new Label(0, j, "学号");
					Label nameCell = new Label(1, j, "姓名");
					Label sexCell = new Label(2, j, "性别");
					Label roomCell = new Label(3, j, "考场");
					Label seatCell = new Label(4, j, "座位号");
					sheet.addCell(noCell);
					sheet.addCell(nameCell);
					sheet.addCell(sexCell);
					sheet.addCell(roomCell);
					sheet.addCell(seatCell);
				}
				Student student = classes.get(i).getNativeStudentsMap().get(studentNo);
				Label noCell = new Label(0, j+1, studentNo);
				Label nameCell = new Label(1, j+1, student.getName());
				Label sexCell = new Label(2, j+1, student.getSex());
				Label roomCell = new Label(3, j+1, student.getMyExamRoom().getName());
				Label seatCell = new Label(4, j+1, String.valueOf(student.getMyExamRoom().getSeatsByNo(studentNo)));
				j++;
				sheet.addCell(noCell);
				sheet.addCell(nameCell);
				sheet.addCell(sexCell);
				sheet.addCell(roomCell);
				sheet.addCell(seatCell);
			}
		}
		
		workbook.write();
		workbook.close();
	}

}
