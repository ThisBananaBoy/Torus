public class Projection {
public double zNear;
public double zFar;
public double fov;
public double aspectRatio;
public double fovRad;
public double height;
public double width;

public double[][] mat4x4 = new double[4][4];
public double[][] yRot = new double[4][4];
public double[][] xRot = new double[4][4];

public D3_Vector translationVector;

public Projection(double zNear, double zFar, double fov, int screenWidth, int screenHeight) {
    this.zNear = zNear;
    this.zFar = zFar;
    this.fov = fov;
    this.aspectRatio = (double)screenHeight/screenWidth;
    this.fovRad = 1 / Math.tan(Math.toRadians(fov) * 0.5);

    this.height = screenHeight;
    this.width = screenWidth;
}
public void initializeProjecionMatrix() {
    mat4x4[0][0] = aspectRatio * fovRad;
    mat4x4[1][1] = fovRad;
    mat4x4[2][2] = zFar / (zFar - zNear);
    mat4x4[3][2] = -zFar * zNear / (zFar - zNear);
    mat4x4[2][3] = 1;


}

public void setRotationY(double angel) {
    var rad = Math.toRadians(angel);
    yRot[0][0] = Math.cos(rad);
    yRot[0][2] = Math.sin(rad);
    yRot[1][1] = 1;
    yRot[2][0] = -Math.sin(rad);
    yRot[2][2] = Math.cos(rad);
    yRot[3][3] = 1;
}

public void setRotationX(double angel){
    var rad = Math.toRadians(angel);
    xRot[0][0] = 1;
    xRot[1][1] = Math.cos(rad);
    xRot[1][2] = Math.sin(rad);
    xRot[2][1] = -Math.sin(rad);
    xRot[2][2] = Math.cos(rad);
    xRot[3][3] = 1;
}

public void setTranslationVector(D3_Vector translationVector){
    this.translationVector = translationVector;
}
public D3_DataPoint translate(D3_DataPoint d) {
    return VCUtil.add(d, translationVector).ToDataPoint();
}

public D3_DataPoint project(D3_DataPoint d){
    return VCUtil.matrixMulitplication( d, mat4x4).ToDataPoint();
}

public D3_DataPoint scaleToView(D3_DataPoint d){

    return new D3_DataPoint((d.x + 1) * width * 0.5, (d.y + 1) * height * 0.5, d.z);
}
public D3_DataPoint rotateZ(D3_DataPoint d){
    return VCUtil.matrixMulitplication(d, yRot).ToDataPoint();
}

public D3_DataPoint rotateX(D3_DataPoint d){
    return VCUtil.matrixMulitplication(d, xRot).ToDataPoint();
}

}
