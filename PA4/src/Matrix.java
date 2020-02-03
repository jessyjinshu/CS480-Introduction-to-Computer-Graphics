public class Matrix{
	float[][] mat = new float[4][4];
	
	
	
	
	
	public static float[][] Zero(){
		float[][] res = new float[4][4];
		for (int i = 0; i < 4;i++){
			for (int j = 0; j < 4; j++){
				res[i][j] = 0;
			}
		}
		return res;
	}
	
	public static float[][] Ones(){
		float[][] res = Zero();
		for (int i = 0; i < 4;i++){
			res[i][i] = 1;
		}
		return res;
		
		
	}
	public static float[][] MatrixS(float sx, float sy, float sz){
		float[][] mat = Zero();
		mat[0][0] = sx;
		mat[1][1] = sy;
		mat[2][2] = sz;
		mat[3][3] = 1;
		return mat;
	}
	
	public static float[][] MatrixT(float tx, float ty, float tz){
		float[][] mat = Ones();
		mat[0][3] = tx;
		mat[1][3] = ty;
		mat[2][3] = tz;
		return mat;
		
	}
	
	public static float[][] MatrixRotateX(float theta){
		float[][] mat = Ones();
		mat[1][1] = (float)Math.cos(theta);
		mat[1][2] = (float)-Math.sin(theta);
		mat[2][1] = (float)Math.sin(theta);
		mat[2][2] = (float)Math.cos(theta);
		
		
		return mat;
	}
	
	public static float[][] MatrixRotateY(float theta){
		float[][] mat = Ones();
		mat[0][0] = (float)Math.cos(theta);
		mat[0][2] = (float)Math.sin(theta);
		mat[2][0] = (float)-Math.sin(theta);
		mat[2][2] = (float)Math.cos(theta);
		
		return mat;
	}
	
	public static float[][] MatrixRotateZ(float theta){
		float[][] mat = Ones();
		mat[0][0] = (float)Math.cos(theta);
		mat[0][1] = (float)-Math.sin(theta);
		mat[1][0] = (float)Math.sin(theta);
		mat[1][1] = (float)Math.cos(theta);
		
		return mat;
	}
	
	
	
	public static Vector3D MultMatrixVector(Vector3D v, float[][] mat){
		float x, y, z;
		
		float[][] point = new float[4][1];
		point[0][0] = v.x;
		point[1][0] = v.y;
		point[2][0] = v.z;
		point[3][0] = 1;
		
		x = mat[0][0] * point[0][0] + mat[0][1] * point[1][0] + mat[0][2] * point[2][0] + mat[0][3] * point[3][0];
		y = mat[1][0] * point[0][0] + mat[1][1] * point[1][0] + mat[1][2] * point[2][0] + mat[1][3] * point[3][0];
		z = mat[2][0] * point[0][0] + mat[2][1] * point[1][0] + mat[2][2] * point[2][0] + mat[2][3] * point[3][0];
		
		Vector3D res= new Vector3D(x,y,z);
		return res;
	}

	public static float[][] multiplyMatrices(float[][] firstMatrix, float[][] secondMatrix) {
        float[][] product = new float[4][4];
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return product;
    }
	
	public static void Translate(float x, float y, float z, Vector3D center,Mesh3D mesh){
		float[][] matTin = Matrix.MatrixT(-center.x, -center.y,-center.z);
		float[][] matTout = Matrix.MatrixT(center.x, center.y,center.z);
		
		float[][] matT = Matrix.MatrixT(x,y,z);
		
		matT =Matrix.multiplyMatrices(matTout,Matrix.multiplyMatrices(matT,matTin));
		mesh.transform(matT);
		
	}
	public static void Scaling(float x, float y, float z,Vector3D center, Mesh3D mesh){
		float[][] matTin = Matrix.MatrixT(-center.x, -center.y,-center.z);
		float[][] matTout = Matrix.MatrixT(center.x, center.y,center.z);
		
		float[][] matS = Matrix.MatrixS(x,y,z);
		
		matS =Matrix.multiplyMatrices(matTout,Matrix.multiplyMatrices(matS, matTin));
		mesh.transform(matS);
		
	}
	
	public static void RotateX(float theta, Vector3D center, Mesh3D mesh){
		float[][] matTin = Matrix.MatrixT(-center.x, -center.y,-center.z);
		float[][] matTout = Matrix.MatrixT(center.x, center.y,center.z);
		
		float[][] matS = Matrix.MatrixRotateX(theta);
		
		float[][] matR =Matrix.multiplyMatrices(matTout,Matrix.multiplyMatrices(matS, matTin));
		mesh.transform(matR);
		mesh.NormTransform(matS);
		
	}
	
	public static void RotateY(float theta, Vector3D center, Mesh3D mesh){
		float[][] matTin = Matrix.MatrixT(-center.x, -center.y,-center.z);
		float[][] matTout = Matrix.MatrixT(center.x, center.y,center.z);
		
		float[][] matS = Matrix.MatrixRotateY(theta);
		
		float[][] matR =Matrix.multiplyMatrices(matTout,Matrix.multiplyMatrices(matS, matTin));
		mesh.transform(matR);
		mesh.NormTransform(matS);
		
	}
	
	public static void RotateZ(float theta, Vector3D center, Mesh3D mesh){
		float[][] matTin = Matrix.MatrixT(-center.x, -center.y,-center.z);
		float[][] matTout = Matrix.MatrixT(center.x, center.y,center.z);
		
		float[][] matS = Matrix.MatrixRotateZ(theta);
		
		float[][] matR =Matrix.multiplyMatrices(matTout,Matrix.multiplyMatrices(matS, matTin));
		mesh.transform(matR);
		mesh.NormTransform(matS);
		
		
		
	}
	
	
	
	
}