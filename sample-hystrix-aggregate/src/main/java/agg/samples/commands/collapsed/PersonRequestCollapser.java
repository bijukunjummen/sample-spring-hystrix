package agg.samples.commands.collapsed;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonRequestCollapser extends HystrixCollapser<List<Person>, Person, Integer> {

    private final Integer id;
    public PersonRequestCollapser(Integer id) {
        super(Setter.
                withCollapserKey(HystrixCollapserKey.Factory.asKey("personRequestCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(2000)));
        this.id = id;
    }

    @Override
    public Integer getRequestArgument() {
        return this.id;
    }

    @Override
    protected HystrixCommand<List<Person>> createCommand(Collection<CollapsedRequest<Person, Integer>> collapsedRequests) {
        List<Integer> ids = collapsedRequests.stream().map(cr -> cr.getArgument()).collect(Collectors.toList());
        return new PersonRequestCommand(ids);
    }

    @Override
    protected void mapResponseToRequests(List<Person> batchResponse, Collection<CollapsedRequest<Person, Integer>> collapsedRequests) {
        Map<Integer, Person> personMap = batchResponse.stream().collect(Collectors.toMap(Person::getId, Function.identity()));

        for (CollapsedRequest<Person, Integer> cr: collapsedRequests) {
            cr.setResponse(personMap.get(cr.getArgument()));
        }
    }
}
