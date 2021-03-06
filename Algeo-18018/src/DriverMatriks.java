import java.util.*;
import java.lang.Math; 
import java.io.*;
import java.math.*;

class DriverMatriks{
	public static void main(String[] args){
		Matriks M1 = new Matriks();
		MainMenu(M1);
	}

	public static void PrintMenu(){
		System.out.println("\nMENU");
		System.out.println("1. Sistem Persamaaan Linier");
		System.out.println("2. Determinan");
		System.out.println("3. Matriks balikan");
		System.out.println("4. Matriks kofaktor");
		System.out.println("5. Adjoin");
		System.out.println("6. Interpolasi Polinom");
		System.out.println("7. Keluar");
	}

	public static void MainMenu(Matriks M){
		int choice;
		boolean loop = true;

		Scanner in = new Scanner(System.in);

		while(loop){
			PrintMenu();
			System.out.print(">> ");
			M.ClearMatriks();

			choice = in.nextInt();
			switch(choice){
				case 1:{
					SPLMenu(M);
				    break;
				}
				case 2:{
					DETMenu(M);
				    break;
				}
				case 3:{
					INVMenu(M);
				    break;
				}
				case 4:{
					MintaInput(M);
					if(M.NeffKol != M.NeffBar){
						System.out.println("Matriks harus Square!");
					}
					else{
						MintaOutput(M.Kofaktor().OutputDataMat());
					}
				    break;
				}
				case 5:{
					MintaInput(M);
					if(M.NeffKol != M.NeffBar){
						System.out.println("Matriks harus Square!");
					}
					else{
						MintaOutput(M.Adjoin().OutputDataMat());
					}
				    break;
				}
				case 6:{
					Interpolasi();
				    break;
				}
				case 7:{
					System.out.println("EXIT");
					loop = false;
				    break;
				}
				default:{
					System.out.println("N/A");
				}
			}
		}
		
	}

