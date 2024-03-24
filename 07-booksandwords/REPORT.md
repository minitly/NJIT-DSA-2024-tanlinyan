# REPORT OF THE STUDY

#### Important: During testing, I have already written the return value for BookFactory, but I am unable to test HashTable and BST simultaneously, so I have left a comment here so that you can switch between them during testing

```java
package oy.tol.tira.books;

/**
 * <p>
 * Implement the <code>createBook()</code> method to return your instance of the Book interface.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public final class BookFactory {
    private BookFactory() {
    }

    /**
     * @return Your implementation of the Book interface.
     */
    public static Book createBook() {
        // return null;
        //return new BadBookImplementation();
        //return new HashTableBookImplementation();
        return new BSearchTreeBookImplementation();
    }
}

```



## 0.WordCount

This is a custom class I prepared for reading, which rewrites the hashCode method and provides a pointer to the tree structure, so that it can be called by both hashtable and BST.

```java
String word;
    int count;
    WordCount left;
    WordCount right;
    int hash;
    LinkedListImplementation<WordCount> list = null;

    public WordCount(){
        this.word="";
        this.count=0;
        WordCount left=null;
        WordCount right=null;
        hash=hashCode();
    }

    public WordCount(final WordCount wordcount) {
        this.word = new String(wordcount.word);
        this.count = wordcount.count;
        WordCount left=null;
        WordCount right=null;
        hash=hashCode();
    }
    
    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
        WordCount left=null;
        WordCount right=null;
        hash=hashCode();
    }

```



## 1.HashTable

Regarding hashCode(), this is an important part of hashtable. I am based on scaling the elements of a string by multiplying them with a prime number to ensure as much hashing as possible.

```java  @Override
public int hashCode() {
        int hash=0;
        String hashString=word;
        for (int i = 0; i < hashString.length(); i++) {
            hash=37*hash+hashString.charAt(i);
        }
        return hash;
    }
```

As for the conflict, I used the square detection method,+1,+4,+9 each time... until I found a place to insert. When there is not enough space, use a hash to create a larger HashTable. After reading all the elements, I compressed the space and discarded null elements to turn words into an array filled with WordCount. Then, I used quick sorting to sort it and obtain the desired result.

## 2.BST

BST is sorted based on the hash value of each WordCount, making each search more efficient. For the same hash value, conflicts are resolved through a single linked list. After all reads are completed, the binary tree is traversed and each element (i.e. WordCount) is inserted into a temporary array. This allows for quick sorting like a compressed HashTable to obtain the desired results.

One of the core codes is root.insert();

```java
void insert(WordCount wordCount, int toInsertHash) throws RuntimeException {
      if (toInsertHash < this.hash) {
         if (null == left) {
            left = wordCount;
            BSearchTreeBookImplementation.uniqueWordCount++;
         } else {
            left.insert(wordCount, toInsertHash);
         }
      } else if (toInsertHash > this.hash) {
         if (null == right) {
            right = wordCount;
            BSearchTreeBookImplementation.uniqueWordCount++;
         } else {
            right.insert(wordCount, toInsertHash);
         }
      } else { // equal hashes
         if (this.equals(wordCount)) {
            // Key-value pair already in tree, update the value for the key.
            this.count++;
         } else {
            // OPTIONAL different key, same hash, put in the linked list.
            if (null == list) {
               list = new LinkedListImplementation<>();
               list.add(wordCount);
               BSearchTreeBookImplementation.uniqueWordCount++;
            } else {
               WordCount newItem = wordCount;
               int index = list.indexOf(newItem);
               if (index < 0) {
                  list.add(newItem);
                  BSearchTreeBookImplementation.uniqueWordCount++;
               } else {
                  list.get(index).count++;
               }
            }
            if (list.size() > BSearchTreeBookImplementation.maxProbingSteps) {
                BSearchTreeBookImplementation.maxProbingSteps = list.size();
            }
            // END OPTIONAL
         }
      }
   }
```



## 3.Time Complexity 

The algorithms used in the code of this project mainly include the following

1. ### Quick sorting (time complexity of O (n * log (n)))

