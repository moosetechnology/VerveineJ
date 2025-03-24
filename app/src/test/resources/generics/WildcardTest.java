package generics;

import java.util.List;

@SuppressWarnings("unused")
public class WildcardTest{
	
    private static double sumListElementsWildcard(List<? extends Number> list)
    {
        double sum = 0.0;
        for (Number i : list) {
            sum += i.doubleValue();
        }
 
        return sum;
    }
	private static double sumListElementsWildcardLowerBounded(List<? super Number> list)
    {
        double sum = 0.0;
        return sum;
    }
}