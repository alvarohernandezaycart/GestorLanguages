package com.gestor.excelToJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestor.caching.Dao;
import com.gestor.caching.MessageResource;

public class ExcelTojava {
	
	private Workbook workbook;
	private Sheet firstSheet;

	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}

	public List<MessageResource> save(String excelFilePath,List<MessageResource> list) throws IOException {
	    List<MessageResource> listMessages = new ArrayList<MessageResource>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    workbook = new XSSFWorkbook(inputStream);
	    firstSheet = workbook.getSheetAt(0);
	    int i = 1;
	        for (MessageResource m : list) {
	    	    Row header = firstSheet.createRow(i);
	    	    header.createCell(1).setCellValue(m.getCode());
	    	    header.createCell(2).setCellValue(m.getLocale());
	    	    header.createCell(3).setCellValue(m.getText());
	    	    header.createCell(4).setCellValue(m.getProyect()); 
	    	    i++;
	        }
	        
	    inputStream.close();
	    FileOutputStream outFile =new FileOutputStream(new File(excelFilePath));
	    workbook.write(outFile);
	    outFile.close();
	   
	 
	    return listMessages;
	} 


	public List<MessageResource> load(String excelFilePath) throws IOException {
	    List<MessageResource> listMessages = new ArrayList<MessageResource>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

	    workbook = new XSSFWorkbook(inputStream);
	    firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        MessageResource m = new MessageResource();
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	            
	            switch (columnIndex) {
	            case 1:
	                m.setCode((String) getCellValue(nextCell));
	                break;
	            case 2:
	                m.setLocale((String) getCellValue(nextCell));
	                break;
	            case 3:
	                m.setText((String) getCellValue(nextCell));
	                break;
	            case 4:
	                m.setProyect((String) getCellValue(nextCell));
	                break;
	            }
	 
	 
	        }
	        listMessages.add(m);
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return listMessages;
	} 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExcelTojava e = new ExcelTojava();
		Dao dao = new Dao();
		String excelFilePath = "src/main/resources/com/excel/Traducciones.xlsx";
		List<MessageResource> listMessages = null;
		try {
			listMessages = e.load(excelFilePath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (MessageResource messageResource : listMessages) {
			System.out.println(messageResource.getCode()+messageResource.getLocale()+messageResource.getProyect()+messageResource.getText());
		}
		
		for (MessageResource messageResource : listMessages) {
			dao.openCurrentSessionwithTransaction();
			dao.persistMessageResource(messageResource);
			dao.closeCurrentSessionwithTransaction();
		}
		
		try {
			e.save(excelFilePath, dao.findAllMessageResource());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