	public static void SPLMenu(Matriks M){
		int choice;
		boolean loop = true;

		Scanner in = new Scanner(System.in);
		System.out.println("\nSistem Persamaaan Linier");
		System.out.println("1. Metode eliminasi Gauss");
		System.out.println("2. Metode eliminasi Gauss-Jordan");
		System.out.println("3. Metode matriks balikan");
		System.out.println("4. Kaidah Cramer");
		System.out.print(">> ");

		choice = in.nextInt();
		MintaInput(M);
		System.out.println();
		switch(choice){
			case 1:{
				// GAUSS
				M.toReducedEchelon();
				if(M.isNoSol()){
					System.out.println("SPL tidak memiliki solusi!");
				} 
				else{
					String hasil = "";
					for(int i=1; i<M.NeffKol; i++){
						hasil += ("x" + i + " = " + M.SolveSPLgauss(i) + "\n");
					}
					MintaOutput(hasil);
				}

				
			    break;
			}
			case 2:{
				// GAUSS-JORDAN
				String hasil = "";
				M.toReducedEchelon();
				if(M.isNoSol()){
					System.out.println("SPL tidak memiliki solusi!");
				}
				else{
					for(int i=1; i<M.NeffKol; i++){
						hasil += ("x" + i + " = " + M.SolveSPLgJordan(i) + "\n");
					}
					MintaOutput(hasil);
				}
			    break;
			}
			case 3:{
				// BALIKAN
				if(M.NeffKol != M.NeffBar+1){
					System.out.println("Matriks harus Augmented dan Square!");			
				}
				else if(M.isNoSol2()){
					System.out.println("SPL tidak memiliki solusi/banyak solusi!");
				} else {
					String hasil = "";
					for(int i=1; i<=M.NeffBar; i++){
						hasil += ("x" + i + " = " + M.SolveSPLinverse(i).setScale(3, RoundingMode.HALF_EVEN) + "\n");
					}
					MintaOutput(hasil);
				}		
			    break;
			}
			case 4:{
				// CRAMER
				String hasil = "";
				if(M.NeffKol != M.NeffBar+1){
					System.out.println("Matriks harus Augmented dan Square!");			
				}
				else if(M.isNoSol2()){
					System.out.println("SPL tidak memiliki/banyak solusi!");
				} 
				else{
					for(int i=1; i<=M.NeffBar; i++){
						hasil += ("x" + i + " = " + M.SolveSPLKramer(i).setScale(3, RoundingMode.HALF_EVEN) + "\n");
					}
					MintaOutput(hasil);
				}
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void DETMenu(Matriks M){
		int choice;
		boolean loop = true;

		Scanner in = new Scanner(System.in);
		System.out.println("\nDeterminan");
		System.out.println("1. Metode Segitiga Atas");
		System.out.println("2. Metode Ekspansi Kofaktor");
		System.out.print(">> ");

		choice = in.nextInt();
		MintaInput(M);
		System.out.println();
		switch(choice){
			case 1:{
				// Segitiga Atas
				if(M.NeffKol != M.NeffBar){
					System.out.println("Matriks harus Square!");			
				}
				else{
					System.out.println("Determinannya: " + M.DeterminanOBE().setScale(3, RoundingMode.HALF_EVEN));
				}
			    break;
			}
			case 2:{
				// Cofactor
				if(M.NeffKol != M.NeffBar){
					System.out.println("Matriks harus Square!");			
				}
				else{
					System.out.println("Determinannya: " + M.DeterminanKofaktor().setScale(3, RoundingMode.HALF_EVEN));
				}
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void INVMenu(Matriks M){
		int choice;

		Scanner in = new Scanner(System.in);
		System.out.println("\nMatriks Balikan");
		System.out.println("1. Metode eliminasi Gauss-Jordan");
		System.out.println("2. Metode Matriks Adjoin");
		System.out.print(">> ");

		choice = in.nextInt();
		MintaInput(M);
		System.out.println();
		switch(choice){
			case 1:{
				// GaussJordan
				if(M.NeffKol == M.NeffBar){
					if(M.IsInvertible()){
						M.InverseOBE();
						MintaOutput(M.OutputDataMat());
					}
					else{
						System.out.println("Matriks tidak bisa di Inverse!");
					}
				}
				else{
					System.out.println("Matriks harus Square!");
				}
			    break;
			}
			case 2:{
				// Adjoin
				if(M.NeffKol == M.NeffBar){
					if(M.IsInvertible()){
						System.out.println("Inversenya:");
						MintaOutput(M.InverseAdjoin().OutputDataMat());
					}
					else{
						System.out.println("Matriks tidak bisa di Inverse!");
					}
				}
				else{
					System.out.println("Matriks harus Square!");
				}
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void MintaInput(Matriks M){
		int choice;

		Scanner in = new Scanner(System.in);
		String fileName;
		System.out.println("\nPilih metode input:");
		System.out.println("1. Input dari Keyboard");
		System.out.println("2. Input dari File");
		System.out.print(">> ");

		choice = in.nextInt();
		switch(choice){
			case 1:{
				M.InputDataMat();
			    break;
			}
			case 2:{
				boolean valid = false;
				in.nextLine();

				while(!valid){
					do {
						System.out.print("Nama File: ");
						fileName = in.nextLine();
						if(!FileCheck(fileName)){
							System.out.println("File tidak ada.");
						}
					} while(!FileCheck(fileName));

					if(M.InputDataMatFile(fileName)==0){
						 valid = true;
					}
					else{
						System.out.println("Ukuran matriks tidak seragam");
					}
				}
				

			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void MintaOutput(String out){
		int choice;

		Scanner in = new Scanner(System.in);
		String fileName;
		System.out.println("Pilih metode output:");
		System.out.println("1. Output ke Layar");
		System.out.println("2. Output ke File");
		System.out.print(">> ");

		choice = in.nextInt();
		switch(choice){
			case 1:{
				System.out.println(out);
			    break;
			}
			case 2:{
				// belom
				in.nextLine();
				System.out.print("Nama File: ");
				fileName = in.nextLine();
				try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
		            bufferedWriter.write(out);
		        } catch (IOException e) {
		            System.out.println("error");
		        }
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void InputInterpolasi(Matriks X, Matriks Y){
		int choice;
		int n;

		Scanner in = new Scanner(System.in);
		String fileName;
		System.out.println("\nPilih metode input:");
		System.out.println("1. Input dari Keyboard");
		System.out.println("2. Input dari File");
		System.out.print(">> ");

		choice = in.nextInt();
		switch(choice){
			case 1:{
				System.out.println("Jumlah titik:");
				n = in.nextInt();
				String[] baris;
				boolean valid;
				boolean found;

				X.NeffBar = n;
				X.NeffKol = 1;
				Y.NeffBar = n;
				Y.NeffKol = 1;

				in.nextLine();

				for(int i=1; i<=n; i++){
					baris = in.nextLine().split(" ");
					valid = false;

					while(!valid){
						if(baris.length != 2){
							System.out.println("Mohon masukkan sebagai pasangan x-y!");
							baris = in.nextLine().split(" ");
							continue;
						}

						found = false;
						for(int j=1; j<i; j++){
							if(X.angka[j][1].compareTo(new BigDecimal(baris[0])) == 0 && Y.angka[j][1].compareTo(new BigDecimal(baris[1])) != 0){
								found = true;
								break;
							}
						}
						if(found){
							System.out.println("Titik tidak bisa menjadi suatu fungsi!");
							baris = in.nextLine().split(" ");
							continue;
						}
						valid = true;
					}

					X.angka[i][1] =  new BigDecimal(baris[0]);
					Y.angka[i][1] =  new BigDecimal(baris[1]);
				}
			    break;
			}
			case 2:{
				in.nextLine();
				boolean valid = false;
				boolean found;

				while(!valid){
					do {
						System.out.print("Nama File: ");
						fileName = in.nextLine();
						if(!FileCheck(fileName)){
							System.out.println("File tidak ada.");
						}
					} while(!FileCheck(fileName));
					
					X.InputDataMatFile(fileName);
					if(X.NeffKol != 2){
						System.out.println("Mohon masukkan sebagai pasangan x-y!");
						continue;
					}
					X.unAugmented(Y, 1);
					found = false;
					for(int i=1; i<=X.NeffBar; i++){
						for(int j=1; j<i; j++){
							if(X.angka[i][1].compareTo(X.angka[j][1]) == 0 && Y.angka[i][1].compareTo(Y.angka[j][1]) != 0){
								found = true;
								break;
							}
						}
					}
					if(found){
						System.out.println("Titik tidak bisa menjadi suatu fungsi!");
						continue;
					}
					valid = true;
				}
				
				
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void Interpolasi(){
		// CEK DI https://www.wolframalpha.com/input/?i=polynomial+interpolation&assumption=%7B%22F%22%2C+%22InterpolatingPolynomialCalculator%22%2C+%22data%22%7D+-%3E%22%7B1%2C+4%2C+9%2C+16%7D%22&assumption=%7B%22FVarOpt%22%7D+-%3E+%7B%7B%22InterpolatingPolynomialCalculator%22%2C+%22data2%22%7D%7D
		Matriks X = new Matriks();
		Matriks Y = new Matriks();
		Matriks M = new Matriks();
		// M|Y
		InputInterpolasi(X, Y);
		M.NeffBar = X.NeffBar;
		M.NeffKol = X.NeffBar;
		for(int i=1; i<=M.NeffBar; i++){
			for(int j=1; j<=M.NeffKol; j++){
				M.angka[i][j] = X.angka[i][1].pow(j-1);
			}
		}
		M.Augmented(Y);

		M.toReducedEchelon();


		M.unAugmented(Y, 1);

		// PRINT FUNGSI
		String hasil = "";
		boolean firstPrinted = false;

		for(int i=Y.NeffBar; i>=1; i--){

			if(Y.angka[i][1].setScale(20, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0){
				if(firstPrinted && Y.angka[i][1].setScale(20, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO)!=0){
					if(Y.angka[i][1].setScale(20, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) >= 0){
						hasil += (" + ");
					}
					else{
						hasil += (" - ");
					}
				}

				if(!firstPrinted){
					hasil += Y.angka[i][1].setScale(3, RoundingMode.HALF_EVEN);
					firstPrinted = true;
				}
				else{
					hasil +=  Y.angka[i][1].abs().setScale(3, RoundingMode.HALF_EVEN);
				}

				if(i==2){
					hasil += "x";
				}
				else if(i>2){
					hasil += "x^" + (i-1);
				}
			}
			
		}

		MintaOutput(hasil);

		// taksir nilai x
		Scanner in = new Scanner(System.in);
		int n;
		BigDecimal taksirX = BigDecimal.ZERO;
		BigDecimal taksirY = BigDecimal.ZERO;
		System.out.println("Jumlah nilai X yang mau ditaksir:");
		n = in.nextInt();

		for(int j=1; j<=n; j++){
			System.out.print("Masukkan nilai X yang akan ditaksir: ");
			taksirX = new BigDecimal(Double.toString(in.nextDouble()));
			taksirY = BigDecimal.ZERO;
			for(int i=1; i<=M.NeffBar; i++){
				taksirY = taksirY.add(Y.angka[i][1].multiply(taksirX.pow(i-1)));
			}
			System.out.println("Taksiran nilai Y: " +  taksirY.setScale(3, RoundingMode.HALF_EVEN));
		}
	}

	public static boolean FileCheck(String fileName){
		File tmpDir = new File(fileName);
		return tmpDir.exists() && tmpDir.isFile();
	}

}