class Polynomial
{
    public double[] coefficients;
    public Polynomial()
    {
        coefficients = new double[1];
        coefficients[0] = 0;
    }


    public Polynomial(double[] new_coeff)
    {
        coefficients = new double[new_coeff.length]; 
        for(int i = 0; i < new_coeff.length; i++)
        {
            coefficients[i] = new_coeff[i];
        }
    }


    public Polynomial add(Polynomial poly)
    {
        if(poly.coefficients.length > coefficients.length)
        {
            Polynomial new_poly = new Polynomial(poly.coefficients);
            for(int i = 0; i < coefficients.length; i++)
            {
                new_poly.coefficients[i] += coefficients[i];
            }
            return new_poly;
        }

        Polynomial new_poly = new Polynomial(coefficients);
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