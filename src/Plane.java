public class Plane {
    D3_Vector v;
    D3_Vector u;
    D3_Vector normalVector;

    public Plane(D3_Vector v, D3_Vector u, D3_Vector normalVector) {
        this.normalVector = normalVector;
        this.v = v;
        this.u = u;
    }
}