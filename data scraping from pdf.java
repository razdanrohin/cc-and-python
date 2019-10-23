import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class punjab{
	 public static void main(String a[]){
		File file = new File(a[0]);
        File[] files = file.listFiles();
        for(File f: files){
		BufferedReader br = null;
			 
	try 
	{	String line,line1,sfilename,statecode,AC,Part,PC,pin=new String();
		int i=0;int count=0;PC="";
		String[] EPIC=new String[3000];
		String[] master=new String[500];
		statecode=a[2]+f.getName().replace(".txt","");
		sfilename = a[0]+f.getName();	


//=========================================================================================================================================
String[] array = new String[] { "1,1,143303","1,2,143202","1,3,143301","1,4,143008","1,5,143419","1,6,143412","1,7,143105","1,9,143407","1,10,143406","2,11,143102","2,12,143101","2,13,143601","2,15,143001","2,16,143001","2,17,143001","2,18,143001","2,19,143001","2,20,143108","3,14,143411","3,21,143401","3,22,143419","3,23,143416","3,24,143117","3,25,143201","3,27,143001","3,28,143001","3,75,143502","4,30,143112","4,31,143117","4,32,143416","4,33,143102","4,34,143114","4,35,143114","4,36,143114","4,37,.,143114","4,38,143502","5,8,143501","5,26,143102","5,29,143416","5,39,143501","5,40,143305","5,41,143112","5,42,143504","5,43,143107","5,44,143205","6,45,143414","6,46,143601","6,47,143001","6,48,143204","6,49,143001","6,50,143104","6,51,143103","6,52,143410","6,53,143414","7,60,143502","7,61,143502","7,62,143108","7,63,143502","7,64,143502","7,65,143502","7,66,143603","7,68,143102","7,70,143102","8,54,143105","8,55,143407","8,56,143419","8,57,143501","8,58,143415","8,59,143417","8,67,143416","8,69,143401","8,106,143111","9,71,143108","9,72,143407","9,73,143108","9,74,143108","9,84,143603","9,87,143115","9,88,143001","9,89,143419","9,90,143116","10,76,143102","10,77,143102","10,78,143006","10,79,143116","10,80,143102","10,81,143502","10,82,143601","10,85,143101","10,86,143501","11,83,143301","11,91,143111","11,92,143406","11,93,143406","11,94,143113","11,95,143504","11,96,143401","11,97,143110","11,98,143601","12,99,143504","12,100,143406","12,101,143201","12,102,143407","12,103,143411","12,104,143001","12,105,143103","12,107,143408","12,108,143003","13,109,143202","13,110,143301","13,111,143305","13,112,143406","13,113,143205","13,114,143003","13,115,143301","13,116,143415","13,117,143411" };
//=========================================================================================================================================	
		
		br = new BufferedReader( new FileReader(sfilename));
		FileOutputStream out = new FileOutputStream((a[1]+f.getName()).replaceAll(".txt","")+"_out.txt");//appends master to the file streamed
			
			AC=f.getName().substring(4,7).replaceFirst("^0+(?!$)", "");;
			Part=f.getName().substring(8,11).replaceFirst("^0+(?!$)", "");;
			
			//System.out.println(AC+"\t"+Part);
			
			for(int temp1=0;temp1<array.length;temp1++)
			{		String compAC=array[temp1].split(",")[1];
					Pattern pat9=Pattern.compile(AC);
					Matcher m9=pat9.matcher(compAC);
			
				if(m9.find()==true)
				{
					PC=array[temp1].split(",")[0];
					pin=array[temp1].split(",")[2];
				}
				
			}
			
			while((line=br.readLine())!=null)
			{	
				count++;
				String[] stringitems=line.split("\\s{1,}");
				for(int temp=0;temp<stringitems.length;temp++)
				{	Pattern pat=Pattern.compile("[0-9]|[A-Z]|\\(|\\)|\\,|\\:");
					Matcher m=pat.matcher(stringitems[temp]);		
					if(m.find()==true)
					{
							Pattern pat2=Pattern.compile("[0-9]{6}");//=============regex for Pincode
							Matcher m2=pat2.matcher(stringitems[temp]);
							
							Pattern pat3=Pattern.compile("[a-zA-Z]{2,4}[0-9]{5,8}");//======================regex for epic no
							Pattern pat4=Pattern.compile("[A-Z]{1,3}/[0-9]{1,2}/[0-9]{1,4}/[0-9]{4,9}");
							Matcher m3=pat3.matcher(stringitems[temp]);
							Matcher m4=pat4.matcher(stringitems[temp]);
//===================================================================================================================================
						if(m3.find()==true||m4.find()==true)//condition to select Serial number and EPIC
						{
							EPIC[i]=stringitems[temp].replaceAll("\\s{2,}","|");
							i++;
						}}
				}
			}
			out.write(("S_NO|"+"EPIC_ID|"+"statecode|"+"pincode|"+"AC_no|"+"Part_No"+"|"+"PC_no"+"\n").getBytes());
			i=0;
			while(EPIC[i]!=null)
			{
				out.write((i+"|"+EPIC[i]+"|"+statecode+"|"+pin+"|"+AC+"|"+Part+"|"+PC+"\n").getBytes());
				i++;
			}
	}	
	catch (FileNotFoundException e) {System.err.println("Unable to find the file: fileName");} 
	catch (IOException e) {System.err.println("Unable to read the file: fileName");}
}
}
}