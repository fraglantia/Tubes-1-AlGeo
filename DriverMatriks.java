import java.util.*;
import java.lang.Math; 

class DriverMatriks{
	public static void main(String[] args){
		Matriks M1 = new Matriks();
		// System.out.println("HALO");
		// Matriks M2 = new Matriks();

		// M1.InputDataMatFile("matrixout.txt");
		// M1.InputDataMat();
		// System.out.println);
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
					// belum implementasi gauss-gJordan
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
					// DONE
					MintaInput(M);
					if(M.NeffKol != M.NeffBar){
						System.out.println("Matriks harus Square!");
					}
					else{
						System.out.println("Kofaktornya");
						M.Kofaktor().OutputDataMat();
					}
				    break;
				}
				case 5:{
					// DONE
					MintaInput(M);
					if(M.NeffKol != M.NeffBar){
						System.out.println("Matriks harus Square!");
					}
					else{
						System.out.println("Adjoinnya");
						M.Adjoin().OutputDataMat();
					}
				    break;
				}
				case 6:{
					// !
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
				// Kelar + tinggal detect kalo many sols / no sols
				System.out.println("Gauss");
				M.toReducedEchelon();
				if(M.isNoSol()){
					System.out.println("SPL tidak memiliki solusi!");
					break;
				}

				for(int i=1; i<M.NeffKol; i++){
					System.out.println("x" + i + " = " + M.SolveSPLgauss(i));
				}
			    break;
			}
			case 2:{
				// tinggal
				M.toReducedEchelon();
				if(M.isNoSol()){
					System.out.println("SPL tidak memiliki solusi!");
					break;
				}
				System.out.println("Gauss-Jordan");
			    break;
			}
			case 3:{
				// BALIKAN
				if(M.NeffKol != M.NeffBar+1){
					System.out.println("Matriks harus Augmented dan Square!");			
				}
				else{
					Matriks hasil = new Matriks();
					M.unAugmented(hasil, 1);
					hasil = hasil.KaliMat(M.InverseAdjoin());
					for(int i=1; i<=hasil.NeffBar; i++){
						System.out.println("x" + i + " = " + hasil.angka[i][1]);
					}
				}		
			    break;
			}
			case 4:{
				// CRAMER
				if(M.NeffKol != M.NeffBar+1){
					System.out.println("Matriks harus Augmented dan Square!");			
				}
				else{
					for(int i=1; i<=M.NeffBar; i++){
						System.out.println("x" + i + " = " + M.SolveSPLKramer(i));
					}
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
					System.out.println("Determinannya: " + M.DeterminanOBE());
				}
			    break;
			}
			case 2:{
				// Cofactor
				if(M.NeffKol != M.NeffBar){
					System.out.println("Matriks harus Square!");			
				}
				else{
					System.out.println("Determinannya: " + M.DeterminanKofaktor());
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
						System.out.println("Inversenya:");
						M.OutputDataMat();
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
						M.InverseAdjoin().OutputDataMat();
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

		choice = in.nextInt();
		switch(choice){
			case 1:{
				M.InputDataMat();
			    break;
			}
			case 2:{
				// belom
				in.nextLine();
				fileName = in.nextLine();
				System.out.println("Nama File:");
				M.InputDataMatFile(fileName);
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

		choice = in.nextInt();
		switch(choice){
			case 1:{
				System.out.println("Jumlah titik:");
				n = in.nextInt();
				String[] baris;

				X.NeffBar = n;
				X.NeffKol = 1;
				Y.NeffBar = n;
				Y.NeffKol = 1;

				in.nextLine();

				for(int i=1; i<=n; i++){
					baris = in.nextLine().split(" ");

					while(baris.length != 2){
						System.out.println("Mohon masukkan sebagai pasangan x-y!");
						baris = in.nextLine().split(" ");
					}
					X.angka[i][1] =  Float.parseFloat(baris[0]);
					Y.angka[i][1] =  Float.parseFloat(baris[1]);
				}
			    break;
			}
			case 2:{
				in.nextLine();
				System.out.println("Nama File:");
				fileName = in.nextLine();
				X.InputDataMatFile(fileName);
				if(X.NeffKol != 2){
					System.out.println("Mohon masukkan sebagai pasangan x-y!");
				}
				else{
					X.unAugmented(Y, 1);
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
				M.angka[i][j] = (float)Math.pow(X.angka[i][1], j-1);
			}
		}
		M.Augmented(Y);
		M.toReducedEchelon();
		M.unAugmented(Y, 1);

		// PRINT FUNGSI
		for(int i=Y.NeffBar; i>=1; i--){

			if(i==1){
				System.out.print(Math.abs(Y.angka[i][1]));
			}
			else if(i==2){
				System.out.print(Math.abs(Y.angka[i][1]) + "x");
			}
			else {
				System.out.print(Math.abs(Y.angka[i][1]) + "x^" + (i-1));
			}

			if(i!=1){
				if(Y.angka[i+1][1]>=0){
					System.out.print(" + ");
				}
				else{
					System.out.print(" - ");
				}
				
			}
			else{
				System.out.print("\n");
			}
		}

		// taksir nilai x
		Scanner in = new Scanner(System.in);
		float taksirX;
		float taksirY=0;
		System.out.print("Masukkan nilai X yang akan ditaksir: ");
		taksirX = in.nextFloat();
		for(int i=1; i<=M.NeffBar; i++){
			taksirY += Y.angka[i][1] * (float)Math.pow(taksirX, i-1);
		}
		System.out.println("Taksiran nilai Y: " +  taksirY);
	}


}