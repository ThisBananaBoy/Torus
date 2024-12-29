import java.util.ArrayList;

public class Circle {

    public Plane plane;
    public ArrayList<D3_DataPoint> dataPoints = new ArrayList<D3_DataPoint>();
    public D3_DataPoint midPoint;
    public double radius;
    public int maxSegments;


    public Circle(D3_DataPoint midPoint, D3_Vector normalVector, D3_Vector v, D3_Vector u, double radius, int maxSegments){
            this.midPoint = midPoint;
            this.plane = new Plane(v, u, normalVector);
            this.radius = radius;
            this.maxSegments = maxSegments;
    }

    public void calcDataPoints(){
        D3_NVector nU = VCUtil.normalize(plane.u);
        D3_NVector nV = VCUtil.normalize(plane.v);

        for (var i = 0; i < 360; i = i + 360/maxSegments){
            var sinVec = VCUtil.multiply(nU, Math.sin(Math.toRadians(i)));
            var cosVec = VCUtil.multiply(nV, Math.cos(Math.toRadians(i)));
            var accumulatedVector = VCUtil.add(sinVec, cosVec);
            var dataPoint_Circle = VCUtil.multiply(accumulatedVector, radius);
            var scaledDataPoint = VCUtil.add(dataPoint_Circle, midPoint);
            dataPoints.add(scaledDataPoint.ToDataPoint());
        }
    }
}
