public class Triangle {
    D3_DataPoint a;
    D3_DataPoint b;
    D3_DataPoint c;
    Plane plane;
    D3_DataPoint midPoint;

    public Triangle(D3_DataPoint a, D3_DataPoint b, D3_DataPoint c){
        this.a = a;
        this.b = b;
        this.c = c;

        var ab = VCUtil.subtract(a, b);
        var ac = VCUtil.subtract(a, c);
        var normalVector = VCUtil.crossProduct(ab, ac);
        this.plane = new Plane(ab, ac, normalVector);
        this.midPoint = new D3_DataPoint(
                (a.x + b.x + c.x) / 3,
                (a.y + b.y + c.y) / 3,
                (a.z + b.z + c.z) / 3
        );

    }

    public void normalizeNormal(){
        this.plane.normalVector = VCUtil.multiply(VCUtil.normalize(plane.normalVector), 10);
    }

    public void setMidPoint() {
       this.midPoint = new D3_DataPoint(
                (a.x + b.x + c.x) / 3,
                (a.y + b.y + c.y) / 3,
                (a.z + b.z + c.z) / 3
        );
    }

}
