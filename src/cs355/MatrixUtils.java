package cs355;

import java.util.ArrayList;
import java.util.List;

/**
 * User: matt
 * Date: 11/12/13
 * Time: 3:59 PM
 */
public class MatrixUtils {

	public Matrix4D multiply4x4Matrices(Matrix4D m1, Matrix4D m2) {
		Matrix4D resultMatrix = new Matrix4D(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					double currCellVal = resultMatrix.getRow(i).get(j);
					resultMatrix.getRow(i).set(j, currCellVal + m1.getRow(i).get(k) * m2.getRow(k).get(j));
				}
			}
		}
		return resultMatrix;
	}

	public ArrayList<Double> multiplyRowWithMatrix(Matrix4D matrix, double x, double y, double z) {

		List<Double> r0 = matrix.getRow(0);
		List<Double> r1 = matrix.getRow(1);
		List<Double> r2 = matrix.getRow(2);
		List<Double> r3 = matrix.getRow(3);

		double resultX = (r0.get(0) * x + r0.get(1) * y + r0.get(2) * z + r0.get(3) * 1);
		double resultY = (r1.get(0) * x + r1.get(1) * y + r1.get(2) * z + r1.get(3) * 1);
		double resultZ = (r2.get(0) * x + r2.get(1) * y + r2.get(2) * z + r2.get(3) * 1);
		double resultW = (r3.get(0) * x + r3.get(1) * y + r3.get(2) * z + r3.get(3) * 1);

		ArrayList<Double> result = new ArrayList<>();
		result.add(resultX);
		result.add(resultY);
		result.add(resultZ);
		result.add(resultW);
		return result;
	}

	public Matrix4D createRotationMatrix(float angle) {
		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		return new Matrix4D(cosAngle, 0, sinAngle, 0, 0, 1, 0, 0, -sinAngle, 0, cosAngle, 0, 0, 0, 0, 1);
	}

	public Matrix4D createTranslationMatrix(Point3D cameraPosition) {
		return new Matrix4D(1, 0, 0, -cameraPosition.x,
							0, 1, 0, -cameraPosition.y,
							0, 0, 1, -cameraPosition.z,
							0, 0, 0, 1);
	}

	public Matrix4D createClipMatrix(double xZoom, double yZoom, double near, double far) {
		return new Matrix4D(xZoom, 0, 0, 0,
							0, yZoom, 0,0,
							0, 0, (far+near)/(far-near), -(2*near*far)/(far-near),
							0, 0, 1, 0);
	}
}
