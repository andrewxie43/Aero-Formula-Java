public class layer {

//initialize vars
public double initAlt;
public double endAlt;
public double gradient;
public double initDense;



//constants

public static double gravity = -9.8;
public static double seaLevelDensity = 1.225;
public static double seaLevelPressure = 101325;
public static double seaLevelTemp = 288.15;
public static double gasConstant = 287;

//vars in methods
public double startTemp = 0;
public double temp = 0;
public double startAlt = 0;


//constructors
  public layer(double ia, double ea){
    initAlt = ia;
    endAlt = ea;
  }
  public layer(double ia, double ea, double g){
    initAlt = ia;
    endAlt = ea;
    gradient = g;
  }

 // add function to do the recursive layer var init.
  public void setVars(double alti){
    if (alti >= 0 && alti < 11000){ //if troposphere
      startTemp = 288.15;
      gradient = -0.0065;
      startAlt = 0;
    }
    else if (alti >= 11000 && alti < 20000){
      layer troposphere = new layer(0,11000,-0.0065);
      startTemp = troposphere.findTempAlt(11000);
      startAlt = 11000;
    }
    else if(alti >= 20000 && alti < 32000){
      layer tropopause = new layer(11000,20000,0);
      startTemp = tropopause.findTempAlt(200000);
      startAlt = 20000;
    }
    else if(alti >= 32000 && alti < 47000){
      layer lowStratosphere = new layer(20000,32000,0.001);
      startTemp = lowStratosphere.findTempAlt(32000);
      startAlt = 32000;
    }
    else if(alti >= 47000 && alti < 51000){
      layer highStratosphere = new layer(32000,47000,0.0028);
      startTemp = highStratosphere.findTempAlt(47000);
      startAlt = 47000;
    }
    else if(alti >= 51000){ //CURRENTLY ONLY GOES TO STRATOPAUSE
      layer stratopause = new layer(47000,51000,0);
      startTemp = stratopause.findTempAlt(47000);
      startAlt = 51000;
    }
  }

  public double findTempAlt(double alti)
  {
    setVars(alti);
    temp = startTemp + (gradient * (alti - initAlt));
    return temp;
  }

  public double findPressureAlt(double alti)
  {
    setVars(alti);

    if (gradient != 0)
    {

      double gar = ((gravity) / (gradient * gasConstant) );//If testing works for 9144, change to use recursion to calculate pressures up to other levels.
      double x = (temp / seaLevelTemp);
      return seaLevelPressure * Math.pow(x, gar);
    }
    else
    {
      double grt = (-gravity/(gasConstant * temp)) * (alti - startAlt);
      return Math.pow(Math.E, grt);
    }



  }

}