import java.io.*;
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

    void InputDataMatFile(String fileName){
        BufferedReader reader;

        Matriks M = new Matriks();

        String baris;
        String[] barisParsed;
        int maxKol = -1;
        boolean sameLength = true;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            M.NeffBar = 0;
            baris = bufferedReader.readLine();
            if(baris != null){
                barisParsed = baris.split(" ");
                maxKol = barisParsed.length;
                M.NeffKol = maxKol;
            }

            while(baris != null && sameLength) {
                M.NeffBar += 1;
                barisParsed = baris.split(" ");

                sameLength = maxKol == barisParsed.length;

                if(sameLength){
                    for(int j=1; j<=maxKol; j++){
                        M.angka[M.NeffBar][j] = Float.parseFloat(barisParsed[j-1]);
                    }
                }
                
                baris = bufferedReader.readLine();
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
        catch (IOException e) {
            System.out.println("error");
        }

        if(sameLength){
            this.ReverseCopyMatriks(M);
        }
        else {
            System.out.println("Ukuran matriks tidak seragam");
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

    void OutputDataMatFile(String fileName){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for(int i=1; i<=this.NeffBar; i++){
                for(int j=1; j<=this.NeffKol; j++){
                    bufferedWriter.write(String.format("%.2f", this.angka[i][j]));
                    if(j!=this.NeffKol){
                        bufferedWriter.write(" ");
                    }
                }
                if(i!=this.NeffBar){
                    bufferedWriter.write("\n");
                }
            }
            
        } catch (IOException e) {
            System.out.println("error");
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
		//System.out.println("R" + a + " <-> R" + b);
	}

	void OBEScale(int a, float k){
		// pekalian baris dengan k != 0

        for(int i=1; i<=this.NeffKol; i++){
            if(this.angka[a][i] != 0) {
                this.angka[a][i] *= k;
            }
		}
		// DEBUG: NANTI DIBUANG AJA
		//System.out.println("R" + a + " <- R" + a + "*" + k);
	}

	void OBEReplace(int a, int b, float k){
		// elemen baris ke-a ditambah k * elemen baris ke-b

		for(int i=1; i<=this.NeffKol; i++){
            if (this.angka[b][i] != 0) {
	    		this.angka[a][i] += k*this.angka[b][i];
            }
		}
		// DEBUG: NANTI DIBUANG AJA
		//System.out.println("R" + a + " <- R" + a + " + R" + b + "*" + k);
	}

    void KaliKons(float k){
        for (int i = 1; i <= this.NeffBar; i++) {
            for (int j = 1; j <= this.NeffKol; j++) {
                this.angka[i][j] *= k;
            }
        }
    }

    public Matriks KaliMat(Matriks M1){
        // perkalian matriks dengan M1
        // PREKONDISI UKURAN this.NeffKol = M1.NeffBar
        int tmp;

        Matriks M = new Matriks();
        M.NeffBar = this.NeffBar;
        M.NeffKol = M1.NeffKol;

        for(int i=1; i<=M.NeffBar; i++){
            for(int j=1; j<=M.NeffKol; j++){
                // 1 1 = 
                tmp = 0;
                for(int k=1; k<=this.NeffKol; k++){
                    tmp += this.angka[i][k] * M1.angka[k][j];
                }
                M.angka[i][j] = tmp;
            }
        }

        return M;
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

    void ReverseCopyMatriks(Matriks M) {
        // meng-copy matriks M ke matriks ini
        this.NeffBar = M.NeffBar;
        this.NeffKol = M.NeffKol;
        for (int i = 1; i <= M.NeffBar; i++) {
            for (int j = 1; j <= M.NeffKol; j++) {
                this.angka[i][j] = M.angka[i][j];
            }
        }
    }

    void ClearMatriks() {
        // mengosongkan matriks
        this.NeffBar = 0;
        this.NeffKol = 0;
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
        // [A|B] -> [A], M -> [B], KSize = ukuran B
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

    int toSegitigaAtas() {
        // mengubah matriks menjadi bentuk matriks segitiga atas
        boolean found;
        int k = 1;
        int cs = 0;
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
                if (i != j) {
                    this.OBESwap(i,j);
                    cs++;
                }
                for (j = i+1; j <= this.NeffBar; j++) {
                    if (this.angka[j][k] != 0) {
                        OBEReplace(j,i,-this.angka[j][k]/this.angka[i][k]);
                    }
                }
                k++;
            }
        }
        return cs;
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

    int CountPivot(int B, int K) {
        // menghitung jumlah pivot point
        // matriks sudah berbentuk echelon
        // 
        boolean found;
        int count = 0;
        for (int i = B; i >= 1; i--) {
            found = false;
            int j = 1;
            while (!found && j <= K) {
                found = (this.angka[i][j] != 0);
                j++;
            }
            if (found) {
                count++;
            }
        }
        return count;
    }

    float DeterminanOBE() {
        // menghitung determinan matriks
        // prekondisi: matriks square
        float temp_d = 1;
        Matriks temp = new Matriks();
        this.CopyMatriks(temp);
        int cs = temp.toSegitigaAtas();
        for (int i = 1; i <= this.NeffBar; i++) {
            temp_d *= temp.angka[i][i];
        }
        return temp_d*(float)Math.pow(-1,cs);
    }

    boolean IsInvertible(){
        return this.DeterminanOBE() != 0;
    }

    void InverseOBE() {
        // menngubah matriks ke bentuk inversenya
        // prekondisi matriks invertible
        Matriks temp = new Matriks();
        this.CopyMatriks(temp);
        temp.Augmented(temp.MatriksIdentitas());
        temp.toReducedEchelon();
        temp.unAugmented(this,this.NeffKol);
    }


	Matriks MinorMatriks(int idxRow, int idxCol){
        Matriks holder = new Matriks();
        holder.NeffBar = this.NeffBar-1;
        holder.NeffKol = this.NeffKol-1;

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


	float DeterminanKofaktor(){
        float sum = 0f;
        int multiplier;
        Matriks holder = new Matriks();
        holder.NeffBar = this.NeffBar-1;
        holder.NeffKol = this.NeffKol-1;
        // if(this.NeffBar == 2){ // BASIS saat 2x2
        //     return this.angka[1][1] * this.angka[2][2] - this.angka[1][2] * this.angka[2][1];
        // }
        if(this.NeffBar == 1){ // BASIS saat 2x2
            return this.angka[1][1];
        }
        else {
            for (int i = 1; i <= this.NeffBar; i++){
                holder = this.MinorMatriks(i, 1);
                if ((i + 1) % 2 == 0){ 
                    multiplier = 1;
                } else {
                    multiplier = -1;
                }
                sum += this.angka[i][1] * multiplier * holder.DeterminanKofaktor(); //RECC
            }
        }
        return sum;
    }

    
	Matriks Kofaktor(){
        int multiplier;
        Matriks MatriksKofaktor = new Matriks();
        MatriksKofaktor.NeffBar = this.NeffBar;
        MatriksKofaktor.NeffKol = this.NeffKol;

        for (int i = 1; i <= this.NeffBar; i++){
            for (int j = 1; j <= this.NeffKol; j++){    
                if((i + j) % 2 == 0){
                    multiplier = 1;
                } else {
                    multiplier = -1;
                }
                
                if(this.MinorMatriks(i, j).DeterminanKofaktor() != 0){
                     MatriksKofaktor.angka[i][j] = multiplier * this.MinorMatriks(i, j).DeterminanKofaktor();
                } else {
                    MatriksKofaktor.angka[i][j] = 0;
                }
                
            }
        }
        return MatriksKofaktor;
    }


    Matriks Transpose(){
        Matriks T = new Matriks();
        T.NeffKol = this.NeffKol;
        T.NeffBar = this.NeffBar;
        for (int i = 1; i <= this.NeffBar; i++){
            for (int j = 1; j <= this.NeffKol; j++){
                T.angka[j][i] = this.angka[i][j];       
            }
        }
        return T;
    }

    Matriks Adjoin(){
        return this.Kofaktor().Transpose();
    }

    Matriks InverseAdjoin(){
        Matriks temp = new Matriks();
        this.CopyMatriks(temp);
        temp = temp.Adjoin();
        temp.KaliKons(1/this.DeterminanKofaktor());
        return temp;
    }

    float SolveSPLKramer(int valNum){
        Matriks b = new Matriks();
        Matriks numerator = new Matriks();
        Matriks denom = new Matriks();
        this.unAugmented(b, 1);
        this.CopyMatriks(numerator);
        this.CopyMatriks(denom);
        this.Augmented(b);
        for (int i = 1; i <= numerator.NeffBar; i++){
            numerator.angka[i][valNum] = b.angka[i][1];
        }
        return numerator.DeterminanOBE()/this.DeterminanOBE();
    }

    float SolveSPLgJordan(int valNum){
        Matriks holder = new Matriks();
        this.CopyMatriks(holder);
        holder.toReducedEchelon();
        return holder.angka[valNum][holder.NeffKol];
    }

    float SolveSPLgauss(int valNum){
        Matriks holder = new Matriks();
        float sum = 0;
        this.CopyMatriks(holder);
        holder.toEchelon();
        int start = holder.NeffKol-1;

        while (start <= 0 && holder.angka[start][start] != 1){
            start--;
        }

        float[] arrayVal = new float[holder.NeffKol-1];
        arrayVal[start] = holder.angka[start][start];

        for (int i = start-1; i > 0; i--){
            if (holder.angka[start][start] == 1){
                for (int j = i+1; j < holder.NeffKol; j++){
                    sum += arrayVal[j]*holder.angka[i][j];
                }
                arrayVal[i] = holder.angka[i][this.NeffKol] - sum;
            } else {
                arrayVal[i] = -99999; //mark untuk variabel bebas
            }
            sum = 0;
        }
        
        return arrayVal[valNum];
    }

    float SolveSPLinverse(int valNum){
        Matriks holder = new Matriks();
        Matriks b = new Matriks();
        this.unAugmented(b, 1);
        this.CopyMatriks(holder);
        this.Augmented(b);
        holder.InverseOBE();
        holder = holder.KaliMat(b);
        return holder.angka[valNum][this.NeffKol];
    }

}