package agg.samples.commands.collapsed;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PersonRequestCommand extends HystrixCommand<List<Person>>{

    private final List<Integer> ids;
    private final PersonService personService = new PersonService();
    private static final Logger logger = LoggerFactory.getLogger(PersonRequestCommand.class);

    public PersonRequestCommand(List<Integer> ids) {
        super(HystrixCommandGroupKey.Factory.asKey("default"));
        this.ids = ids;
    }

    @Override
    protected List<Person> run() throws Exception {
        logger.info("Retrieving details for : " + this.ids);
        return personService.findPeople(this.ids);
    }
}
