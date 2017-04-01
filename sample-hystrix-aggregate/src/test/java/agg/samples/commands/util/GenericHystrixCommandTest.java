package agg.samples.commands.util;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.observers.TestSubscriber;

import static agg.samples.commands.util.GenericHystrixCommand.execute;
import static agg.samples.commands.util.GenericHystrixCommand.executeObservable;
import static org.assertj.core.api.Assertions.assertThat;

public class GenericHystrixCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static final Logger logger = LoggerFactory.getLogger(GenericHystrixCommandTest.class);

    @Test
    public void testGenericHystrixCommand() {
        String result = GenericHystrixCommand.execute("mycommandgroup", "mycommand", () -> {
            System.out.println("do something");
            return "Hello";
        }, (t) -> {
            return "fallback";
        });

        assertThat(result).isEqualTo("Hello");
    }

    @Test
    public void testGenericHystrixCommandWithException() {
        String result = GenericHystrixCommand.execute("mycommandgroup", "mycommand", () -> {
            throw new RuntimeException("An Exception...");
        }, (t) -> {
            System.out.println("t = " + t);
            return "fallback";
        });

        assertThat(result).isEqualTo("fallback");
    }

    @Test
    public void testWithHystrixBadRequestException() {
        expectedException.expect(CustomBusinessException.class);
        GenericHystrixCommand.execute("mycommandgroup", "mycommand", () -> {
            throw new HystrixBadRequestException("A bad exception");
        }, (t) -> {
            return "fallback";
        });
    }

    @Test
    public void testWithObservable() {
        Observable<String> res = GenericHystrixCommand.executeObservable("mycommandgroup", "mycommand", () -> {
            return "Hello";
        }, (t) -> {
            System.out.println("t = " + t);
            return "fallback";
        });

        TestSubscriber testSubscriber = TestSubscriber.create();
        res.subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertValue("Hello");

    }
    
    @Test
    public void testAggregateResults() {
        String  r1 = execute("remote1", "remote1", () -> remoteCall1());
        Integer r2 = execute("remote2", "remote2", () -> remoteCall2());
        
        String aggregated = r1 + r2;
        assertThat(aggregated).isEqualTo("result1");
    }

    @Test
    public void testAggregateResultsObservable() {
        Observable<String>  r1Obs = executeObservable("remote1", "remote1", () -> remoteCall1());
        Observable<Integer> r2Obs = executeObservable("remote2", "remote2", () -> remoteCall2());

        String aggregated = Observable.zip(r1Obs, r2Obs, (r1, r2) -> (r1 + r2)).toBlocking().single();
        
        assertThat(aggregated).isEqualTo("result1");
    }

    @Test
    public void testAggregateResultsObservableWithFallback() {
        Observable<String> r1Obs = executeObservable("remote1", "remote1",
                () -> {
                    throw new RuntimeException("!!");
                },
                (t) -> {
                    logger.error(t.getMessage(), t);
                    return "fallback";
                });
        Observable<Integer> r2Obs = executeObservable("remote2", "remote2",
                () -> {
                    throw new RuntimeException("!!");
                },
                (t) -> {
                    logger.error(t.getMessage(), t);
                    return 0;
                });

        String aggregated = Observable.zip(r1Obs, r2Obs, (r1, r2) -> (r1 + r2)).toBlocking().single();

        assertThat(aggregated).isEqualTo("fallback0");
    }

    @Test
    public void testAggregateWithObservableNoFallback() {
        Observable<String> r1Obs = executeObservable("remote1", "remote1",
                () -> {
                    throw new RuntimeException("!!");
                });
        Observable<Integer> r2Obs = executeObservable("remote2", "remote2",
                () -> {
                    throw new RuntimeException("!!");
                });

        expectedException.expect(CustomSystemException.class);
        String aggregated = Observable.zip(r1Obs, r2Obs, (r1, r2) -> (r1 + r2)).toBlocking().single();

        assertThat(aggregated).isEqualTo("fallback0");
    }    



    private String remoteCall1() {
        DelayUtil.addDelay(300);
        return "result";
    }

    private Integer remoteCall2() {
        DelayUtil.addDelay(300);
        return 1;
    }

}
