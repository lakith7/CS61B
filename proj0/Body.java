public class Body {
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  static final double G = 6.67e-11;

  public Body(double xP, double yP, double xV, double yV,
              double m, String img) {
                xxPos = xP;
                yyPos = yP;
                xxVel = xV;
                yyVel = yV;
                mass = m;
                imgFileName = img;
              }

  public Body(Body b) {
    this.xxPos = b.xxPos;
    this.yyPos = b.yyPos;
    this.xxVel = b.xxVel;
    this.yyVel = b.yyVel;
    this.mass = b.mass;
    this.imgFileName = b.imgFileName;

  }

  public double calcDistance(Body c) {
    double dx = this.xxPos - c.xxPos;
    double dy= this.yyPos - c.yyPos;
    double distance = Math.sqrt((dx * dx) + (dy * dy));
    return distance;
  }

  public double calcForceExertedBy(Body d) {
    double distance = this.calcDistance(d);
    double Force = (G * this.mass * d.mass)/(distance * distance);
    return Force;
  }

  public double calcForceExertedByX(Body e) {
    double dx = e.xxPos - this.xxPos;
    double Forcex = (this.calcForceExertedBy(e) * dx)/this.calcDistance(e);
    return Forcex;
  }

  public double calcForceExertedByY(Body e) {
    double dy = e.yyPos - this.yyPos;
    double Forcey = (this.calcForceExertedBy(e) * dy)/this.calcDistance(e);
    return Forcey;
  }

  public double calcNetForceExertedByX(Body[] Bodies) {
    double NetForceX = 0;
    for (int index = 0; index<Bodies.length; index += 1) {
      if (!this.equals(Bodies[index])) {
        NetForceX += this.calcForceExertedByX(Bodies[index]);
      }
    }
    return NetForceX;
  }

  public double calcNetForceExertedByY(Body[] Bodies) {
    double NetForceY = 0;
    for (int index = 0; index<Bodies.length; index += 1) {
      if (!this.equals(Bodies[index])) {
        NetForceY += this.calcForceExertedByY(Bodies[index]);
      }
    }
    return NetForceY;
  }

  public void update(double dt, double fX, double fY) {
    double accx = fX/this.mass;
    double accy = fY/this.mass;
    double velocityx = xxVel + (dt * accx);
    double velocityy = yyVel + (dt * accy);
    this.xxPos = this.xxPos + (dt * velocityx);
    this.yyPos = this.yyPos + (dt * velocityy);
    this.xxVel = velocityx;
    this.yyVel = velocityy;
  }
  public void draw() {
    StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
  }
}
