import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class TestCalc {

    @TestFactory
    Stream<DynamicTest> testCalculating_Should_Work_Correctly(){
        List<String> variables = Arrays.asList("2+(4-1+2)*5+(6.0*3+8/2)+5",
                "(2+(4-1+2)*5+(6.0*3+8/2)+5)",
                "6",
                "6+2*2",
                "(2+2*3)+5*(4+2*3-15/3)-10");

        List<Float> expectedResult = Arrays.asList(54.0f, 54.0f, 6.0f, 10.0f, 23.0f);

        return variables.stream()
                .map( el -> DynamicTest.dynamicTest(
                        "Calculate expression: " + el,
                        ()->{
                            //Given
                            Calculator calculator = new Calculator();
                            float result;

                            //When
                            result = calculator.calculate(el);

                            //Then
                            assertTrue(expectedResult.get(variables.indexOf(el)) == result);
                        }
                ));
    }
}
