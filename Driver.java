import java.io.File;
import java.util.Arrays;
public class Driver {

    public static void print_poly(Polynomial poly)
    {
        System.out.println(Arrays.toString(poly.coefficients));
        System.out.println(Arrays.toString(poly.exponents));

    }


    public static void main(String [] args) {

        
        Polynomial none = new Polynomial();

        double[] coeff_1 = new double[1];
        coeff_1[0] = 1;

        double[] coeff_neg1 = new double[1];
        coeff_neg1[0] = -1;

        int[] expon_1 = new int[1];
        expon_1[0] = 1;

        int[] expon_2 = new int[1];
        expon_2[0] = 2;

        Polynomial x = new Polynomial(coeff_1, expon_1);
        Polynomial xpower2 = new Polynomial(coeff_1, expon_2);
        Polynomial neg_xpower2 = new Polynomial(coeff_neg1, expon_2);


        System.out.println("***********************************************");
        System.out.println("ADDITION TESTS.....");
        System.out.println("***********************************************");

        //0 + x
        System.out.println("testing: x+0");
        print_poly(none.add(x));

        //x + x^2
        System.out.println("testing: x^2 + x");
        print_poly(x.add(xpower2));

        //0 + x + x^2
        System.out.println("testing: x^2 + x + 0");
        print_poly(none.add(x.add(xpower2)));

        System.out.println("***********************************************");
        System.out.println("Multiplication Tests.....");
        System.out.println("***********************************************");

        System.out.println("0*(x^2*x)");
        print_poly(none.multiply(neg_xpower2.multiply(x)));
        System.out.println("testing: x^2 * x^2 + x");
        print_poly(xpower2.multiply(x.add(xpower2)));
        System.out.println("testing: -x^2 * x^2 + x");
        print_poly(neg_xpower2.multiply(x.add(xpower2)));
        System.out.println("testing: (x + x^2) * (-x^2 + x)");
        print_poly(x.add(xpower2).multiply(neg_xpower2.add(x)));


        System.out.println("***********************************************");
        System.out.println("Eval + Root test");
        System.out.println("***********************************************");
        // -x^2 when x = 25
        System.out.println(neg_xpower2.evaluate(25));
        // -x^2 when x = -12.5
        System.out.println(neg_xpower2.evaluate(-12.5));
        // Does -x^2 have a root at 0? at 2?
        System.out.println(neg_xpower2.hasRoot(0));
        System.out.println(neg_xpower2.hasRoot(2));

        System.out.println("***********************************************");
        System.out.println("File input/output tests");
        System.out.println("***********************************************");
        
        print_poly(new Polynomial(new File("test.txt"))); // should output [5, -3, 7] [0, 2, 8]
        new Polynomial(new File("test.txt")).saveToFile("write_test.txt"); // should write back the same thing as test.txt but with doubles instead of ints

    }
}