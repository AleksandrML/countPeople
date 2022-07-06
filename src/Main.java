import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // count quantity of people before 18 age:
        System.out.println("Число несовершеннолетних: " + persons.stream().filter(x -> x.getAge() < 18).count());

        // get armyGuys and print first 3 persons (we can use long filter instead of filter sets,
        // because the condition is quite simple and small anyway, may be read and understood easily):
        List<Person> armyGuys = persons.stream().
                filter(x -> x.getAge() >= 18 && x.getAge() < 27 && x.getSex().equals(Sex.MAN)).toList();
        System.out.println(armyGuys.subList(0, 3));

        // get working persons and print first 3 persons, try to atom filter to smaller ones because it is big one and
        // one will not be able to read it very well if we use one filter:
        List<Person> workingGuys = persons.stream().
                filter(x -> x.getEducation().equals(Education.HIGHER)).
                filter(x -> x.getAge() >= 18).
                filter( x -> (x.getAge() < 65 && x.getSex().equals(Sex.MAN)) ||
                        (x.getAge() < 60 && x.getSex().equals(Sex.WOMAN))).
                sorted(Comparator.comparing(Person::getFamily)).toList();
        System.out.println(workingGuys.subList(0, 3));
    }
}
