import com.cab404.chumroll.ConstructingPool;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created at 16:53 on 14/03/17
 *
 * @author cab404
 */
@RunWith(JUnit4.class)
public class PoolTests {

    public static class A {
        int val = 1;
    }

    public static class B extends A {
        {
            val = 2;
        }
    }

    public static class C extends B {
        {
            val = 3;
        }
    }

    public static class D extends C {
        {
            val = 4;
        }
    }

    @Test
    public void testConverterPool() {
        final ConstructingPool<A> pool = new ConstructingPool<A>() {
            @Override
            protected A makeInstance(Class clazz) {
                return null;
            }
        };

        for (A object : new A[]{new A(), new B(), new C(), new D()})
            pool.enforceInstance(object);

        Assert.assertEquals(pool.getInstance(A.class).val, 1);
        Assert.assertEquals(pool.getInstance(B.class).val, 2);
        Assert.assertEquals(pool.getInstance(C.class).val, 3);
        Assert.assertEquals(pool.getInstance(D.class).val, 4);

    }

}
