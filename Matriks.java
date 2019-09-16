import java.util.*;
import java.lang.Math; 


class Matriks{
	// Atribut
	int NeffKol, NeffBar;
	float[][] angka = new float[100][100];
	// IdxMin = 1
	// BERBENTUK AUGMENTED MATRIKS

	// Metode
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
            if (this.angka[a][i] != 0) {
        		this.angka[a][i] *= k;
            }
		}
		// DEBUG: NANTI DIBUANG AJA
		System.out.println("R" + a + " <- R" + a + "*" + k);
	}

	void OBEReplace(int a, int b, float k){
		// elemen baris ke-a ditambah k * elemen baris ke-b

		for(int i=1; i<=this.NeffKol; i++){
            if (this.angka[b][i] != 0) {
	    		this.angka[a][i] += k*this.angka[b][i];
            }
		}
		// DEBUG: NANTI DIBUANG AJA
		System.out.println("R" + a + " <- R" + a + " + R" + b + "*" + k);
	}

    void CopyMatriks(Matriks M) {
        // meng-copy matriks ke matriks M
        M.NeffBar = this.NeffBar;
        M.NeffKol = this.NeffKol;
        for (int i = 1; i <= this.NeffBar; i++) {
            for (int j = 1; j <= this.NeffKol; j++) {
                M.angka[i][j] = this.angka[i][j];
            }
        }
    }

    void Augmented(Matriks M) {
        // membentuk bentuk augmented matriks dengan matriks M
        // prekondisi: this.NeffKol + M.NeffKol <= 100
        for (int i = 1; i <= this.NeffBar; i++) {
            for (int j = this.NeffKol+1; j <= this.NeffKol+M.NeffKol; j++) {
                this.angka[i][j] = M.angka[i][j-this.NeffKol];
            }
        }
        this.NeffKol += M.NeffKol;
    }

    void unAugmented(Matriks M, int KSize) {
        // memecahkan matriks augmentasi ke bentuk awalnya masing-masing
        this.NeffKol -= KSize;
        M.NeffKol = KSize;
        for (int i = 1; i <= this.NeffBar; i++) {
            for (int j = this.NeffKol+1; j <= this.NeffKol+KSize; j++) {
                M.angka[i][j-this.NeffKol] = this.angka[i][j];
            }
        }
    }

    Matriks MatriksIdentitas() {
        // mengirimkan matriks identitas dari matriks
        // prekondisi: matriks square
        Matriks M = new Matriks();
        M.NeffKol = this.NeffKol;
        M.NeffBar = this.NeffBar;
        for (int i = 1; i <= this.NeffBar; i++) {
            M.angka[i][i] = 1;
        }
        return M;
    }

    void toSegitigaAtas() {
        // mengubah matriks menjadi bentuk matriks segitiga atas
        boolean found;
        int k = 1;
        for (int i = 1; i < this.NeffBar; i++) {
            found = false;
            int j = i;
            while (!found && k <= this.NeffKol) {
                found = (this.angka[j][k] != 0);
                j++;
                if (!found && j > this.NeffBar) { k++; j = i; }
            }
            if (found && k <= this.NeffKol) {
                j--;
                this.OBESwap(i,j);
                for (j = i+1; j <= this.NeffBar; j++) {
                    if (this.angka[j][k] != 0) {
                        OBEReplace(j,i,-this.angka[j][k]/this.angka[i][k]);
                    }
                }
                k++;
            }
        }
    }

    void toEchelon() {
        // mengubah matriks menjadi bentuk echelon
        boolean found;
        this.toSegitigaAtas();
        for (int i = 1; i <= this.NeffBar; i++) {
            found = false;
            int j = 1;
            while (!found && j <= this.NeffKol) {
                found = (this.angka[i][j] != 0);
                j++;
            }
            if (found && this.angka[i][j-1] != 1) {
                j--;
                OBEScale(i,1/this.angka[i][j]);
            }
        }
    }

    void toReducedEchelon() {
        // mengubah matriks menjadi bentuk reduced echelon
        boolean found;
        this.toEchelon();
        for (int i = this.NeffBar; i >= 1; i--) {
            found = false;
            int j = 1;
            while (!found && j <= this.NeffKol) {
                found = (this.angka[i][j] != 0);
                j++;
            }
            if (found) {
                j--;
                for (int k = i-1; k >= 1; k--) {
                    OBEReplace(k,i,-this.angka[k][j]/this.angka[i][j]);
                }
            }
        }
    }

    int CountPivot() {
        // menghitung jumlah pivot point
        // matriks sudah berbentuk echelon
        boolean found;
        int count = 0;
        for (int i = this.NeffBar; i >= 1; i--) {
            found = false;
            int j = 1;
            while (!found && j <= this.NeffKol) {
                found = (this.angka[i][j] != 0);
                j++;
            }
            if (found) {
                count++;
            }
        }
        return count;
    }

    float Determinan() {
        // menghitung determinan matriks
        // prekondisi: matriks square
        float temp_d = 1;
        Matriks temp = new Matriks();
        this.CopyMatriks(temp);
        temp.toSegitigaAtas();
        for (int i = 1; i <= this.NeffBar; i++) {
            temp_d *= temp.angka[i][i];
        }
        return temp_d;
    }

    void Inverse() {
        // menngubah matriks ke bentuk inversenya
        Matriks temp = new Matriks();
        this.CopyMatriks(temp);
        temp.Augmented(temp.MatriksIdentitas());
        temp.toReducedEchelon();
        if (temp.CountPivot() != temp.NeffBar) {
            System.out.println("Matriks tidak memiliki invers!");
        } else {
            temp.unAugmented(this,this.NeffKol);
        }
    }

/*
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
        Matriks T = new Matriks(this.NeffKol, this.NeffBar);
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
    */
}