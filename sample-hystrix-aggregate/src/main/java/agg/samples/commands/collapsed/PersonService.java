package agg.samples.commands.collapsed;


import java.util.List;
import java.util.stream.Collectors;

public class PersonService {

    public Person findPerson(Integer id) {
        return new Person(id, "name : " + id);
    }

    public List<Person> findPeople(List<Integer> ids) {
        return ids
                .stream()
                .map(i -> new Person(i, "name : " + i))
                .collect(Collectors.toList());
    }
}