```java
 //QuickSort
    public static <E extends Comparable<E>> void fastSort(E [] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static <E extends Comparable<E>> void quickSort(E [] array, int begin, int end) {
        if(begin>=end){
            return;
        }
        int pivot=partition(array, begin, end);
        quickSort(array, begin, pivot-1);
        quickSort(array, pivot+1, end);
    }
    private static <E extends Comparable<E>> int partition(E [] array, int begin, int end) {
        E p=array[begin];
        int left=begin;
        int right=end;
        while(left!=right){
            while ((left<right)&&array[right].compareTo(p)>0) {
                right--;
            }
            while ((left<right)&&array[left].compareTo(p)<=0) {
                left++;
            }
            if(left<right){
                swap(array, left, right);
            }
        }
        array[begin]=array[left];
        array[left]=p;
        return left;
    }

```

2. ### Data compression algorithm, using double pointer exchange method, with the worst time complexity of O (n ^ 2) and an average time complexity of O (n)

```java
public static <T> int partitionByRule(T [] pairs,int count,Predicate<T> judgeNullPredicate){
        int left = 0;
        int right = count - 1;

        while (left <= right) {
            while (left <= right && !judgeNullPredicate.test(pairs[left])) {
                left++;
            }

            while (left <= right && judgeNullPredicate.test(pairs[right])) {
                right--;
            }

            if (left < right) {
                swap(pairs, left, right);
                left++;
                right--;
            }
        }
        return left;

    }

 private void Arrayreallocate(int newSize) throws OutOfMemoryError {
        WordCount[] newWords = new WordCount[newSize];
        for (int index = 0; index < newSize; index++) {
            newWords[index] = words[index];
        }
        words = newWords;
    }
```

3. ### The average time complexity for adding elements to the hash table should be O (1), but due to conflicts, it may be slightly larger

   ```java
   private void addToWords(WordCount wordcount) throws OutOfMemoryError {
           // Filter out too short words or words in filter list.
           if (!filter.shouldFilter(wordcount.word) && wordcount.word.length() >= 2) {
               // Checks if the LOAD_FACTOR has been exceeded --> if so, reallocates to a bigger hashtable.
               if (((double)uniqueWordCount * (1.0 + LOAD_FACTOR)) >= words.length) {
                   reallocate((int)((double)(words.length) * (1.0 / LOAD_FACTOR)));
               }
   
               int hash=wordcount.hashCode();
               int index=hash%words.length;
               if(index<0){
                   index+=words.length;
               }
               // if index was taken by different WordCount (collision), get new hash and index,
               int tmpIndex;
               for(int i=0;;i++){
                   tmpIndex=(index+i*i)%words.length;
                   if(words[tmpIndex]==null){
                   // insert into table when the index has a null in it,
                       words[tmpIndex]=wordcount;
                       uniqueWordCount++;
                       break;
                   }else if(words[tmpIndex].word.equals(wordcount.word)){
                       //find the same word,count++
                       words[tmpIndex].count+=1;
                       break;
                   }
                   collisionCount++;
                   if(i>maxProbingSteps){
                       maxProbingSteps=i;
                   }
               }
           } else {
               ignoredWordsTotal++;
           }
       }
   ```

   4. ### The BST element is added using recursive writing, and I cannot estimate its time complexity. The code has already been pasted on it

## 4.Learning process

I don't think it's particularly difficult overall, especially after the teacher provided BadBookImplementation and the sample code for learning hash tables and binary search trees. I haven't made any changes to file operations, and the core functions have already been implemented before. From this course assignment, I have gained a deeper understanding of hash tables and binary search trees, and have also improved my debugging skills. Sometimes when there are errors in testing, I need to debug step by step and pay attention to variable information to find the problem.

Additionally, I noticed that in the correctness test, the final bulk test determined that the time should be 10000ms, but the test file stated 1000ms. (On line 258 of the file)

5.appendix（compare.csv for BST and hash）

![BST](E:\work\moodle\8.Data Structures and Algorithms\Homework-dsa\NJIT-DSA-2024-tanlinyan\07-booksandwords\BST.png)

![Hash](E:\work\moodle\8.Data Structures and Algorithms\Homework-dsa\NJIT-DSA-2024-tanlinyan\07-booksandwords\Hash.png)