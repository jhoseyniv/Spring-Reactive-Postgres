package reactive140110.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapAndFlatMap {

    public static void main(String[] args){
        //create stream from integers
        Stream<Integer> numbers = Stream.of(1,2,-3,4,5,6);
        Stream<Integer> numbers2 = Stream.of(1,2,-3,4,5,6);
        //map each integer to negative and multyply 3
        Stream<Integer> results =  numbers.map(number -> number * -3);
        Stream<String> results2 =  numbers2.map(number -> number.toString() + " number");
         results.forEach(System.out::println);
         results2.forEach(System.out::println);

         String myWord = "this is a test!";
         String[] splits =  myWord.split("");
         Stream<String> myStream = Stream.of(splits);
         myStream.forEach(System.out::print);


        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(4,5,6);
        List<Integer> list3 = Arrays.asList(7,8,9);
        List<List<Integer>> lists = Arrays.asList(list1,list2,list3);

        List<Integer> allIntegers = lists.stream().flatMap( n -> n.stream()).collect(Collectors.toList());

        allIntegers.forEach( b -> System.out.println("this " + b ) );

    }
}
