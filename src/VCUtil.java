
public class VCUtil {
    public static D3_Object add(D3_Object firstD3Vector, D3_Object secondD3Vector){
        return new D3_Vector(
                firstD3Vector.x + secondD3Vector.x,
                firstD3Vector.y + secondD3Vector.y,
                firstD3Vector.z + secondD3Vector.z
        );
    }

    public static D3_Vector subtract(D3_DataPoint firstD3Vector, D3_Object secondD3Vector){
        return new D3_Vector(
                firstD3Vector.x - secondD3Vector.x,
                firstD3Vector.y - secondD3Vector.y,
                firstD3Vector.z - secondD3Vector.z
        );
    }

    public static D3_NVector normalize(D3_Vector firstD3Vector){
        double length = Math.sqrt(Math.pow(firstD3Vector.x, 2) + Math.pow(firstD3Vector.y, 2) + Math.pow(firstD3Vector.z, 2));
        return new D3_NVector(firstD3Vector.x / length, firstD3Vector.y / length, firstD3Vector.z / length);
    }

    public static double scalar(D3_Vector firstD3Vector, D3_Vector secondD3Vector) {
       return  (firstD3Vector.x * secondD3Vector.x) + (firstD3Vector.y * secondD3Vector.y) + (firstD3Vector.z * secondD3Vector.z);
    }

    public static D3_Vector multiply(D3_Object firstD3Vector, double parameter){
        return new D3_Vector(firstD3Vector.x * parameter, firstD3Vector.y * parameter, firstD3Vector.z * parameter);
    }


    public static D3_Vector crossProduct(D3_Vector firstD3Vector, D3_Vector secondD3Vector){
        return new D3_Vector(
                (firstD3Vector.y * secondD3Vector.z) - (firstD3Vector.z * secondD3Vector.y),
                (firstD3Vector.z * secondD3Vector.x) - (firstD3Vector.x * secondD3Vector.z),
                (firstD3Vector.x * secondD3Vector.y) - (firstD3Vector.y * secondD3Vector.x)
        );
    }


    public static D3_Vector matrixMulitplication(D3_DataPoint v, double[][] m){

        var x = v.x * m[0][0] + v.y * m[1][0] + v.z * m[2][0] + m[3][0];
        var y = v.x * m[0][1] + v.y * m[1][1] + v.z * m[2][1] + m[3][1];
        var z = v.x * m[0][2] + v.y * m[1][2] + v.z * m[2][2] + m[3][2];
        var f = v.x * m[0][3] + v.y * m[1][3] + v.z * m[2][3] + m[3][3];

        if (f != 0) {
            return new D3_Vector(
                    x/f,
                    y/f,
                    z/f
            );
        }

        return new D3_Vector(
                x,
                y,
                z
        );
    }
}
