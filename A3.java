/**
 * Matrix
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class A3 {

    static class Matrix{
        protected int row,column;
        protected int mat[][];
        protected ArrayList<String> myTypes;
        protected int scalar_value;
        protected int dia[];
        protected int determinant=Integer.MIN_VALUE;
        protected int UID;
    //     Matrix(int row,int column,int[][] mat){
    //         this.row=row;
    //         this.column=column;
    //         this.mat = new int[row][column];
        
    //     for(int i=0;i<row;i++){
    //         for(int j=0;j<column;j++){
    //             this.mat[i][j]=mat[i][j];
    //         }
    //     }
    // }
        public void initialize(int r,int c,int mat[][]){
            this.row=r;
            this.column=c;
            this.mat=mat;

        }
        public int[][] getMatrix(){
            return mat;
        }
        public int getRow(){
            return this.row;
        }
        public int getCol(){
            return this.column;
        }
        public int[][] cofactor(int[][] mat,int i,int j){
            int r=mat.length;
            int cofact[][]=new int[r-1][r-1];
            int row=0,col=0;
            for(int ro=0;ro<r;ro++){
                for(int co=0;co<r;co++){
                    if(ro!=i && co!=j){
                        cofact[row][col]=mat[ro][co];
                        col++;
                        if(col==r-1){
                            col=0;row++;
                        }
                    }
                }
            }
            return cofact;
        }
        public boolean isRectangular(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("rectangular")){
                    return true;
                }
            }
            return false;
        }
        public boolean isSquare(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("square")){
                    return true;
                }
            }
            return false;
        }
        public boolean isSymmetric(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("symmetric")){
                    return true;
                }
            }
            return false;
        }
        public boolean isSkew_symmetric(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("skew-symmetric")){
                    return true;
                }
            }
            return false;
        }
        public boolean isUT(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("upper triangular")){
                    return true;
                }
            }
            return false;
        }
        public boolean isLT(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("lower triangular")){
                    return true;
                }
            }
            return false;
        }
        public boolean isNull(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("null")){
                    return true;
                }
            }
            return false;
        }
        public boolean isOnes(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("ones")){
                    return true;
                }
            }
            return false;
        }
        public boolean isRow(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("row")){
                    return true;
                }
            }
            return false;
        }
        public boolean isColumn(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("column")){
                    return true;
                }
            }
            return false;
        }
        public boolean isDiagonal(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("diagonal")){
                    return true;
                }
            }
            return false;
        }
        public boolean isScalar(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("scalar")){
                    return true;
                }
            }
            return false;
        }
        public boolean isIdentity(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("identity")){
                    return true;
                }
            }
            return false;
        }
        public boolean isSingleton(){
            for(int i=0;i<myTypes.size();i++){
                if(this.myTypes.get(i).equalsIgnoreCase("singleton")){
                    return true;
                }
            }
            return false;
        }
        public boolean isSingular(){
    
                if(this.isSquare() && this.determinant==0){
                    return true;
                }
            
            return false;
        }
        public int calculateDeterminant(int[][] mat){//credits:https://www.geeksforgeeks.org/java-program-to-find-the-determinant-of-a-matrix/
            int det=0;
            int r=mat.length;
            if(r==1){
                return mat[0][0];
            }
            int sign=1;
            for(int i=0;i<r;i++){
                int cofact[][]=cofactor(mat,0,i);
                det+=sign*mat[0][i]*calculateDeterminant(cofact);

                sign=-sign;
            }
            return det;
        }

        public int[][] adjoint(int [][] matrix){
            int d=matrix.length;
            int verticalsign=-1;
            int adj[][]=new int[d][d];
            for (int i = 0; i < d; i++) {
                verticalsign=-1*verticalsign;
                int sign=verticalsign;
                for (int j = 0; j < d; j++) {
                    
                    int [][]cofact= cofactor(matrix, i, j);
                    adj[i][j]=sign*calculateDeterminant(cofact);
                    sign=-sign;
                }
            }
            adj=transpose(adj);//transpose to get adjoint
            return adj;
        }
        public int[][] inverse(){
            if(this.isRectangular()){
                System.out.println("Inverse does not exist");
                return null;
            }
            else{
                if(this.determinant==0){
                    System.out.println("Inverse does not exist");
                    return null;
                }
                int inv[][]=new int[this.getRow()][this.getCol()];
                int adj[][]=adjoint(this.getMatrix());
                for(int i=0;i<this.getRow();i++){
                    for (int j = 0; j < this.getCol(); j++) {
                        inv[i][j]=(adj[i][j]/this.determinant);
                        
                    }
                }
                return inv;
            }
            
            
        }
        public int[][] transpose(){
            int r=this.getMatrix().length;
            int c=this.getMatrix()[0].length;
            int t[][]=new int[c][r];
            for(int i=0;i<c;i++){
                for(int j=0;j<r;j++){
                    t[i][j]=this.getMatrix()[j][i];
                }
            }
            return t;
        }
        public int[][] transpose(int[][] m){//overloading
            int r=m.length;
            int c=m[0].length;
            int t[][]=new int[c][r];
            for(int i=0;i<c;i++){
                for(int j=0;j<r;j++){
                    t[i][j]=m[j][i];
                }
            }
            return t;
        }
        public static ArrayList<String> getTypes(Matrix m){
            int r=m.getRow();
            int c=m.getCol();
            ArrayList<String> types= new ArrayList<>();
            if(r==c){
                types.add("square");
                if(m.calculateDeterminant(m.getMatrix())==0){
                    types.add("Singular");
                }
                int [][]trans=m.transpose();
                boolean symm=true;
                boolean skewsymm=true;
                boolean uppert=true;
                boolean lowert=true;
                boolean diagonal = true;
                boolean scalar=true;
                boolean identity = true;
                boolean singleton = false;
                boolean Null = true;
                boolean ones= true;
                int scale = m.getMatrix()[0][0];
                for(int i=0;i<r;i++){
                    for(int j=0;j<c;j++){
                        if(i>j && m.getMatrix()[i][j]!=0){
                            uppert=false;
                        }
                        if(i<j && m.getMatrix()[i][j]!=0){
                            lowert=false;
                        }
                        if(m.getMatrix()[i][j]!=trans[i][j]){
                            symm=false;
                        }
                        if(m.getMatrix()[i][j]!=-(trans[i][j])){
                            skewsymm=false;
                        }
                        if(i!=j && m.getMatrix()[i][j]!=0){
                            diagonal = false;
                            scalar = false;
                            identity = false;
                        }
                        if(i==j && m.getMatrix()[i][j]!=scale){
                            scalar=false;
                        }
                        if(i==j && m.getMatrix()[i][j]!=1){
                            identity = false;
                        }
                        if(r==1 && c==1){
                            singleton = true;
                        }
                        if(m.getMatrix()[i][j]!=0){
                            Null=false;
                        }
                        if(m.getMatrix()[i][j]!=1){
                            ones=false;
                        }
                    }
                }
                if(Null){
                    diagonal=false;
                    scalar=false;
                    identity=false;
                    uppert=false;
                    lowert=false;
                }
                //in accordance to my inheritance tree
                if(Null){  
                    types.add("null");
                }
                if(ones){
                    types.add("ones");
                }
                if(symm){
                    types.add("symmetric");
                }
                if(skewsymm){
                    types.add("skew-symmetric");
                }
                if(lowert){
                    types.add("lower triangular");
                }
                if(uppert){
                    types.add("upper triangular");
                }
                if(diagonal){
                    types.add("diagonal");
                }
                if(scalar){
                    types.add("scalar");
                }
                if(identity){
                    types.add("identity");
                }
                if(singleton){
                    types.add("singleton");
                }
            }
            else{
                types.add("rectangular");

                boolean Null = true;
                boolean ones = true;
                for(int i=0;i<r;i++){
                    for(int j=0;j<c;j++){
                        if(m.getMatrix()[i][j]!=0){
                            Null = false;
                        }
                        if(m.getMatrix()[i][j]!=1){
                            ones =false;
                        }
                    }
                }
                if(Null){
                    types.add("null");
                }
                if(ones){
                    types.add("ones");
                }
                if(r==1){
                    types.add("row");
                }
                else if(c==1){
                    types.add("column");
                }
                
            }
            return types;
        }

        public static void means(Matrix x){
            int mat[][];
            if(x.isScalar() || x.isIdentity()){
                int scale=1;
                if(x.isScalar()){
                    scale = x.scalar_value;
                } 
                mat=new int[x.getRow()][x.getCol()];
                for (int i = 0; i < x.getRow(); i++) {
                    for (int j = 0; j < x.getCol(); j++) {
                        if(i==j){
                            mat[i][j]=scale;
                        }
                        else{
                            mat[i][j]=0;
                        }
                    }
                }
            }
            else if(x.isNull() || x.isOnes()){
                int fill=1;
                if(x.isNull()){
                    fill=0;
                }
                mat=new int[x.getRow()][x.getCol()];
                for (int i = 0; i < x.getRow(); i++) {
                    for (int j = 0; j < x.getCol(); j++) {
                        mat[i][j]=fill;
                    }
                }    
            }
            else if(x.isDiagonal()){
                mat=new int[x.getRow()][x.getCol()];
                int k=0;
                for (int i = 0; i < x.getRow(); i++) {
                    for (int j = 0; j < x.getCol(); j++) {
                        if(i==j){
                            mat[i][j]=x.dia[k++];
                        }
                        else{
                            mat[i][j]=0;
                        }
                    }
                }
            }
            else{
                mat=x.getMatrix();
            }
            int rw=0,cw=0, total=0;
            for(int i=0;i<mat.length;i++){
                rw=0;
                for(int j=0;j<mat[0].length;j++){
                    rw+=mat[i][j];total+=mat[i][j];
                    
                }
                System.out.println("Mean of row "+ (i+1) +" = "+ (1.0*rw/mat[0].length));
            }
            for(int i=0;i<mat[0].length;i++){
                cw=0;
                for(int j=0;j<mat.length;j++){
                    cw+=mat[j][i];
                    
                }
                System.out.println("Mean of column "+ (i+1) +" = "+ (1.0*cw/mat.length));
            }
            System.out.println("Mean of matrix  = "+ (1.0*total/(mat.length*mat[0].length)));
        }

        public void updateMatrix()throws IOException{
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            if(this.isIdentity() || this.isNull() || this.isOnes()){
                System.out.println("Can not update contents of this matrix.");
                return;
            }
            if(this.isSingular()){
                int ch=1;
                while(ch!=0){
                    System.out.println("Enter row index and column index to update");
                    int ri=Integer.parseInt(br.readLine());
                    int ci=Integer.parseInt(br.readLine());
        
                        System.out.println("Enter new element to replace");
                        int ele=Integer.parseInt(br.readLine());
                        int backup=mat[ri][ci];
                        mat[ri][ci]=ele;
                        if(calculateDeterminant(mat)!=0){
                            System.out.println("Singular matrix made non singular, reverting");
                            mat[ri][ci]=backup;
                        }
                        System.out.println("Update more elements? 1-> yes 0->no");
                        ch=Integer.parseInt(br.readLine());
                    }
            }
            if(this.isSingleton()){
                System.out.println("Enter new element to replace");
                int ele=Integer.parseInt(br.readLine());
                if(ele==1){
                    System.out.println("Cant make identity");
                    return;
                }
                this.mat[0][0]=ele;
            }
            else if(this.isScalar()){
                System.out.println("Enter new scale");
                int ele=Integer.parseInt(br.readLine());
                if(ele==1){
                    System.out.println("Cant make scalar to identity");
                    return;
                }
                this.scalar_value=ele;
                
            }
            else if(this.isDiagonal()){
                int ch=1;
                while(ch!=0){
                    System.out.println("Enter row index and column index to update");
                    int ri=Integer.parseInt(br.readLine());
                    int ci=Integer.parseInt(br.readLine());
                    if(ri==ci){
                        System.out.println("Enter new element to replace");
                        int ele=Integer.parseInt(br.readLine());
                        this.dia[ri]=ele;
                        System.out.println("Update more elements? 1-> yes 0->no");
                        ch=Integer.parseInt(br.readLine());
                    }
                    else{
                        System.out.println("Can only replace diagonl elements");
                        return;
                    }
                }
            }
            else if(this.isSymmetric()){
                int ch=1;
                while(ch!=0){
                    System.out.println("Enter row index and column index to update");
                    int ri=Integer.parseInt(br.readLine());
                    int ci=Integer.parseInt(br.readLine());
        
                        System.out.println("Enter new element to replace");
                        int ele=Integer.parseInt(br.readLine());
                        mat[ri][ci]=ele;
                        mat[ci][ri]=ele;//making automatically symmetric 
                        System.out.println("Update more elements? 1-> yes 0->no");
                        ch=Integer.parseInt(br.readLine());
                    }
            }
            else if(this.isSkew_symmetric()){
                int ch=1;
                while(ch!=0){
                    System.out.println("Enter row index and column index to update");
                    int ri=Integer.parseInt(br.readLine());
                    int ci=Integer.parseInt(br.readLine());
        
                        System.out.println("Enter new element to replace");
                        int ele=Integer.parseInt(br.readLine());
                        mat[ri][ci]=ele;
                        mat[ci][ri]=-ele;//making automatically skew-symmetric 
                        System.out.println("Update more elements? 1-> yes 0->no");
                        ch=Integer.parseInt(br.readLine());
                    }
            }
            else if(this.isUT()){
                int ch=1;
                while(ch!=0){
                    System.out.println("Enter row index and column index to update");
                    int ri=Integer.parseInt(br.readLine());
                    int ci=Integer.parseInt(br.readLine());
        
                        System.out.println("Enter new element to replace");
                        int ele=Integer.parseInt(br.readLine());
                        if(ci>ri || ci==ri)
                        mat[ri][ci]=ele;
                        else{
                            System.out.println("Can change only upper triangle");
                            
                        }
                        System.out.println("Update more elements? 1-> yes 0->no");
                        ch=Integer.parseInt(br.readLine());
                    }
            }
            else if(this.isLT()){
                int ch=1;
                while(ch!=0){
                    System.out.println("Enter row index and column index to update");
                    int ri=Integer.parseInt(br.readLine());
                    int ci=Integer.parseInt(br.readLine());
        
                        System.out.println("Enter new element to replace");
                        int ele=Integer.parseInt(br.readLine());
                        if(ci<ri || ci==ri)
                        mat[ri][ci]=ele;
                        else{
                            System.out.println("Can change only upper triangle");
                            
                        }
                        System.out.println("Update more elements? 1-> yes 0->no");
                        ch=Integer.parseInt(br.readLine());
                    }
            }
            else if(this.isColumn() || this.isRow() || this.isRectangular() || this.isSquare()){
                int ch=1;
                while(ch!=0){
                    System.out.println("Enter row index and column index to update");
                    int ri=Integer.parseInt(br.readLine());
                    int ci=Integer.parseInt(br.readLine());
        
                        System.out.println("Enter new element to replace");
                        int ele=Integer.parseInt(br.readLine());
                        mat[ri][ci]=ele;
                        System.out.println("Update more elements? 1-> yes 0->no");
                        ch=Integer.parseInt(br.readLine());
                    }
            }
            
        }

        public static int[][] add(Matrix m1,Matrix m2){
             if(m1.getRow()!=m2.getRow()|| m1.getCol()!=m2.getCol()){
                 System.out.println("Dimensions mismatch can't add");
                 return null;
             }
             int addm[][]=new int[m1.getRow()][m1.getCol()];
             for (int i = 0; i < m1.getRow(); i++) {
                 for (int j = 0; j < m1.getCol(); j++) {
                     addm[i][j]=m1.getMatrix()[i][j]+m2.getMatrix()[i][j];
                 }
             }
             return addm;
        }
        public static int[][] subtract(Matrix m1,Matrix m2){
            if(m1.getRow()!=m2.getRow()|| m1.getCol()!=m2.getCol()){
                System.out.println("Dimensions mismatch can't subtract");
                return null;
            }
            int subm[][]=new int[m1.getRow()][m1.getCol()];
            for (int i = 0; i < m1.getRow(); i++) {
                for (int j = 0; j < m1.getCol(); j++) {
                    subm[i][j]=m1.getMatrix()[i][j]-m2.getMatrix()[i][j];
                }
            }
            return subm;
       }
       public static int[][] multiply(Matrix m1,Matrix m2){
        if(m1.getCol()!=m2.getRow()){
            System.out.println("Dimensions mismatch can't add");
            return null;
        }
        int mulm[][]=new int[m1.getRow()][m2.getCol()];
        if(m1.isNull() || m2.isNull()){
            return mulm;// efficiency
        }
        for (int i =0;i<m1.getRow();i++) {
            for (int j= 0;j<m2.getCol();j++) {
                for (int k=0; k < m2.getRow(); k++)
                    mulm[i][j]+=m1.getMatrix()[i][k]*m2.getMatrix()[k][j];
            }
        }
        return mulm;
   }
   public static int[][] divide(Matrix m1,Matrix m2){
    if(m2.isNull()){
        System.out.println("Can't divide by a null matrix");
        return null;
    }
    if(m2.isRectangular()){
        System.out.println("Cant divide by given matrix as it is rectangular and cant be inversed");
        return null;
    }
    int temp[][]=m2.inverse();
    Matrix tm = new Matrix();
    tm.initialize(temp.length, temp[0].length, temp);
    return multiply(m1, tm);
    }
}

    static class rectangular extends Matrix{
        public void initialize(int [][]mat,ArrayList<String> types){
            row=mat.length;
            column=mat[0].length;
            this.mat=mat;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class NULL extends Matrix{
    
        public void initialize(int r,int c,ArrayList<String> types){
            row=r;
            column=c;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            int m[][]=new int[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    m[i][j]=0;
                }
            }
            return m;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class Ones extends Matrix{
        //STORED without an array;
        public void initialize(int r,int c,ArrayList<String> types){
            row=r;
            column=c;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            int m[][]=new int[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    m[i][j]=1;
                }
            }
            return m;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class Row extends rectangular{
        
        @Override
        public void initialize(int mat[][],ArrayList<String> types){//OVERRIDING
            row=1;
            column=mat[0].length;
            this.mat=mat;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class Column extends rectangular{
        
        
        @Override
        public void initialize(int mat[][],ArrayList<String> types){//OVERRIDING
            column=1;
            row=mat.length;
            this.mat=mat;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }

    static class square extends Matrix{
        
        public void initialize(int [][]mat,ArrayList<String> types){
            row=mat.length;
            column=mat.length;
            this.mat=mat;
            myTypes=types;
            determinant=calculateDeterminant(this.mat);
        }
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class Symmetric extends square{
        
        @Override
        public void initialize(int [][]mat,ArrayList<String> types){
            row=mat.length;
            column=mat.length;
            this.mat=mat;
            myTypes=types;
        }
        
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class Diagonal extends Symmetric{
        //storing in compressed manner
        
        public void initialize(int []dia,ArrayList<String> types){ //OVER LOADING
            row=dia.length;
            column=dia.length;
            this.dia=dia;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            int [][] m = new int[row][column];
            int k=0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j <column; j++) {
                    if(i==j){
                        m[i][j]=dia[k++];
                    }
                    else{
                        m[i][j]=0;
                    }
                }
            }
            return m;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }

    static class Scalar extends Diagonal{
        //storing in compressed manner
        
        public void initialize(int d, int scalar_value,ArrayList<String> types){ //OVER LOADING, d=dimension
            row=d;
            column=d;
            myTypes=types;
            this.scalar_value=scalar_value;
        }
        @Override
        public int[][] getMatrix(){
            int [][] m = new int[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j <column; j++) {
                    if(i==j){
                        m[i][j]=scalar_value;
                    }
                    else{
                        m[i][j]=0;
                    }
                }
            }
            return m;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class Singleton extends Scalar{
        
        @Override
        public void initialize(int mat[][],ArrayList<String> types){//OVERRIDING
            column=1;
            row=1;
            this.mat=mat;
            myTypes=types;
            scalar_value=mat[0][0];
        }
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }

    }
    static class Identity extends Scalar{
        
        public void initialize(int d,ArrayList<String> types){ //OVER LOADING
            row=d;
            column=d;
            myTypes=types;
            scalar_value=1;
        }
        @Override
        public int[][] getMatrix(){
            int [][] m = new int[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j <column; j++) {
                    if(i==j){
                        m[i][j]=1;
                    }
                    else{
                        m[i][j]=0;
                    }
                }
            }
            return m;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class Skew_Symmetric extends square{

        
        @Override
        public void initialize(int [][]mat,ArrayList<String> types){
            row=mat.length;
            column=mat.length;
            this.mat=mat;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class UpperTriangular extends square{
        
        @Override
        public void initialize(int [][]mat,ArrayList<String> types){
            row=mat.length;
            column=mat.length;
            this.mat=mat;
            myTypes=types;
        }
        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }
    static class LowerTriangular extends square{
        
        @Override
        public void initialize(int [][]mat,ArrayList<String> types){
            row=mat.length;
            column=mat.length;
            this.mat=mat;
            myTypes=types;
        }

        @Override
        public int[][] getMatrix(){
            return mat;
        }
        @Override
        public int getRow(){
            return this.row;
        }
        @Override
        public int getCol(){
            return this.column;
        }
    }

    // static class NULLsq extends square{

    //     public void initialize(int d){//overloading
    //         row=d;
    //         column=d;
    //     }
    // }
    // static class Onessq extends square{

    //     public void initialize(int d){//overloading
    //         row=d;
    //         column=d;
    //     }
    // }


    //credits: 

   

    public static Matrix takeInput()throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Number of rows : ");
        int r=Integer.parseInt(br.readLine());
        System.out.println("Enter Number of columns : ");
        int c=Integer.parseInt(br.readLine());
        System.out.println("Enter elements : ");
        
        int [][]mat=new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                mat[i][j]=Integer.parseInt(br.readLine());
            }
        }
        
        Matrix x=new Matrix();
        x.initialize(r,c,mat);
        ArrayList<String> types = Matrix.getTypes(x);
       
        String tp = types.get(types.size()-1);
        
        if(tp.equalsIgnoreCase("Identity")){
            Identity i=new Identity();
            i.initialize(r,types);
            return i;
        }
        if(tp.equalsIgnoreCase("Scalar")){
            Scalar s=new Scalar();
            s.initialize(r,mat[0][0],types);
            return s;
        }
        if(tp.equalsIgnoreCase("Diagonal")){
            Diagonal d =new Diagonal();
            int[] dia =new int[r];
            for(int i=0;i<r;i++){
                dia[i]=mat[i][i];
            }
            d.initialize(dia, types);
            return d;
        }
        if(tp.equalsIgnoreCase("Symmetric")){
            Symmetric sym=new Symmetric();
            sym.initialize( mat,types);
            return sym;
        }
        if(tp.equalsIgnoreCase("skew-symmetric")){
            Skew_Symmetric sks=new Skew_Symmetric();
            sks.initialize( mat,types);
            return sks;
        }
        if(tp.equalsIgnoreCase("upper triangular")){
            UpperTriangular up=new UpperTriangular();
            up.initialize(mat, types);
            return up;
        }
        if(tp.equalsIgnoreCase("Lower triangular")){
            LowerTriangular lt=new LowerTriangular();
            lt.initialize(mat, types);
            return lt;
        }
        if(tp.equalsIgnoreCase("NULL")){
            NULL nl=new NULL();
            nl.initialize(r, c, types);
            return nl;
        }
        if(tp.equalsIgnoreCase("ones")){
            Ones on=new Ones();
            on.initialize(r, c, types);
            return on;
        }
        if(tp.equalsIgnoreCase("row")){
            Row ro=new Row();
            ro.initialize( mat,types);
            return ro;
        }
        if(tp.equalsIgnoreCase("column")){
            Column co=new Column();
            co.initialize( mat,types);
            return co;
        }
        if(tp.equalsIgnoreCase("Singleton")){
            Singleton st =new Singleton();
            st.initialize(mat, types);
            return st;
        }
        if(tp.equalsIgnoreCase("square")){
            square sq=new square();
            sq.initialize(mat, types);
            return sq;
        }
        if(tp.equalsIgnoreCase("rectangular")){
            rectangular rct =new rectangular();
            rct.initialize(mat, types);
            return rct;
        }
        if(tp.equalsIgnoreCase("singular")){
            square sq=new square();
            sq.initialize(mat, types);
            return sq;  
        }
        System.out.println("Will never reach here");
        return x;
    }
    public static void displayMatrices(){
        for(int i=0;i<allMatrix.size();i++){
            System.out.println("[ "+(i+1)+" ]");
            display(allMatrix.get(i));
            System.out.println();
        }
    }
    public static void display(Matrix x){
        if(x.isScalar() || x.isIdentity()){
            int scale=1;
            if(x.isScalar()){
                scale = x.scalar_value;
            } 
            int mat[][]=new int[x.getRow()][x.getCol()];
            for (int i = 0; i < x.getRow(); i++) {
                for (int j = 0; j < x.getCol(); j++) {
                    if(i==j){
                        mat[i][j]=scale;
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            for (int i = 0; i < x.getRow(); i++) {
                for (int j = 0; j < x.getCol(); j++) {
                    System.out.print(mat[i][j]+" ");
                }
                System.out.println();
            }

        }
        else if(x.isNull() || x.isOnes()){
            int fill=1;
            if(x.isNull()){
                fill=0;
            }
            int mat[][]=new int[x.getRow()][x.getCol()];
            for (int i = 0; i < x.getRow(); i++) {
                for (int j = 0; j < x.getCol(); j++) {
                    mat[i][j]=fill;
                }
            }
            for (int i = 0; i < x.getRow(); i++) {
                for (int j = 0; j < x.getCol(); j++) {
                    System.out.print(mat[i][j]+" ");
                }
                System.out.println();
            }    

        }
        else if(x.isDiagonal()){
            int mat[][]=new int[x.getRow()][x.getCol()];
            int k=0;
            for (int i = 0; i < x.getRow(); i++) {
                for (int j = 0; j < x.getCol(); j++) {
                    if(i==j){
                        mat[i][j]=x.dia[k++];
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            for (int i = 0; i < x.getRow(); i++) {
                for (int j = 0; j < x.getCol(); j++) {
                    System.out.print(mat[i][j]+" ");
                }
                System.out.println();
            }

        }
        else{
        for (int i = 0; i < x.getRow(); i++) {
            for (int j = 0; j < x.getCol(); j++) {
                System.out.print(x.getMatrix()[i][j]+" ");
            }
            System.out.println();
        }
    }
    }

    public static void display(int m[][]){//overloading
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static Matrix createMatrix()throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter type of matrix : ");
        String t=br.readLine();
        int [][]mat;
        Matrix m;
        ArrayList<String> types;
        if(t.equalsIgnoreCase("rectangular")){
            System.out.println("Enter Number of rows : ");
            int r=Integer.parseInt(br.readLine());
            System.out.println("Enter Number of columns : ");
            int c=Integer.parseInt(br.readLine());
            mat = new int[r][c];
            for(int i=0;i<r;i++){
                for(int j=0;j<c;j++){
                    mat[i][j]=(int)(Math.random()*10);
                }
            }
            m = new Matrix();
            m.initialize(r, c, mat);
            types=Matrix.getTypes(m);
            rectangular rct= new rectangular();
            rct.initialize(mat,types);
            return rct;
        }
        if(t.equalsIgnoreCase("row")){
            System.out.println("Enter Number of columns : ");
            int c=Integer.parseInt(br.readLine());
            mat = new int[1][c];
            for(int i=0;i<c;i++){
                mat[0][i]=(int)(Math.random()*10);
            }
            m = new Matrix();
            m.initialize(1, c, mat);
            types=Matrix.getTypes(m);
            Row ro = new Row();
            ro.initialize( mat,types);
            return ro;
        }
        if(t.equalsIgnoreCase("column")){
            System.out.println("Enter Number of rows : ");
            int r=Integer.parseInt(br.readLine());
            mat = new int[r][1];
            for(int i=0;i<r;i++){
                mat[i][0]=(int)(Math.random()*10);
            }
            m = new Matrix();
            m.initialize(r,1, mat);
            types=Matrix.getTypes(m);
            Column ro = new Column();
            ro.initialize( mat,types);
            return ro;
        }
        if(t.equalsIgnoreCase("null")){
            System.out.println("Enter Number of rows : ");
            int r=Integer.parseInt(br.readLine());
            System.out.println("Enter Number of columns : ");
            int c=Integer.parseInt(br.readLine());
            mat = new int[r][c];
            for(int i=0;i<r;i++){
                for(int j=0;j<c;j++){
                    mat[i][j]=0;
                }
            }
            m = new Matrix();
            m.initialize(r, c, mat);
            types=Matrix.getTypes(m);
            NULL nu= new NULL();
            nu.initialize(r,c,types);
            return nu;
        }
        if(t.equalsIgnoreCase("ones")){
            System.out.println("Enter Number of rows : ");
            int r=Integer.parseInt(br.readLine());
            System.out.println("Enter Number of columns : ");
            int c=Integer.parseInt(br.readLine());
            mat = new int[r][c];
            for(int i=0;i<r;i++){
                for(int j=0;j<c;j++){
                    mat[i][j]=1;
                }
            }
            m = new Matrix();
            m.initialize(r, c, mat);
            types=Matrix.getTypes(m);
            Ones on= new Ones();
            on.initialize(r,c,types);
            return on;
        }
        if(t.equalsIgnoreCase("Square")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    mat[i][j]=(int)(Math.random()*10);
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            square sq=new square();
            sq.initialize(mat,types);
            return sq;
        }
        if(t.equalsIgnoreCase("symmetric")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(i==j || j>i){
                        mat[i][j]=(int)(Math.random()*10);
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(j<i){
                        mat[i][j]=mat[j][i];
                    }
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            Symmetric sy=new Symmetric();
            sy.initialize(mat,types);
            return sy;
        }
        if(t.equalsIgnoreCase("upper triangular")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(i==j || j>i){
                        mat[i][j]=(int)(Math.random()*10);
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            UpperTriangular sy=new UpperTriangular();
            sy.initialize(mat,types);
            return sy;
        }
        if(t.equalsIgnoreCase("lower triangular")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(i==j || j<i){
                        mat[i][j]=(int)(Math.random()*10);
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            LowerTriangular sy=new LowerTriangular();
            sy.initialize(mat,types);
            return sy;
        }
        if(t.equalsIgnoreCase("skew-symmetric")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(j>i){
                        mat[i][j]=(int)(Math.random()*10);
                    }
                    else if(i==j){
                        mat[i][j]=0;
                    }
                }
            }
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(j<i){
                        mat[i][j]=-mat[j][i];
                    }
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            Skew_Symmetric sy=new Skew_Symmetric();
            sy.initialize(mat,types);
            return sy;
        }
        if(t.equalsIgnoreCase("diagonal")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
            int dia[]=new int[d];
            int k=0;
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(i==j){
                        mat[i][j]=(int)(Math.random()*10);
                        dia[k++]=mat[i][j];
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            Diagonal di = new Diagonal();
            di.initialize(dia,types);
            return di;
        }
        if(t.equalsIgnoreCase("scalar")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
        
            int scale=(int)(Math.random()*10);
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(i==j){
                        mat[i][j]=scale;
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            Scalar di = new Scalar();
            di.initialize(d,scale,types);
            return di;
        }
        if(t.equalsIgnoreCase("singular")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
            if(d==1){
                mat[0][0]=0;
            }
            else{
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(i==0 || i>1){
                        mat[i][j]=(int)(Math.random()*10);
                    }
                    else{
                        mat[i][j]=mat[i-1][j];
                    }
                }
            }
        }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            square di = new square();
            di.initialize(mat,types);
            return di;
        }
        if(t.equalsIgnoreCase("Identity")){
            System.out.println("Enter dimension: ");
            int d=Integer.parseInt(br.readLine());
            mat = new int[d][d];
           
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    if(i==j){
                        mat[i][j]=1;
                    }
                    else{
                        mat[i][j]=0;
                    }
                }
            }
            m = new Matrix();
            m.initialize(d, d, mat);
            types=Matrix.getTypes(m);
            Identity di = new Identity();
            di.initialize(d,types);
            return di;
        }
        if(t.equalsIgnoreCase("singleton")){
            
            mat = new int[1][1];
        
            mat[0][0]=(int)(Math.random()*10);
            
            
            m = new Matrix();
            m.initialize(1, 1, mat);
            types=Matrix.getTypes(m);
            Singleton di = new Singleton();
            di.initialize(mat,types);
            return di;
        }

        return null;
    }
    static int matrixCount=0;
    static ArrayList<Matrix> allMatrix;
    public static void main(String[] args)throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("1. Take matrices as input and label them with appropriate matrix-types.\n2. Create matrices of requested matrix-types and label them with appropriate matrix-types.\n3. Change the elements of a matrix as long as the fixed matrix-type labels remain valid.\n4. Display all the matrix-type labels of a requested matrix.\n\n5. Perform addition, subtraction, multiplication & division.\n6. Perform element-wise operations.[NOT DONE]\n7. Transpose matrices.\n8. Inverse matrices.\n9. Compute means: row-wise mean, column-wise mean, mean of all the elements.\n10. Compute determinants.\n11. Use singleton matrices as scalars, if requested.\n12. Compute A+AT for a matrix A.\n13. Compute Eigen vectors and values[NOT DONE].\n14. Solve sets of linear equations using matrices. [NOT DONE]\n15. Retrieve all the existing matrices (entered or created) having requested matrix-type labels.");

        System.out.println("Enter choice");
        int ch = Integer.parseInt(br.readLine());
        allMatrix=new ArrayList<>();
        while(ch!=16){
            if(ch==0){
                displayMatrices();
            }
            if(ch==1){
            
            Matrix x = takeInput();
            
            if(x!=null){
                allMatrix.add(x);
                x.UID=matrixCount+1;
                matrixCount++;
                System.out.println(x.myTypes);
            display(x);
            }
            
            }
            if(ch==2){
                
                Matrix  x= createMatrix();
                if(x!=null){
                    allMatrix.add(x);
                    x.UID=matrixCount+1;
                    matrixCount++;
                    System.out.println(x.myTypes);
                System.out.println("Matrix created");
                display(x);
                }
                else{
                    System.out.println("Spelling mistake?");
                }
                
            }
            if(ch==3){
                System.out.println("Choose matrix");
                displayMatrices();
                int choice =Integer.parseInt(br.readLine());
                Matrix toUpdate = allMatrix.get(choice-1);
                toUpdate.updateMatrix();
                
            }
            if(ch==4){
                displayMatrices();
                
                int choice =Integer.parseInt(br.readLine());
                Matrix chosen=allMatrix.get(choice-1);
                System.out.println(chosen.myTypes);
            }
            if(ch==5){
                displayMatrices();
                System.out.println("Choose 2 matrix by Unique ID");
                int c1 =Integer.parseInt(br.readLine());
                int c2 =Integer.parseInt(br.readLine());
                Matrix m1 = allMatrix.get(c1-1);
                Matrix m2 = allMatrix.get(c2-1);
                System.out.println("1-add, 2- subtract, 3-Multiply, 4-Divide");
                int c3 =Integer.parseInt(br.readLine());
                if(c3==1){
                    int addresult[][]=Matrix.add(m1,m2);
                    System.out.println("Addition result");
                    if(addresult!=null)
                    display(addresult);
                }
                if(c3==2){
                    int subresult[][]=Matrix.subtract(m1,m2);
                    System.out.println("Addition result");
                    if(subresult!=null)
                    display(subresult);
                }
                if(c3==3){
                    int mulresult[][]=Matrix.multiply(m1,m2);
                    System.out.println("Addition result");
                    if(mulresult!=null)
                    display(mulresult);
                }
                if(c3==4){
                    int divresult[][]=Matrix.divide(m1, m2);
                    System.out.println("Division result");
                    if(divresult!=null)
                    display(divresult);
                }
            }
            if(ch==7){
                displayMatrices();
                System.out.println("Choose a matrix to find transpose");
                int c1 =Integer.parseInt(br.readLine());
                Matrix m1 = allMatrix.get(c1-1);
                int trans[][]=m1.transpose();
                System.out.println("Transposed");
                display(trans);
            }
            if(ch==8){
                displayMatrices();
                System.out.println("Choose a matrix to find inverse");
                int c1 =Integer.parseInt(br.readLine());
                Matrix m1 = allMatrix.get(c1-1);
                int inv[][];
                inv = m1.inverse();
                if(inv!=null){
                    System.out.println("Inversed");
                    display(inv);
                }
                
            }
            if(ch==9){
                displayMatrices();
                System.out.println("Choose a matrix to find means");
                int c1 =Integer.parseInt(br.readLine());
                Matrix m1 = allMatrix.get(c1-1);
                Matrix.means(m1);
            }
            if(ch==10){
                displayMatrices();
                System.out.println("Choose a matrix to find determinant");
                int c1 =Integer.parseInt(br.readLine());
                Matrix m1 = allMatrix.get(c1-1);
                if(m1.isRectangular()){
                    System.out.println("Cant find determinant of rectangular matrix");
                }
                else if(m1.isIdentity()){
                    System.out.println("Determinant = 1");
                }
                else if(m1.isNull()){
                    System.out.println("Determinant = 0");
                }
                else if(m1.isScalar()){
                    int det=(int)Math.pow(m1.scalar_value, m1.getRow());
                    System.out.println("Determinant = "+ det);
                }
                else if(m1.isDiagonal()){
                    int det=1;
                    for(int i=0;i<m1.dia.length;i++){
                        det*=m1.getMatrix()[i][i];
                    }
                    System.out.println("Determinant = "+ det);
                }
                else{
                    System.out.println("Determinant = "+ m1.calculateDeterminant(m1.getMatrix()));
                }
            }
            if(ch==11){
                
                System.out.println("Doing this may change type of matrix. Do you allow using singleton matrices as a scalar value?");
                String xyz=br.readLine();
                if(xyz.equalsIgnoreCase("yes")){
                displayMatrices();
                System.out.println("Choose a singleton matrix to use as scalars");
                int c1 =Integer.parseInt(br.readLine());
                Matrix m1 = allMatrix.get(c1-1);
                Matrix m2;
                if(m1.isSingleton()){
                    displayMatrices();
                    System.out.println("Choose a matrix apply scaling");
                    int c2 =Integer.parseInt(br.readLine());
                    m2 = allMatrix.get(c2-1);
                    
                    
                    for(int i=0;i<m2.getRow();i++){
                        for(int j=0;j<m2.getCol();j++){
                            m2.getMatrix()[i][j]*=m1.getMatrix()[0][0];
                            m2.myTypes=Matrix.getTypes(m2);
                        }
                    }
                    System.out.println("Scaled successfully");
                
                }
                else{
                    System.out.println("chosen matrix not singleton");
                }
            }
        }
            if(ch==12){
                displayMatrices();
                System.out.println("Choose a matrix to Compute A + A' ");
                int c1 =Integer.parseInt(br.readLine());
                Matrix m1 = allMatrix.get(c1-1);
                int trans[][]=m1.transpose(m1.getMatrix());
                Matrix m2=new Matrix();
                m2.initialize(trans.length, trans[0].length, trans);
                int addresult[][]=Matrix.add(m1, m2);
                if(addresult!=null){
                    display(addresult);
                }
            }
            if(ch==15){
                System.out.println("enter label");
                String label=br.readLine();
                System.out.println("All matrices of given label/n");
                for(int i=0;i<allMatrix.size();i++){
                    for(int j=0;j<allMatrix.get(i).myTypes.size();j++){
                        if(allMatrix.get(i).myTypes.get(j).equalsIgnoreCase(label)){
                            System.out.println("[ ID = "+allMatrix.get(i).UID+" ]");
                            display(allMatrix.get(i).getMatrix());
                            
                        }
                    }
                }
            }
            System.out.println("1. Take matrices as input and label them with appropriate matrix-types.\n2. Create matrices of requested matrix-types and label them with appropriate matrix-types.\n3. Change the elements of a matrix as long as the fixed matrix-type labels remain valid.\n4. Display all the matrix-type labels of a requested matrix.\n\n5. Perform addition, subtraction, multiplication & division.\n6. Perform element-wise operations.[NOT DONE]\n7. Transpose matrices.\n8. Inverse matrices.\n9. Compute means: row-wise mean, column-wise mean, mean of all the elements.\n10. Compute determinants.\n11. Use singleton matrices as scalars, if requested.\n12. Compute A+AT for a matrix A.\n13. Compute Eigen vectors and values[NOT DONE].\n14. Solve sets of linear equations using matrices. [NOT DONE]\n15. Retrieve all the existing matrices (entered or created) having requested matrix-type labels.");
             System.out.println("Enter choice");
             ch = Integer.parseInt(br.readLine());
        }

    }
}