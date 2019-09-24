import java.io.*;
import java.util.*;
import java.lang.Math; 
import java.math.*;


class Matriks{
	// Atribut
	int NeffKol, NeffBar;
	BigDecimal[][] angka = new BigDecimal[100][100];
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
				this.angka[i][j] =  new BigDecimal(baris[j-1]);
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
                        M.angka[M.NeffBar][j] = new BigDecimal(barisParsed[j-1]);
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

	String OutputDataMat(){
        String hasil = "";
		for(int i=1; i<=this.NeffBar; i++){
            for(int j=1; j<=this.NeffKol; j++){
                hasil += this.angka[i][j].setScale(3, RoundingMode.HALF_EVEN);
                if(j!=this.NeffKol){
                    hasil += " ";
                }
            }
            if(i!=this.NeffBar){
                hasil += "\n";
            }
        }
        return hasil;
	}

	void OBESwap(int a, int b){
		// swap baris ke a dan b
		BigDecimal temp = BigDecimal.ZERO;

		for(int i=1; i<=this.NeffKol; i++){
			temp = this.angka[a][i];
			this.angka[a][i] = this.angka[b][i];
			this.angka[b][i] = temp;
		}
	}

	void OBEScale(int a, BigDecimal k){
		// pekalian baris dengan k != 0

        for(int i=1; i<=this.NeffKol; i++){
            if(this.angka[a][i].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0) {
                this.angka[a][i] = this.angka[a][i].multiply(k);
            }
		}
	}

	void OBEReplace(int a, int b, BigDecimal k){
		// elemen baris ke-a ditambah k * elemen baris ke-b
		for(int i=1; i<=this.NeffKol; i++){
            if (this.angka[b][i].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0) {
	    		this.angka[a][i] = this.angka[a][i].add(this.angka[b][i].multiply(k));
            }
		}	
	}

    void KaliKons(BigDecimal k){
        for (int i = 1; i <= this.NeffBar; i++) {
            for (int j = 1; j <= this.NeffKol; j++) {
                this.angka[i][j] = this.angka[i][j].multiply(k);
            }
        }
    }

