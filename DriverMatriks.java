import java.util.*;

class DriverMatriks{
	public static void main(String[] args){
		Matriks M1 = new Matriks();
		Matriks M2 = new Matriks();

		M1.InputDataMat();
		// M2.InputDataMat();

		M1.InverseAdjoin().OutputDataMat();
		System.out.println(M1.DeterminanOBE());
		System.out.println(M1.DeterminanKofaktor());
		M1.InverseOBE();
		M1.OutputDataMat();

		// MainMenu();
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

	public static void MainMenu(){
		int choice;
		boolean loop = true;

		Scanner in = new Scanner(System.in);

		while(loop){
			PrintMenu();
			System.out.print(">> ");

			choice = in.nextInt();
			switch(choice){
				case 1:{
					// CEK DULU SOLSNYA APAKAB
					SPLMenu();
				    break;
				}
				case 2:{
					// CEK KOTAK ATO ENGGA!
					DETMenu();
				    break;
				}
				case 3:{
					// CEK INVERTIBLE
					INVMenu();
				    break;
				}
				case 4:{
					// DONE
					System.out.println("CFC");
				    break;
				}
				case 5:{
					// DONE
					System.out.println("ADJ");
				    break;
				}
				case 6:{
					// !
					System.out.println("INPOL");
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

	public static void SPLMenu(){
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
		switch(choice){
			case 1:{
				// Kelar + tinggal detect kalo many sols / no sols
				System.out.println("Gauss");
			    break;
			}
			case 2:{
				// tinggal
				System.out.println("Gauss-Jordan");
			    break;
			}
			case 3:{
				// bikin fungsi perkalian matrix
				System.out.println("Inverse");
			    break;
			}
			case 4:{
				// bikin fungsi replace col
				System.out.println("Cramer");
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void DETMenu(){
		int choice;
		boolean loop = true;

		Scanner in = new Scanner(System.in);
		System.out.println("\nDeterminan");
		System.out.println("1. Metode Segitiga Atas");
		System.out.println("2. Metode Ekspansi Kofaktor");
		System.out.print(">> ");

		choice = in.nextInt();
		switch(choice){
			case 1:{
				// buat fungsi pengalinya
				System.out.println("Segitiga Atas");
			    break;
			}
			case 2:{
				// belom
				System.out.println("Cofactor");
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}

	public static void INVMenu(){
		int choice;
		boolean loop = true;

		Scanner in = new Scanner(System.in);
		System.out.println("\nMatriks Balikan");
		System.out.println("1. Metode eliminasi Gauss-Jordan");
		System.out.println("2. Metode Matriks Adjoin");
		System.out.print(">> ");

		choice = in.nextInt();
		switch(choice){
			case 1:{
				// udah
				System.out.println("Gauss");
			    break;
			}
			case 2:{
				// belom
				System.out.println("Adjoin");
			    break;
			}
			default:{
				System.out.println("N/A");
			}
		}
	}
}