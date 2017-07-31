package com.ctb;

import java.io.File;

import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Datasheet {
	 private Workbook wbook;
	    private WritableWorkbook wwbCopy;
	    private WritableSheet shSheet;

	    public void readExcel()
	    {
	        try
	        {
	            wbook = Workbook.getWorkbook(new File("C:\\Users\\nessk\\Desktop\\testSampleData.xls"));
	            wwbCopy = Workbook.createWorkbook(new File("C:\\Users\\nessk\\Desktop\\testSampleDataCopy.xls"), wbook);
	            shSheet = wwbCopy.getSheet(0);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	   
	    public void setValueIntoCell(String strSheetName, int iColumnNumber, int iRowNumber, String strData) throws WriteException
	    {
	        CellView cell = new CellView();
	        CellView cell2 = new CellView();
	    	WritableSheet wshTemp = wwbCopy.getSheet(strSheetName);
	        Label labTemp = new Label(iColumnNumber, iRowNumber, strData);
	               
	        try 
	        {
	            wshTemp.addCell(labTemp);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        cell = wshTemp.getColumnView(iColumnNumber);
	      //  cell.setAutosize(true);
	        cell.setSize(5000);
	      
	        cell2 = wshTemp.getRowView(iRowNumber);
	        cell2.setSize(750);
	        
	        wshTemp.setColumnView(iColumnNumber, cell);
	        wshTemp.setRowView(iRowNumber, cell2);
	    }
	   
	    public void closeFile()
	    {
	        try 
	        {
	            // Closing the writable work book
	            wwbCopy.write();
	            wwbCopy.close();

	            // Closing the original work book
	            wbook.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }

}
