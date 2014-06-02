package lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LambdaTest {
    @Test
    public void test_lamba_with_typed_parameters() throws Exception {
        Comparator<Long> longComparator = (Long first, Long second) -> Long.compare(first, second);
        assertThat(longComparator.compare(1L, 2L), lessThan(0));
    }

    @Test
    public void test_lambda_with_implicit_type_paramter() throws Exception {
        Comparator<Long> longComparator = (first, second) -> Long.compare(first, second);
        assertThat(longComparator.compare(1L, 2L), lessThan(0));
    }

    @Test
    public void test_lambda_with_single_parameter() throws Exception {
        Predicate<String> predicate = t -> t.length() > 10;
        assertThat(predicate.test("123"), equalTo(false));
    }

    @Test
    public void test_lambda_with_no_params() throws Exception {
        Callable<String> callable = () -> "noparam";
        assertThat(callable.call(), is("noparam"));
    }

    @Test
    public void test_lambda_without_value() throws Exception {
        Runnable runnable = () -> System.out.println("no return");
        runnable.run();
    }

    @Test
    public void test_lambda_with_code_block() throws Exception {
        Operator<Integer> plusSquareOp = (op1, op2) -> {
            int result = op1 + op2;
            return result * result;
        };
        assertThat(plusSquareOp.operate(1, 2), equalTo(9));
    }

    @Test
    public void test_lambda_using_static_method_reference() throws Exception {
        assertThat(operate(LambdaTest::squrePlus, 2, 3), equalTo(13));

        Operator<Double> pow = Math::pow;
        assertThat(pow.operate(2.0, 3.0), equalTo(8.0));
    }

    public static int squrePlus(Integer op1, Integer op2) {
        return op1 * op1 + op2 * op2;
    }

    @Test
    public void test_lambda_using_object_method_reference() throws Exception {
        LambdaTest lambdaTest = new LambdaTest();
        assertThat(operate(lambdaTest::doublePlus, 2, 3), equalTo(10));
    }

    private int doublePlus(int op1, int op2) {
        return 2 * (op1 + op2);
    }

    @Test
    public void test_lambda_using_param_method_reference() throws Exception {
        BiFunction<Long, Long, Integer> longOp = Long::compareTo;
        assertThat(run(longOp, 10L, 20L), equalTo(new Long(10L).compareTo(20L)));
    }

    private Integer run(BiFunction<Long, Long, Integer> longOp, Long op1, Long op2) {
        return longOp.apply(op1, op2);
    }

    private int operate(Operator<Integer> operator, Integer op1, Integer op2) {
        return operator.operate(op1, op2);
    }

    @FunctionalInterface
    public interface Operator<T> {
        public T operate(T op1, T op2);

        default public int twoHash() {
            return 1;
        }
    }
}
