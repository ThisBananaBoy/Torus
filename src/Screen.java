import jdk.dynalink.linker.ConversionComparator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Screen extends JPanel {
    public Torus torus;
    public D3_DataPoint camera;
    public Projection projektion;
public Color color;

public D3_Vector lighting = new D3_Vector(0, 0, -1);

    public void setDrawableTorus(Torus torus){
        this.torus = torus;
    }
    public void setCamera(D3_DataPoint camera){
        this.camera = camera;
    }
    public void setProjectionMatrix(Projection p){
        this.projektion = p;
    }
    public void updateScreen(Map<String, Integer> sliderParams){
      this.torus = new Torus(new D3_DataPoint(0,0,0), sliderParams.get("Radius-Außenkreis"), sliderParams.get("Max-Segements-Außen"), sliderParams.get("Max-Segements-Innen"), sliderParams.get("Radius-Innenkreis"));
      torus.createCicles();
      torus.createTriangles();
      projektion.setRotationX(sliderParams.get("Winkel-x"));
      projektion.setRotationY(sliderParams.get("Winkel-y"));

      var x = sliderParams.get("Verschiebung-X");
      var y = sliderParams.get("Verschiebung-Y");
      var z = sliderParams.get("Verschiebung-Z");

      projektion.setTranslationVector(new D3_Vector(x, y, z));

      var r = sliderParams.get("R");
      var g = sliderParams.get("G");
      var b = sliderParams.get("B");
      this.color = new Color(r, g, b);
    }


    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            projektion.initializeProjecionMatrix();

            System.out.println("Printing");

            //DebugDrawing(g);
            drawColor(g);

            System.out.println("Done drawing");
        }

public void drawColor(Graphics g){
    var triangleArrayList = new ArrayList<Triangle>();
    System.out.println("New Round     ");
    var first = true;
    for (Triangle origninalTriangale : torus.triangles){
        var triangle = new Triangle(origninalTriangale.a, origninalTriangale.b, origninalTriangale.c);

        triangle.normalizeNormal();
        triangle.plane.normalVector = projektion.rotateX(triangle.plane.normalVector.ToDataPoint()).ToVector();
        triangle.plane.normalVector = projektion.rotateZ(triangle.plane.normalVector.ToDataPoint()).ToVector();

        triangle.a = projektion.rotateX(triangle.a);
        triangle.b = projektion.rotateX(triangle.b);
        triangle.c = projektion.rotateX(triangle.c);

        triangle.a = projektion.rotateZ(triangle.a);
        triangle.b = projektion.rotateZ(triangle.b);
        triangle.c = projektion.rotateZ(triangle.c);

        // midpoint berechenen
        // sortieren
        triangle.setMidPoint();
        triangleArrayList.add(triangle);
        if (first) {
            System.out.println("Values before translation");
            System.out.println(triangle.a.x);
            System.out.println(triangle.a.y);
            System.out.println(triangle.a.z);

            System.out.println(triangle.b.x);
            System.out.println(triangle.b.y);
            System.out.println(triangle.b.z);

            System.out.println(triangle.c.x);
            System.out.println(triangle.c.y);
            System.out.println(triangle.c.z);
            first = false;
        }

    }

    triangleArrayList.sort((x, y) -> Double.compare(y.midPoint.z, x.midPoint.z));
    first = true;
    for (Triangle triangle : triangleArrayList) {


        triangle.a = projektion.translate(triangle.a);
        triangle.b = projektion.translate(triangle.b);
        triangle.c = projektion.translate(triangle.c);

        var scalA = VCUtil.scalar(triangle.plane.normalVector.ToVector(), triangle.a.ToVector());


        if (scalA < 0) {
            continue;
        }

        triangle.a = projektion.project(triangle.a);
        triangle.b = projektion.project(triangle.b);
        triangle.c = projektion.project(triangle.c);

        triangle.a = projektion.scaleToView(triangle.a);
        triangle.b = projektion.scaleToView(triangle.b);
        triangle.c = projektion.scaleToView(triangle.c);

//                g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
//                g.drawLine((int) b.x, (int) b.y, (int) c.x, (int) c.y);
//                g.drawLine((int) c.x, (int) c.y, (int) a.x, (int) a.y);
        if(first) {

            System.out.println("Values before projection");
            System.out.println(triangle.a.x);
            System.out.println(triangle.a.y);
            System.out.println(triangle.a.z);

            System.out.println(triangle.b.x);
            System.out.println(triangle.b.y);
            System.out.println(triangle.b.z);

            System.out.println(triangle.c.x);
            System.out.println(triangle.c.y);
            System.out.println(triangle.c.z);
            first = false;
        }

        var nNormalVec = VCUtil.normalize(triangle.plane.normalVector.ToVector());
        var nLighting = VCUtil.normalize(lighting);

        // werte zwischen -1 und 1, aber eigentlich nicht > 0, weil dann falsche richtung.
        var lightingFactor = Math.abs(VCUtil.scalar(nNormalVec, nLighting));

        //System.out.println("light" + lightingFactor);

        g.setColor(new Color( (int) (color.getRed() * lightingFactor), (int) (color.getGreen() * lightingFactor), (int)(color.getBlue() * lightingFactor )));

        int[] xPoints = {(int) triangle.a.x, (int) triangle.b.x, (int) triangle.c.x};
        int[] yPoints = {(int) triangle.a.y, (int) triangle.b.y, (int) triangle.c.y};
        g.fillPolygon(xPoints, yPoints, 3);
    }

}
        public void DebugDrawing(Graphics g){
            for (Triangle triangle : torus.triangles){
                System.out.println("normalVector " + triangle.plane.normalVector.x + " " + triangle.plane.normalVector.y + " " + triangle.plane.normalVector.z );

                triangle.normalizeNormal();


                var n_mid = VCUtil.add(triangle.midPoint, triangle.plane.normalVector);


                var a = projektion.rotateX(triangle.a);
                var b = projektion.rotateX(triangle.b);
                var c = projektion.rotateX(triangle.c);

                var mid = projektion.rotateX(triangle.midPoint);
                n_mid = projektion.rotateX(n_mid.ToDataPoint());

                a = projektion.rotateZ(a);
                b = projektion.rotateZ(b);
                c = projektion.rotateZ(c);

                mid = projektion.rotateZ(mid);
                n_mid = projektion.rotateZ(n_mid.ToDataPoint());

                var translationVector = new D3_Vector(0, 0, 200);

                a = projektion.translate(a);
                b = projektion.translate(b);
                c = projektion.translate(c);

                mid = projektion.translate(mid);
                n_mid = projektion.translate(n_mid.ToDataPoint());


                a = projektion.project(a);
                b = projektion.project(b);
                c = projektion.project(c);

                mid = projektion.project(mid);
                n_mid = projektion.project(n_mid.ToDataPoint());


                a = projektion.scaleToView(a);
                b = projektion.scaleToView(b);
                c = projektion.scaleToView(c);

                mid = projektion.scaleToView(mid);
                n_mid = projektion.scaleToView(n_mid.ToDataPoint());

//                System.out.println("midPoitn x: " + mid.x + "mid_n Point x: " + n_mid.x);
//                System.out.println("midPoitn y: " + mid.y + "mid_n Point y: " + n_mid.y);

               g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
               g.drawLine((int) b.x, (int) b.y, (int) c.x, (int) c.y);
               g.drawLine((int) c.x, (int) c.y, (int) a.x, (int) a.y);

               g.drawLine((int) mid.x, (int) mid.y, (int) n_mid.x, (int) n_mid.y);
            }

        }
    }
