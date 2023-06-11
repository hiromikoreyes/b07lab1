import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Polynomial
{
    public double[] coefficients;
    public int[] exponents;
    

    public Polynomial()
    {
        coefficients = null;
        exponents = null;
    }


    public Polynomial(double[] new_coeff, int[] new_exp)
    {
        coefficients = new double[new_coeff.length]; 
        for(int i = 0; i < new_coeff.length; i++)
        {
            coefficients[i] = new_coeff[i];
        }

        exponents = new int[new_exp.length];
        for(int i = 0; i < new_exp.length; i++)
        {
            exponents[i] = new_exp[i];
        }
    }


    private void processSegment(String seg)
    {
        //helper to Polynomial(File file)
        double[] new_coeff = new double[1];
        int[] new_exp = new int[1];

        if(seg.indexOf('x') == -1)
        {
            new_coeff[0] = Double.valueOf(seg);
            new_exp[0] = 0;

        } else
        {
            String[] split_arr;
            split_arr = seg.split("x");
            new_coeff[0] = Double.valueOf(split_arr[0]);
            new_exp[0] = Integer.valueOf(split_arr[1]);;
            
        }

        Polynomial poly_1 = new Polynomial(new_coeff, new_exp);
        Polynomial poly_2 = add(poly_1);

        coefficients = poly_2.coefficients;
        exponents = poly_2.exponents;
        removeRedundant();
    }


    public Polynomial(File file)
    {
        // init coeff, exp
        coefficients = new double[1];
        exponents = new int[1];

        // This code assumes that 3x^{1}+2 (latex) is represented as 3x1+2 in the file NOT 3x+2
        String poly_string;

        try {

            Scanner sc = new Scanner(file);
            poly_string = sc.nextLine();
            sc.close();
        } catch (FileNotFoundException e) {

            System.out.println("your file couldn't be found..");
            return;
        }

        if(poly_string.isEmpty())
        {
            coefficients = null;
            exponents = null;
            return;
        }

        int seg_start = 0;
        int i = 0;

        for(i = 0; i < poly_string.length(); i++)
        {


            if(poly_string.charAt(i) == '-' || poly_string.charAt(i) == '+')
            {
                String seg = poly_string.substring(seg_start, i);
                processSegment(seg);
                seg_start = i;
            }

        }
    
        String seg = poly_string.substring(seg_start, i);
        processSegment(seg);
    
    }

    
    private int searchIntArray(int search, int arr[], int start, int end)
    {
        //Returns the index of the array for the wanted item
        for(int i = start; i < end; i++)
        {
            if(arr[i] == search) return i;
        }
        return -1;
    }


    public Polynomial add(Polynomial poly)
    {
        if(coefficients == null || exponents == null)
        {
            return poly;
        }
        if(poly.coefficients == null || poly.exponents == null)
        {
            return new Polynomial(coefficients, exponents);
        }

        double[] new_coeff = new double[coefficients.length + poly.coefficients.length];
        int[] new_exp = new int[exponents.length + poly.exponents.length];

        //merge the two arrays together, exponents, coefficients
        int index = 0;

        for (int i = 0; i < coefficients.length; i++)
        {
            new_coeff[index] = coefficients[i];
            new_exp[index] = exponents[i];
            index++;
        }

        for (int i = 0; i < poly.coefficients.length; i++)
        {
            new_coeff[index] = poly.coefficients[i];
            new_exp[index] = poly.exponents[i];
            index++;
        }


        Polynomial new_poly = new Polynomial(new_coeff, new_exp);
        return new_poly;
    }

    private void removeRedundant()
    {
        //Removes values with coefficient 0.. only if the polynomial isn't the empty polynomial


        int[] stored_exp = new int[exponents.length];
        double[] stored_coeff = new double[coefficients.length];

        int index = 0;
        for(int i = 0; i < coefficients.length; i++)
        {
            if(coefficients[i] == 0) continue;
            stored_coeff[index] = coefficients[i];
            stored_exp[index] = exponents[i];
            index++;
        }

        
        //Copy stored arrays into fresh arrays with proper length
        int[] new_exp = new int[index];
        double[] new_coeff = new double[index];
        for(int i = 0; i < index; i++)
        {
            new_exp[i] = stored_exp[i];
            new_coeff[i] = stored_coeff[i];
        }

        //Set this.exponents, this.coefficients to new_exp, new_coeff
        exponents = new_exp;
        coefficients = new_coeff;

    }

    private void collectLikeTerms()
    {

        int[] stored_exp = new int[exponents.length];
        double[] stored_coeff = new double [coefficients.length];
        
        //-1 initialize the stored_exp arr
        for(int i = 0; i < exponents.length; i++)  stored_exp[i] = -1;


        int index = 0;
        for(int i = 0; i < exponents.length ; i++)
        {   
            int j = searchIntArray(exponents[i], stored_exp, 0, exponents.length - 1);
            if( j != - 1)
            {
                stored_coeff[j] += coefficients[i];
            } 
            else
            {
                stored_exp[index] = exponents[i];
                stored_coeff[index] += coefficients[i];
                index++;
            }

        }

        //Copy stored arrays into fresh arrays with proper length
        int[] new_exp = new int[index];
        double[] new_coeff = new double[index];
        for(int i = 0; i < index; i++)
        {
            new_exp[i] = stored_exp[i];
            new_coeff[i] = stored_coeff[i];
        }

        //Set this.exponents, this.coefficients to new_exp, new_coeff
        exponents = new_exp;
        coefficients = new_coeff;

        removeRedundant();
    }


    public double evaluate(double x)
    {
        if(coefficients == null || exponents == null) return 0;

        collectLikeTerms();
        double total = 0;
        for(int i = 0; i < exponents.length; i++)
        {
            total = total + (coefficients[i] * Math.pow(x,exponents[i]));
        }
        return total;
    }


    public boolean hasRoot(int x)
    {

        if(evaluate(x) == 0)
        {
            return true;
        }
        return false;
    }


    public Polynomial multiply(Polynomial poly)
    {
        if(coefficients == null || exponents == null)
        {
            return new Polynomial();
        }
        if(poly.coefficients == null || poly.exponents == null)
        {
            return new Polynomial();
        }

        double[] new_coeff = new double[poly.coefficients.length * coefficients.length];
        int[] new_exp = new int[poly.exponents.length * exponents.length];

        int index = 0;

        for(int i = 0; i < exponents.length; i++)
        {
            for(int j = 0; j < poly.exponents.length; j++)
            {

                new_exp[index] = exponents[i] + poly.exponents[j];
                new_coeff[index] = coefficients[i] * poly.coefficients[j];
                index++;
            }

        }


        Polynomial new_poly = new Polynomial(new_coeff, new_exp);
        new_poly.collectLikeTerms();
        return new_poly;
    }

    public void saveToFile(String filename)
    {
        try {
            FileWriter fw = new FileWriter(filename);
            String output_poly = "";
            for(int i = 0; i < exponents.length; i++)
            {

                if(i!=0 && coefficients[i] > 0) output_poly += '+';
                output_poly += coefficients[i];

                if(exponents[i] != 0)
                {
                output_poly += 'x';
                output_poly += exponents[i];
                }
            }   

            fw.write(output_poly);
            fw.close();



          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

    }

}