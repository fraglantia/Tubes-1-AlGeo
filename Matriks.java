import java.util.*;
import java.lang.Math; 


class Matriks{
	// Atribut
	int NeffKol, NeffBar;
	float[][] angka = new float[100][100];
	// IdxMin = 1
	// BERBENTUK AUGMENTED MATRIKS

	// Metode
	Matriks(int rowEff, int colEff){
		this.NeffBar = rowEff;
        this.NeffKol = colEff;
        angka = new float[rowEff+1][colEff+1];
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
		// DEBUG: NANTI DIBUANG AJA
		System.out.println("R" + a + " <-> R" + b);
	}

	void OBEScale(int a, float k){
		// pekalian baris dengan k != 0

		for(int i=1; i<=this.NeffKol; i++){
			this.angka[a][i] *= k;
		}
		// DEBUG: NANTI DIBUANG AJA
		System.out.println("R" + a + " <- R" + a + "*" + k);
	}

	void OBEReplace(int a, int b, float k){
		// elemen baris ke-a ditambah k * elemen baris ke-b

		for(int i=1; i<=this.NeffKol; i++){
			this.angka[a][i] += k*this.angka[b][i];
		}
		// DEBUG: NANTI DIBUANG AJA
		System.out.println("R" + a + " <- R" + a + " + R" + b + "*" + k);
	}

	public Matriks minorMatriks(int idxRow, int idxCol){
        Matriks holder = new Matriks(this.NeffBar-1, this.NeffKol-1);
        for (int i = 1; i <= holder.NeffBar; i++){
            for (int j = 1; j <= holder.NeffKol; j++){
                if(i >= idxRow){
                    if(j >= idxCol){
                        holder.angka[i][j] = this.angka[i+1][j+1];
                    } else {
                        holder.angka[i][j] = this.angka[i+1][j];
                    }
                } else {
                    if(j >= idxCol){
                        holder.angka[i][j] = this.angka[i][j+1];
                    } else {
                        holder.angka[i][j] = this.angka[i][j];
                    }
                }
            }
        }
        return holder;
    }

	public float determinan(){
        float sum = 0f;
        int multiplier;
        Matriks holder = new Matriks(this.NeffBar-1, this.NeffKol-1);
        if(this.NeffBar == 2){ // BASIS saat 2x2
            return this.angka[1][1] * this.angka[2][2] - this.angka[1][2] * this.angka[2][1];
        } else {
            for (int i = 1; i <= this.NeffBar; i++){
                holder = this.minorMatriks(i, 1);
                if ((i + 1) % 2 == 0){ 
                    multiplier = 1;
                } else {
                    multiplier = -1;
                }
                sum += this.angka[i][1] * multiplier * holder.determinan(); //RECC
            }
        }
        return sum;
    }

	public Matriks kofaktor(){
        int multiplier;
        Matriks MatriksKofaktor = new Matriks(this.NeffBar, this.NeffKol);
        for (int i = 1; i <= this.NeffBar; i++){
            for (int j = 1; j <= this.NeffKol; j++){    
                if((i + j) % 2 == 0){
                    multiplier = 1;
                } else {
                    multiplier = -1;
                }
                
                if(this.minorMatriks(i, j).determinan() != 0){
                     MatriksKofaktor.angka[i][j] = multiplier * this.minorMatriks(i, j).determinan();
                } else {
                    MatriksKofaktor.angka[i][j] = 0;
                }
                
            }
        }
        return MatriksKofaktor;
    }

    public Matriks transpose(){
        Matriks T = new Matriks(this.NeffBar, this.NeffKol);
        for (int i = 1; i <= this.NeffBar; i++){
            for (int j = 1; j <= this.NeffKol; j++){
                T.angka[j][i] = this.angka[i][j];       
            }
        }
        return T;
    }

    public Matriks adjoin(){
        return this.kofaktor().transpose();
    }
}