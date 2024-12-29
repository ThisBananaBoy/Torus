import java.util.ArrayList;

public class Torus {
    public ZeroPlane plane;
    public ArrayList<Circle> segments = new ArrayList<Circle>();
    public ArrayList<Triangle> triangles = new ArrayList<Triangle>();
    public D3_DataPoint midPoint;
    public double radius;
    public double radiusCircle;
    public int maxSegments;
    public int maxSegmentsCircle;

    public Torus(D3_DataPoint midPoint, double radius, int maxSegments, int maxSegmentsCircle, double radiusCircle){
        this.midPoint = midPoint;
        this.plane = new ZeroPlane();
        this.radius = radius;
        this.maxSegments = maxSegments;
        this.maxSegmentsCircle = maxSegmentsCircle;
        this.radiusCircle = radiusCircle;
    }

    public void createCicles(){
        D3_NVector nU = VCUtil.normalize(plane.u);
        D3_NVector nV = VCUtil.normalize(plane.v);

        for (var i = 0; i < 360; i = i + 360/maxSegments){
            var sinVec = VCUtil.multiply(nU, Math.sin(Math.toRadians(i)));
            var cosVec = VCUtil.multiply(nV, Math.cos(Math.toRadians(i)));
            D3_Vector accumulatedVector = VCUtil.add(sinVec, cosVec).ToVector();
            var dataPoint_Circle = VCUtil.multiply(accumulatedVector, radius);


            D3_DataPoint scaledDataPoint = VCUtil.add(dataPoint_Circle, midPoint).ToDataPoint();
            D3_Vector u = VCUtil.subtract(midPoint, dataPoint_Circle).ToVector();
            D3_Vector v = plane.normalVector;
            var normalVector = VCUtil.crossProduct(accumulatedVector, plane.normalVector);


            Circle circle = new Circle(scaledDataPoint, normalVector, v, u, radiusCircle, maxSegmentsCircle);
            circle.calcDataPoints();
            segments.add(circle);
        }
    }

    public void createTriangles(){
        for (var i = 1; i <= segments.size(); i++){
            var modi =  i % segments.size();
            var modiMinusOne = (i -1) % segments.size();

            var ci1 = segments.get(modiMinusOne);
            var ci2 = segments.get(modi);

            for ( var n = 1; n <= ci1.dataPoints.size(); n++) {
                var modn =  n % ci1.dataPoints.size();
                var modNMinusOne = (n - 1) %  ci1.dataPoints.size();

                var a1 = ci1.dataPoints.get(modNMinusOne);
                var b1 = ci2.dataPoints.get(modn);
                var c1 = ci1.dataPoints.get(modn);

                var a2 = ci2.dataPoints.get(modNMinusOne);
                var b2 = ci2.dataPoints.get(modn);
                var c2 = ci1.dataPoints.get(modNMinusOne);

                if (a1.x == b1.x && a1.y == b1.y && a1.z == b1.z){
                    System.out.println("i : " + i + "n :" + n);
                }

                triangles.add(new Triangle(a1, b1, c1));
                triangles.add(new Triangle(a2, b2, c2));
            }
        }

    }
}
