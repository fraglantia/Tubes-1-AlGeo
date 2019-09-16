class DriverMatriks{
	public static void main(String[] args){
		Matriks M = new Matriks();
		M.InputDataMat();
		M.OutputDataMat();
		M.Inverse();
		M.OutputDataMat();
		M.toReducedEchelon();
		M.OutputDataMat();
		M.OBESwap(1, 2);
		M.OutputDataMat();
		M.OBEScale(1, 10);
		M.OutputDataMat();
		M.OBEReplace(3, 1, 3);
		M.OutputDataMat();
	}
}