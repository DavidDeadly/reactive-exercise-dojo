package co.david.dojo;

import reactor.core.publisher.Flux;

import java.util.List;

public class DojoReactiveExerciseApplication {

	public static void main(String[] args) {
//		String input = "Level|Cat|Mr Owl ate my metal worm|Do geese see God|Spaces are usually ignored|Madam Im Adam|Noon|Definition|Kayak";
		String input = "Never odd or even|Live not on evil|For Word Nerds Everywhere|We especially love some of the wacky\n";

		Flux<String> words = getWords(input);
		Flux<String> phrases = getPhrases(input);

		words.count()
			.subscribe(num -> System.out.println("Total words:" + num));

		phrases.count()
			.subscribe(num -> System.out.println("Total phrases:" + num));

		getPalindromes(words)
						.collectList()
						.subscribe(w -> System.out.println("Palindromic words: " + w ));

		getPalindromes(phrases)
						.collectList()
						.subscribe(ph -> System.out.println("Palindromic phrases: " + ph));
	}

	public static Flux<String> getWords(String str) {
		return Flux.fromIterable(List.of(str.split("\\|")))
						.filter(s -> s.split(" ").length == 1);
	}

	public static Flux<String> getPhrases(String str) {
		return Flux.fromIterable(List.of(str.split("\\|")))
						.filter(s -> s.split(" ").length > 1);
	}

	public static Flux<String> getPalindromes(Flux<String> strs) {
		return strs
						.filter(DojoReactiveExerciseApplication::isPalindrome)
						.map(DojoReactiveExerciseApplication::quitSpaces)
						.map(DojoReactiveExerciseApplication::reverse);
	}

	public static boolean isPalindrome(String str) {
		String phrase = quitSpaces(str);
		return phrase.equalsIgnoreCase(reverse(phrase));
	}

	public static String quitSpaces (String str) {
		return str.replace(" ", "");
	}

	public static String reverse(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		return stringBuilder
						.append(str)
						.reverse()
						.toString();
	}
}
