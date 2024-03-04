package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E>{
    private Object [] itemArray;
    private int capacity;
    private int currentSize = 0;
    private int head = 0;
    private int tail = -1;
    private static final int DEFAULT_STACK_SIZE = 10;

    /**
     * Allocates a queue with a default capacity.
     * @throws QueueAllocationException
     */
    public QueueImplementation() throws QueueAllocationException {
        capacity=DEFAULT_STACK_SIZE;
        itemArray=new Object[DEFAULT_STACK_SIZE];
     }

    /** 
     * @param capacity The capacity of the queue.
     * @throws QueueAllocationException If cannot allocate room for the internal array.
     */
    public QueueImplementation(int capacity) throws QueueAllocationException {
        if(capacity<2){ 
            throw new QueueAllocationException("Capacity is too small!");
         }
         this.capacity=capacity;
         itemArray=new Object[capacity];
     }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {
        if(currentSize==capacity){
            Object[] tmp=new Object[this.capacity*2+1];
            int indexOfItemArray=head;
            int indexOfTmp=0;
            int loopTime=currentSize;
            while(loopTime-->0){
                tmp[indexOfTmp++]=itemArray[indexOfItemArray];
                indexOfItemArray=(indexOfItemArray+1)%capacity;
            }
            head=0;
            tail=indexOfTmp-1;
            itemArray=tmp;
            tmp=null;
            capacity=capacity*2+1;
         }
        if(element==null){
           throw new NullPointerException();
        }
        tail=(tail+1)%capacity;
        itemArray[tail]=element;
        currentSize++;        
    }


    @Override
    public E dequeue() throws QueueIsEmptyException {
        E returnE =element();
        head=(head+1)%capacity;
        currentSize--;
        return returnE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E element() throws QueueIsEmptyException {
        if(isEmpty()){
            throw new QueueIsEmptyException("Queue is empty");
        }
        return (E)itemArray[head];
    }

    @Override
    public int size() {
       return currentSize;
    }
 
    @Override
    public boolean isEmpty() {
       return currentSize==0;
    }
  
    @Override
    public void clear() {
       head=0;
       tail=-1;
       currentSize=0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        int indexOfItemArray=head;
        int loopTime=currentSize;
        while(loopTime-->0){
            builder.append(itemArray[indexOfItemArray].toString());
            indexOfItemArray=(indexOfItemArray+1)%capacity;
            if(loopTime!=0){
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
