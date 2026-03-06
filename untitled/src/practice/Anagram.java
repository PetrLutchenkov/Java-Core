package practice;

import java.util.Arrays;

public class Anagram {
//    boolean isAnagram(String s1, String s2){
        static boolean isAnagram(String s1, String s2){
        char[] arr1 = s1.toCharArray();
        Arrays.sort(arr1);
        char[] arr2 = s2.toCharArray();
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

    static void main() {
        System.out.println(Anagram.isAnagram("asb", "bas"));
//        System.out.println(new practice.Anagram().isAnagram("asb", "bas"));
    }
}
