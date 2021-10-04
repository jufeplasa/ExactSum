package ui;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
	private static final String IMPORT_DATA="data/bookPrices.txt";
	private static Scanner sc=new Scanner(System.in);
	private static Integer[] priceBook;
	public static void main (String[] args) throws IOException {

		int numBooks=0;
		Long start, time;
		int peterMoney=0;

		int choose=0;
		System.out.println("		Select an option\n");
		System.out.println("1: Enter manual data");
		System.out.println("2: Import Cases");
		choose=sc.nextInt();
		sc.nextLine();
		if(choose==1) {
			boolean b=true;
			while(b) {
				System.out.println("How many available books are?");
				numBooks=sc.nextInt();
				sc.nextLine();
				if(numBooks>=2 && numBooks<=10000) {
					b=false;
				}
				else {
					System.out.println("Please enter a number between 2 and 10000");
				}
			}
			priceBook= new Integer[numBooks];
			int option =selectOption();
			if(option==1) {
				enterPrices(numBooks);
			}
			else if(option==2){
				enterRandomPrices();
			}
			printArray();
			Integer[] copyPriceBook=Arrays.copyOf(priceBook, priceBook.length);
			
			System.out.println("How much money receives peter?");
			peterMoney=sc.nextInt();
			sc.nextLine();
			exportData(numBooks,peterMoney);
			start=System.currentTimeMillis();
			System.out.println("Start Binary search: ");
			sortPrices();

			int nearIndex=binarySearch(peterMoney);
			System.out.println(exactSum(nearIndex,peterMoney));
			time=(System.currentTimeMillis()-start);
			System.out.println("Finish Binary search: "+time);

			priceBook=copyPriceBook;
			start=System.currentTimeMillis();
			System.out.println("Start lineal search: ");
			System.out.println(linealSearch(peterMoney));
			time=(System.currentTimeMillis()-start);
			System.out.println("Finish lineal search: "+time);
		}
		else {
			ImportPrices();
		}



		
	}

	public static void enterPrices(int size) throws IOException {
		String[] pricesbyBook;
		String prices="";
		boolean b=true;
		BufferedReader br= new BufferedReader( new InputStreamReader(System.in));
		System.out.println("Enter a price of "+size+" books");
		prices=br.readLine();
		pricesbyBook=prices.split("\\ ");

		for(int i=0;i<size;i++) {
			b=true;
			priceBook[i]=Integer.parseInt(pricesbyBook[i]);
			while(b) {
				if(priceBook[i]>1000001) {
					System.out.println("Enter a price less than 1000001 ");
					priceBook[i]=br.read();
				}
				else {
					b=false;
				}
			}
		}
	}

	public static void enterRandomPrices() {
		for(int i=0; i<priceBook.length;i++) {
			priceBook[i]=(int) Math.floor(Math.random()*200+1);
		}
	}
	public static void ImportPrices() throws IOException {
		BufferedReader br= new BufferedReader(new FileReader(IMPORT_DATA));
		String line = br.readLine();
		int numBooks;
		int peterMoney;
		Long start, time;
		int a=1;
		while(line!=null) {
			String [] part = line.split(";");
			numBooks=Integer.parseInt(part[0]);
			priceBook= new Integer[numBooks];
			
			for (int i=1; i<part.length-1;i++) {
				priceBook[i-1]=Integer.parseInt(part[i]);
			}
			peterMoney=Integer.parseInt(part[part.length-1]);
			printArray();
			Integer[] copyPriceBook=Arrays.copyOf(priceBook, priceBook.length);
			start=System.currentTimeMillis();
			System.out.println("Start Binary search "+a+" : ");
			sortPrices();
			int nearIndex=binarySearch(peterMoney);
			System.out.println(exactSum(nearIndex,peterMoney));
			time=(System.currentTimeMillis()-start);
			System.out.println("Finish Binary search: "+time+" miliseconds \n");
			
			priceBook=copyPriceBook;
			start=System.currentTimeMillis();
			System.out.println("Start lineal search to case "+a+": ");
			System.out.println(linealSearch(peterMoney));
			time=(System.currentTimeMillis()-start);
			System.out.println("Finish lineal search: "+time+" miliseconds \n");
			a++;
			line = br.readLine();
		}
		br.close();
	}


	public static void sortPrices() {
		int aux, j;
		for(int i=1;i<priceBook.length;i++) {
			aux=priceBook[i];
			j = i-1;
			while(j>=0 && aux<priceBook[j]) {
				priceBook[j+1]=priceBook[j];
				j--;
			}

			priceBook[j+1]=aux;
		}
	}

	public static void printArray() { 
		for(int i=0;i<priceBook.length;i++) { 
			System.out.print(priceBook[i]+" "); 
		} 
		System.out.println("\n"); 
	} 

	public static int binarySearch(int element) {
		int i, j;
		int m = 0;
		boolean find=false;
		i=0;
		j=priceBook.length-1;

		while((i<=j) && !find){
			m = (int)(i+j)/2;
			if(element == priceBook[m]) {
				find = true;
			}
			else if(element < priceBook[m]) {
				j = m-1;
			}
			else {
				i = m+1;
			}
		}
		return m;
	}

	public static String exactSum(int minBook,int  totalMoney) {

		int exactSum;
		int subtraction;
		int minValue=9999;
		String message="";
		if(priceBook[minBook]>=totalMoney) {
			minBook-=1;
		}
		for(int i=0;i<=minBook-1;i++) {
			for(int j=i+1;j<=minBook;j++) {
				exactSum=priceBook[i]+priceBook[j];
				subtraction=priceBook[j]-priceBook[i];
				if(exactSum==totalMoney) {
					if(subtraction<minValue) {
						minValue=subtraction;
						message="Peter should buy books whose prices are "+priceBook[i]+" and " +priceBook[j];
					}
				}
			}
		}
		return message;
	}

	public static String linealSearch(int  totalMoney) {
		int exactSum, subtraction;
		int minValue=999999;
		String message="";
		for(int i=0;i<=priceBook.length-1;i++) {
			for(int j=i+1;j<priceBook.length;j++) {
				exactSum=priceBook[i]+priceBook[j];
				subtraction=priceBook[j]-priceBook[i];
				if(exactSum==totalMoney) {
					if(subtraction<minValue) {
						minValue=subtraction;
						message="Peter should buy books whose prices are "+priceBook[i]+" and " +priceBook[j];
					}
				}
			}
		}
		return message;
	}


	public static int selectOption() {
		int option=0;
		boolean b=true;
		System.out.println("Please select an option:  ");
		System.out.println("1) Manual form");
		System.out.println("2) Random form");
		while(b) {
			option=sc.nextInt();
			if(option==1) {
				return option;
			}
			else if(option==2){
				return option;
			}
			else {
				System.out.println("That option is not available");
			}
		}
		sc.nextLine();
		return option;
	}
	
	public static void exportData(int numBooks, int totalMoney) throws IOException {
		FileWriter fw = new FileWriter(IMPORT_DATA, true);
		String message="";
		message=numBooks+";";
		for(int i=0; i<priceBook.length;i++) {
			message+=priceBook[i]+";";
		}
		message+=totalMoney+"\n";
		fw.write(message);
		fw.close();
	}
}
