public class NBody {

  public static double readRadius(String a) {
    In in = new In(a);
    int firstItemInFile = in.readInt();
    double secondItemInFile = in.readDouble();
    return secondItemInFile;
  }

  public static Body[] readBodies(String a) {
    In in = new In(a);
    int amount = in.readInt();
    double size = in.readDouble();
    Body[] Bodies = new Body[amount];
    int index = 0;
    double xx = 0;
    double yy = 0;
    double xvel = 0;
    double yvel = 0;
    double mass = 0;
    String name = "null";
    while (index < (amount)) {
      xx = in.readDouble();
      yy = in.readDouble();
      xvel = in.readDouble();
      yvel = in.readDouble();
      mass = in.readDouble();
      name = in.readString();
      Bodies[index] = new Body(xx, yy, xvel, yvel, mass, name);
      index += 1;
    }
    return Bodies;
  }

  public static void main(String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = (args[2]);
    double radius = readRadius(filename);
    Body[] Bodies = readBodies(filename);
    StdDraw.setScale(-radius, radius);
    StdDraw.picture(0, 0, "images/starfield.jpg");
    int index = 0;
    while (index < Bodies.length) {
      Bodies[index].draw();
      index += 1;
    }
    StdDraw.enableDoubleBuffering();
    int time = 0;
    double[] xForces = new double[Bodies.length];
    double[] yForces = new double[Bodies.length];
    while (time < T) {
      for (int i = 0; i < Bodies.length; i+=1) {
        xForces[i] = Bodies[i].calcNetForceExertedByX(Bodies);
        yForces[i] = Bodies[i].calcNetForceExertedByY(Bodies);
      }
      for (int indexer = 0; indexer < xForces.length; indexer += 1) {
        Bodies[indexer].update(dt, xForces[indexer], yForces[indexer]);
      }
    StdDraw.picture(0, 0, "images/starfield.jpg");
    int indexing = 0;
    while (indexing < Bodies.length) {
      Bodies[indexing].draw();
      indexing += 1;
    }
    StdDraw.show();
		StdDraw.pause(10);
    time += dt;
  }
  StdOut.printf("%d\n", Bodies.length);
  StdOut.printf("%.2e\n", radius);
  for (int i = 0; i < Bodies.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Bodies[i].xxPos, Bodies[i].yyPos, Bodies[i].xxVel,
                    Bodies[i].yyVel, Bodies[i].mass, Bodies[i].imgFileName);
}
}
}
