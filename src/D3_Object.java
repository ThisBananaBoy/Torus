public abstract class D3_Object {
    public double x;
    public double y;
    public double z;

    public D3_Object(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public D3_Vector ToVector() {
        return new D3_Vector(x, y, z);
    }

    public D3_DataPoint ToDataPoint(){
        return new D3_DataPoint(x, y, z);
    }
}
