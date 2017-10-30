package math;

public class Mat4 {
    public float[] mat;

    public Mat4() {
        mat = new float[16];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                mat[x + 4 * y] = 0.0f;
            }
        }
    }

    public Mat4(float diagonal) {
        mat = new float[16];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (x == y)
                    mat[x + 4 * y] = diagonal;
                else
                    mat[x + 4 * y] = 0.0f;
            }
        }
    }

    public Mat4 mul(Mat4 m) {
        Mat4 newMat = new Mat4();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0.0f;
                for (int i = 0; i < 4; i++) {
                    sum += mat[x + i * 4] * m.mat[i + y * 4];
                }
                newMat.mat[x + y * 4] = sum;
            }
        }
        return newMat;
    }

    public void setElement(int x, int y, float val) {
        if (x >= 0 && x < 4 && y >= 0 && y < 4) {
            mat[x + 4 * y] = val;
        }
    }

    public float getElement(int x, int y) {
        if (x >= 0 && x < 4 && y >= 0 && y < 4) {
            return mat[x + 4 * y];
        } else return 0.0f;
    }

    public static Mat4 identity() {
        return new Mat4(1);
    }

    public static Mat4 orthographic(float l, float r, float b, float t, float n, float f) {
        Mat4 newMat = new Mat4(1.0f);
        newMat.set(new float[]{
                2 / (r - l), 0, 0, -(r + l) / (r - l),
                0, 2 / (t - b), 0, -(t + b) / (t - b),
                0, 0, -2 / (f - n), -(f + n) / (f - n),
                0, 0, 0, 1
        });
        return newMat;
    }

    public static Mat4 perspective(float fov, float ar, float n, float f) {
        Mat4 newMat = new Mat4();
        float t = (float) Math.tan(Math.toRadians(fov/2)) * n;
        float b = -t;
        float r = t * ar;
        float l = -t * ar;
        newMat.set(new float[]{
                2 * n / (r - l), 0, 0, 0,
                0, 2 * n / (t - b), 0, 0,
                (r + l) / (r - l), (t + b) / (t - b), -(f + n) / (f - n), -1,
                0, 0, -2 * f * n / (f - n), 0
        });
        return newMat;
    }


    public void set(float[] mat) {
        this.mat = mat;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                sb.append(getElement(x, y));
                if (x < 3) {
                    sb.append(", ");
                } else
                    sb.append("]\n");
            }
            if (y < 3) {
                sb.append("[");
            } else sb.append("\n");
        }
        return sb.toString();
    }
}