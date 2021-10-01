package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Main {
	private static Scanner sc=new Scanner(System.in);
	private static int[] priceBook;
	public static void main (String[] args) throws IOException {

		int numBooks=0;
		int peterMoney=0;
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
		priceBook= new int[numBooks];
		enterPrices(numBooks);
		sortPrices();
		printArray();
		System.out.println("How much money receives peter?");
		peterMoney=sc.nextInt();
		sc.nextLine();
		int nearIndex=binarySearch(peterMoney);
		System.out.println(exactSum(nearIndex,peterMoney));
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
		int i=0;
		int j;
		
		int exactSum;
		int subtraction;
		int minValue=9999;
		String message="";
		if(priceBook[minBook]>=totalMoney) {
			j=minBook-1;
		}
		else {
			j=minBook;
		}
		
		while((i<j)){
			exactSum=priceBook[i]+priceBook[j];
			subtraction=priceBook[j]-priceBook[i];
			if(exactSum==totalMoney) {
				if(subtraction<minValue) {
					minValue=subtraction;
					message="Peter should buy books whose prices are "+priceBook[i]+" and " +priceBook[j];
				}
			}
			j--;
			i++;
		}
		return message;
	}

	public static void printArray() {
		for(int i=0;i<priceBook.length;i++) {
			System.out.print(priceBook[i]+" ");
		}
		System.out.println("\n");
	}

	/*
	public static int selectOption(int books) {
		int option=0;
		boolean b=true;
		System.out.println("Please select an option:  ");
		System.out.println("1) Manual form");
		System.out.println("2) Random form");
		while(b) {
			option=sc.nextInt();
			if(option==1) {
				b=false;
			}
			else if(option==2){

			}
			else {
				System.out.println("That option is not available");
			}
		}
		sc.nextLine();
		return option;
	}*/
}
