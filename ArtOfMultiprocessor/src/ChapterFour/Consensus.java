package ChapterFour;

public interface Consensus<T> {
	T decide(T value);
}