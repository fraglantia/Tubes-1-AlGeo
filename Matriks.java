import java.util.*;
import java.lang.Math; 


class Matriks{
	// Atribut
	int NeffKol, NeffBar;
	float[][] angka = new float[100][100];
	// IdxMin = 1
	// BERBENTUK AUGMENTED MATRIKS

	// Metode
	Matriks(){

	}

	void InputDataMat(){
		Scanner in = new Scanner(System.in);
		System.out.println("INPUT MATRIKS:");
		System.out.print("JUMLAH BARIS: ");
		this.NeffBar = in.nextInt();
		System.out.print("JUMLAH KOLOM: ");
		this.NeffKol = in.nextInt();
		System.out.println("ANGKA: ");
		in.nextLine();

		String[] baris;

		for(int i=1; i<=this.NeffBar; i++){

			baris = in.nextLine().split(" ");

			while(baris.length != this.NeffKol){
				System.out.println("Banyaknya elemen pada baris ini harus " + this.NeffKol + "!");
				baris = in.nextLine().split(" ");
			}

			for(int j=1; j<=this.NeffKol; j++){
				this.angka[i][j] =  Float.parseFloat(baris[j-1]);
			}

		}
	}

	void OutputDataMat(){
		System.out.println("OUTPUT MATRIKS:");
		for(int i=1; i<=this.NeffBar; i++){
			for(int j=1; j<=this.NeffKol; j++){
				System.out.printf("%.2f\t", this.angka[i][j]);
			}
			System.out.println();
		}
	}

	void OBESwap(int a, int b){
		// swap baris ke a dan b
		float temp;

		for(int i=1; i<=this.NeffKol; i++){
			temp = this.angka[a][i];
			this.angka[a][i] = this.angka[b][i];
			this.angka[b][i] = temp;
		}
		System.out.println("R" + a + " <-> R" + b);
	}

	void OBEScale(int a, float k){
		// pekalian baris dengan k != 0

		for(int i=1; i<=this.NeffKol; i++){
			this.angka[a][i] *= k;
		}
		System.out.println("R" + a + " <- R" + a + "*" + k);
	}

	void OBEReplace(int a, int b, float k){
		// elemen baris ke-a ditambah k * elemen baris ke-b

		for(int i=1; i<=this.NeffKol; i++){
			this.angka[a][i] += k*this.angka[b][i];
		}

		System.out.println("R" + a + " <- R" + a + " + R" + b + "*" + k);
	}
}


// 5 -3 1 14
// 3 2 -4 3
// 2 3 3 15