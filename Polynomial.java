class Polynomial
{
    double[] coefficients = new double[100];
    public Polynomial()
    {
        coefficients[0] = 0;
    }


    public Polynomial(double[] new_coeff)
    {
        for(int i = 0; i < new_coeff.length; i++)
        {
            coefficients[i] = new_coeff[i];
        }
    }


    public Polynomial add(Polynomial poly)
    {
        Polynomial new_poly = new Polynomial();

        for(int i = 0; i < poly.coefficients.length; i++)
        {
            new_poly.coefficients[i] += poly.coefficients[i];
        }

        for(int i = 0; i < poly.coefficients.length; i++)
        {
            new_poly.coefficients[i] += coefficients[i];
        }   
    
        return new_poly;
    }


    public double evaluate(double x)
    {
        if(coefficients.length == 1)
        {
            return coefficients[0];
        }

        double sum = coefficients[0];

        for(int i = 1; i < coefficients.length; i++)
        {
            sum += coefficients[i] * Math.pow(x,i);
        }
        return sum;
    }


    public boolean hasRoot(int x)
    {
        if(evaluate(x) == 0)
        {
            return true;
        }
        return false;
    }

}