    public Matriks KaliMat(Matriks M1){
        // perkalian matriks dengan M1
        // PREKONDISI UKURAN this.NeffKol = M1.NeffBar
        BigDecimal tmp = BigDecimal.ZERO;

        Matriks M = new Matriks();
        M.NeffBar = this.NeffBar;
        M.NeffKol = M1.NeffKol;

        for(int i=1; i<=M.NeffBar; i++){
            for(int j=1; j<=M.NeffKol; j++){
                // 1 1 = 
                tmp = BigDecimal.ZERO;
                for(int k=1; k<=this.NeffKol; k++){
                    tmp = tmp.add(this.angka[i][k].multiply(M1.angka[k][j]));
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
        M.NeffBar = this.NeffBar;
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
            for (int j = 1; j <= this.NeffKol; j++) {
                if(i==j){
                    M.angka[i][j] = BigDecimal.ONE;
                }
                else{
                    M.angka[i][j] = BigDecimal.ZERO;
                }
                
            }
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
                found = (this.angka[j][k].abs().setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0);
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
                    if (this.angka[j][k].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0) {
                        OBEReplace(j, i, this.angka[j][k].negate().divide(this.angka[i][k], MathContext.DECIMAL128));
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
                found = this.angka[i][j].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0;
                j++;
            }
            if (found && this.angka[i][j-1].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ONE) != 0) {
                j--;
                OBEScale(i,BigDecimal.ONE.divide(this.angka[i][j], MathContext.DECIMAL128));
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
                found = this.angka[i][j].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0;
                j++;
            }
            if (found) {
                j--;
                for (int k = i-1; k >= 1; k--) {
                    OBEReplace(k,i,this.angka[k][j].negate().divide(this.angka[i][j], MathContext.DECIMAL128));
                }
            }
        }
    }

    BigDecimal DeterminanOBE() {
        // menghitung determinan matriks
        // prekondisi: matriks square
        BigDecimal temp_d = BigDecimal.ONE;
        Matriks temp = new Matriks();
        this.CopyMatriks(temp);
        int cs = temp.toSegitigaAtas();
        for (int i = 1; i <= this.NeffBar; i++) {
            temp_d = temp_d.multiply(temp.angka[i][i]);
        }
        return temp_d.multiply((new BigDecimal(Double.toString(Math.pow(-1,cs)))));
    }

    boolean IsInvertible(){
        return this.DeterminanOBE().setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0;
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


	BigDecimal DeterminanKofaktor(){
        BigDecimal sum = BigDecimal.ZERO;
        int multiplier;
        Matriks holder = new Matriks();
        holder.NeffBar = this.NeffBar-1;
        holder.NeffKol = this.NeffKol-1;
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
                sum = sum.add(this.angka[i][1].multiply(new BigDecimal(multiplier)).multiply(holder.DeterminanKofaktor())); //RECC
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
                
                if(this.MinorMatriks(i, j).DeterminanKofaktor().setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0){
                     MatriksKofaktor.angka[i][j] = this.MinorMatriks(i, j).DeterminanKofaktor().multiply(new BigDecimal(multiplier));
                } else {
                    MatriksKofaktor.angka[i][j] = BigDecimal.ZERO;
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
        temp.KaliKons(BigDecimal.ONE.divide(this.DeterminanKofaktor(), MathContext.DECIMAL128));
        return temp;
    }

    BigDecimal SolveSPLKramer(int valNum){
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
        return numerator.DeterminanOBE().divide(this.DeterminanOBE(), MathContext.DECIMAL128);
    }

    boolean isNoSol(){
    // Matriks yg dioperasikan harus sudah dalam bentuk reducedEchelon
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 1; i <= this.NeffBar; i++){
            for (int j = 1; j < this.NeffKol; j++){
                sum = sum.add(this.angka[i][j].abs());
            }
            if (sum.setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) == 0 && this.angka[i][this.NeffKol].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0){
                System.out.println(this.angka[i][this.NeffKol]);
                return true;
            }
            sum = BigDecimal.ZERO;
        }
        return false;
    }

    boolean isFreeVar(int valNum){
    // Matriks yg dioperasikan harus sudah dalam bentuk reducedEchelon
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 1; i <= this.NeffBar; i++){
            sum = sum.add(this.angka[i][valNum]);
        }
        if (sum.setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ONE) != 0){
            return true;
        } else {
            return false;
        }
    }



    String SolveSPLgJordan(int valNum){
        // prekondisi: ada solusi
        int search = this.NeffBar;
        String solution = "";
        String simbol;
        BigDecimal temp;
        this.toReducedEchelon();
        while (search >= 1 && this.angka[search][valNum].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ONE) != 0){
            search--;
        }
        if (search >= 1) {
            for (int i = this.NeffKol; i > valNum; i--) {
                temp = this.angka[search][i];
                simbol = "";
                if (i != this.NeffKol) {
                    temp = temp.multiply(BigDecimal.ONE.negate());
                    simbol = "s" + Integer.toString(i);
                }
                if (temp.setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0) {
                    if (temp.compareTo(BigDecimal.ZERO) > 0 && solution != "") {
                        solution += "+ ";
                    } 
                    else if (temp.setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) < 0){ 
                        solution += "- "; 
                    }
                    solution +=  temp.setScale(3, RoundingMode.HALF_EVEN).abs() + simbol + " ";
                }
            }
        } else {
            solution += "s" + Integer.toString(valNum);
        }
        return solution;
    }
    
    String SolveSPLgauss(int valNum){
        // prekondisi: ada solusi
        BigDecimal[] sum = new BigDecimal[this.NeffKol+1];
        int start = this.NeffKol-1;
        int search = this.NeffBar;
        Matriks testFree = new Matriks();
        String solution = "";
        String simbol;
        BigDecimal temp;
        
        this.CopyMatriks(testFree);
        this.toEchelon();
        testFree.toReducedEchelon();
        if (testFree.isFreeVar(valNum)) {
            return "s" + Integer.toString(valNum);
        }

        while (search >= 1 && this.angka[search][valNum].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ONE) != 0){
            search--;
        }
        
        for (int i = 1; i <= this.NeffKol; i++) {
            sum[i] = this.angka[search][i];
        }
        for (int i = this.NeffBar; i > search; i--) {
            int j = 1;
            while (j <= this.NeffKol && this.angka[i][j].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ONE) != 0){
                j++;
            }
            if (j <= this.NeffKol) {
                temp = sum[j];
                for (int k = 1; k <= this.NeffKol; k++) {
                    if (this.angka[i][k].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0) {
                        sum[k] = sum[k].subtract(this.angka[i][k].multiply(temp));
                    }
                }
            }
        }
        sum[valNum] = sum[this.NeffKol];
        for (int i = valNum; i <= this.NeffKol-1; i++) {
            if (sum[i].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) != 0) {
                if (i != valNum) {
                    sum[i] = sum[i].negate();
                    simbol = "s" + Integer.toString(i);
                } else {
                    simbol = "";
                }
                if (sum[i].compareTo(BigDecimal.ZERO) > 0 && solution != "") {
                    solution += "+ ";
                } 
                else if (sum[i].setScale(10, RoundingMode.HALF_EVEN).compareTo(BigDecimal.ZERO) < 0){ 
                    solution += "- "; 
                }
                solution +=  sum[i].setScale(3, RoundingMode.HALF_EVEN).abs() + simbol + " ";
            }
        }
        return solution;
    }

    BigDecimal SolveSPLinverse(int valNum){
        Matriks hasil = new Matriks();
        Matriks clone = new Matriks();
        this.CopyMatriks(clone);
        clone.unAugmented(hasil, 1);
        hasil = hasil.KaliMat(clone.InverseAdjoin());
        return hasil.angka[valNum][1];
    }

}