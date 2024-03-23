package oy.tol.tira.books;

public class WordCount implements Comparable<WordCount> {
    String word;
    int count;

    public WordCount(){
        this.word="";
        this.count=0;
    }

    public WordCount(final WordCount wordcount) {
        this.word = new String(wordcount.word);
        this.count = wordcount.count;
    }
    
    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word){
        this.word=word;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count){
        this.count=count;
    }

    @Override
    public String toString() {
        return "word="+word+" count="+count;
    }

    @Override
    public int hashCode() {
        int hash=0;
        String hashString=word;
        for (int i = 0; i < hashString.length(); i++) {
            hash=37*hash+hashString.charAt(i);
        }
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof WordCount) {
            return this.word.equals(((WordCount)other).word);
        }
        return false;
    }

    @Override
    public int compareTo(WordCount other) {
        return (other.count)-count;
    }
}
