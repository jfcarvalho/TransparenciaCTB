package com.ctb;
import java.io.File; 
import java.io.IOException; 
import jxl.Cell; import jxl.Sheet; 
import jxl.Workbook; 
import jxl.read.biff.BiffException;



public class Alimentar {

	public static void main(String[] args)  throws BiffException, IOException{
		
		{
			 Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\nessk\\Downloads\\A.xls")); 
			 Sheet sheet = workbook.getSheet(0);         /**         * Numero de linhas com dados do xls         */         
			 int linhas = sheet.getRows();
			 int colunas = sheet.getColumns();
			 for(int i = 0; i < 23; i++  )         {             
			 Cell celula1 = sheet.getCell(0, 14+i);   
			 Cell celula2 = sheet.getCell(1, 14+i);
			 Cell celula3 = sheet.getCell(2, 14+i);
			 Cell celula4 = sheet.getCell(3, 14+i);
			 Cell celula5 = sheet.getCell(4, 14+i);
			 Cell celula6 = sheet.getCell(5, 14+i);
			 Cell celula7 = sheet.getCell(6, 14+i);
			 Cell celula8 = sheet.getCell(7, 14+i);
			 Cell celula9 = sheet.getCell(8, 14+i);
			 Cell celula10 = sheet.getCell(9, 14+i);
			 Cell celula11 = sheet.getCell(10, 14+i);
			 Cell celula12 = sheet.getCell(11, 14+i);
			 System.out.println("==================== Inicio de Linha=======");
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula1.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula2.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula3.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula4.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula5.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula6.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula7.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula8.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula9.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula10.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula11.getContents());
			 System.out.println("ConteÃºdo da cÃ©lula 3: " + celula12.getContents());
			 System.out.println("==================== Fim da Linha=======");
			 }
		}
				
	}

}
