import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main
{
    public static String inputFilePath = "input";
    public static String outputFilePath = "output";
    public static File root = new File(inputFilePath);

    public static void main(String[] args) 
    {   
        try
        {
            //Setting the temporary array to store the files found
            File[] fileList = root.listFiles();
            final int rootSize = fileList.length;
            final byte nullByte = 00;

            //Setting up array lists for stored csv data
            ArrayList<TattleEntry> entries = new ArrayList<TattleEntry>();

            for(int i = 0; i < rootSize; i++)
            {
                if(fileList[i].getName().equals("Tattle Data.xlsx"))
                {
                    //Getting excel data
                    entries = readDataFromExcelFile(fileList[i]);
                    
                    //Removing top row
                    entries.remove(0);

                    for(int j = 0; j < rootSize; j++)
                    {
                        if(fileList[j].getName().equals("global.txt"))
                        {
                            //Reading global.txt
                            byte[] globalData = readData(fileList[j]);

                            //Converting byte data to String
                            String globalDataString = new String(globalData, StandardCharsets.UTF_8);

                            //Splitting the String before tattles
                            String[] splitGlobalDataString = globalDataString.split(entries.get(0).eventName, 2);

                            //Checking if file exists
                            String newFileName = "\\global.txt";
                            for(int k = 1; (new File(outputFilePath + newFileName).exists()); k++)
                            {
                                newFileName = "\\global[" + k + "].txt";
                            }

                            //Making new output file
                            File combinationOutput = new File (outputFilePath + newFileName);
                            RandomAccessFile newFile = new RandomAccessFile(combinationOutput, "rw");

                            //Writing pre-tattle data
                            newFile.writeBytes(splitGlobalDataString[0]);

                            //Writing tattles
                            for(int k = 0; k < entries.size(); k++)
                            {
                                //Writing Event Name
                                newFile.writeBytes(entries.get(k).eventName);
                                newFile.write(nullByte);
                                
                                //Checking to see which type of tattle entry is present
                                String[] splitEventName = entries.get(k).eventName.split("_", 2);

                                //Writing Tattle
                                if(splitEventName[0].equals("menu"))
                                {
                                    String retString = "Lv. " + entries.get(k).level + " Max HP: " + entries.get(k).hp + " Defense: " + entries.get(k).defense + '\n' 
                                        + "Attack: " + entries.get(k).attackPower + " Superguardable: " + entries.get(k).superguardable;

                                    newFile.writeBytes(retString);
                                    newFile.write(nullByte);
                                }
                                else if(splitEventName[0].equals("btl"))
                                {
                                    String retString = entries.get(k).intro + '\n' + "<speed 0>" + '\n' + "<pos -25 0 0>*" + '\n' + "<scale 0.85>" + '\n' + "<pos -1 0 0><col 202060ff>Lv</col>.<pos 28 0 0> "
                                        + entries.get(k).level + '\n' + "<pos 83 0 0><col 202060ff>Health Points</col>:<pos 238 0 0> " + entries.get(k).hp + '\n' + "<pos 305 0 0><col 202060ff>Defense</col>:<pos 400 0 0> "
                                        + entries.get(k).defense + '\n' + "</scale>" + '\n' + "<pos 450 0 0>*" + '\n' + "<pos 0 25 0><scale 0.85><col c00000ff>Attack Power</col>: " + entries.get(k).attackPower + '\n'
                                        + "<pos 0 50 0><scale 0.7><col 4d8fccff>Superguardable</col>: " + entries.get(k).superguardable + "  <pos 240 50 0><scale 0.7><col 4d8fccff>Status</col>: " + entries.get(k).status
                                        + '\n' + "<pos 0 70 0><scale 0.7><col 202060ff>Weak</col>: " + entries.get(k).weakness + "  <pos 240 70 0><col 202060ff>Immune</col>: " + entries.get(k).immunity + '\n'
                                        + "</speed></scale></pos><dkey><wait 500></dkey>" + '\n' + "<k>";

                                    //Checking for an outro
                                    if(!entries.get(k).outro.equals(""))
                                    {
                                        retString += '\n' + "<p>" + '\n' + entries.get(k).outro;
                                    }

                                    newFile.writeBytes(retString);
                                    newFile.write(nullByte);
                                }
                                else
                                {
                                    System.out.println("Issue determining tattle entry type");
                                }
                            }
                            
                            //Removing tattle data from the global string
                            globalDataString = splitGlobalDataString[1];
                            splitGlobalDataString = globalDataString.split("obj_hlp_block_y_0", 2);
                            splitGlobalDataString[1] = "obj_hlp_block_y_0" + splitGlobalDataString[1];

                            //Writing post-tattle data
                            newFile.writeBytes(splitGlobalDataString[1]);

                            //Closing newFile
                            newFile.close();
                        }
                    }
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("There was an Error Reading the Input File");
        }
     
        System.out.println("Done!");
    }

    /**
     * @Author geeksforgeeks (Modified by Jemaroo)
     * @Function Method to extract Excel file contents
     * @Source https://www.geeksforgeeks.org/java-program-to-extract-content-from-a-excel-sheet/# 
     */
    public static ArrayList<TattleEntry> readDataFromExcelFile(File excelFile) throws IOException
    {
        // Creating an List object of Employee type
        ArrayList<TattleEntry> listEntries = new ArrayList<TattleEntry>();
    
        FileInputStream inputStream = new FileInputStream(excelFile);
    
        // As used 'xlsx' file is used so XSSFWorkbook will be used
        Workbook workbook = new XSSFWorkbook(inputStream);
    
        // Read the first sheet and if the contents are in different sheets specifying the correct index
        Sheet firstSheet = workbook.getSheetAt(0);
    
        // Iterators to traverse over
        Iterator<Row> iterator = firstSheet.iterator();
    
        // Condition check using hasNext() method which holds true till there is single element remaining in List
        while (iterator.hasNext()) 
        {
            // Get a row in sheet
            Row nextRow = iterator.next();

            // This is for a Row's cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            //Creating temporary TattleEntry
            TattleEntry entry = new TattleEntry();

            // Iterate over the cells
            while (cellIterator.hasNext()) 
            {
                Cell nextCell = cellIterator.next();
    
                // Switch case variable to get the columnIndex
                int columnIndex = nextCell.getColumnIndex();
    
                // Switch-case
                switch (columnIndex) 
                {
                    //Column A
                    case 0: entry.eventName = nextCell.getStringCellValue(); break;
    
                    //Column B
                    case 1: entry.intro = nextCell.getStringCellValue(); break;

                    //Column C
                    case 2: entry.level = nextCell.getStringCellValue(); break;

                    //Column D
                    case 3: entry.hp = nextCell.getStringCellValue(); break;

                    //Column E
                    case 4: entry.defense = nextCell.getStringCellValue(); break;

                    //Column F
                    case 5: entry.attackPower = nextCell.getStringCellValue(); break;

                    //Column G
                    case 6: entry.superguardable = nextCell.getStringCellValue(); break;

                    //Column H
                    case 7: entry.status = nextCell.getStringCellValue(); break;

                    //Column I
                    case 8: entry.weakness = nextCell.getStringCellValue(); break;

                    //Column J
                    case 9: entry.immunity = nextCell.getStringCellValue(); break;
                    
                    //Column K
                    case 10: entry.outro = nextCell.getStringCellValue(); break;
                }
            }
            // Adding up to the list
            listEntries.add(entry);
        }
    
        // Closing the inputstream and workbook as it free up the space in memory
        workbook.close();
        inputStream.close();
    
        // Return all the employees present in List object of Employee type
        return listEntries;
    }

    /**
     * @Author Jemaroo
     * @Function Will attempt to read the given file and return the byte data
     */
    private static byte[] readData(File file)
    {
        try 
        {
            FileInputStream f1 = new FileInputStream(file);

            byte[] data = new byte[(int)file.length()];

            f1.read(data);

            f1.close();

            return data;
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("There was an Error Opening the Input File");
        }
        catch (IOException e)
        {
            System.out.println("There was an Error Reading the Input File");
        }
    
        return new byte[0];
    }
}