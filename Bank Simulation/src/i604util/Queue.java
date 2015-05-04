package i604util;

public class Queue<T>
{
   Node theQueue ;

   class Node
   {
      private T    data ;
      private Node next ;

      Node () { this(null,null) ; }

      Node (T pData, Node pNext)
      {
         data = pData ;
         next = pNext ;

         return ;
      }

      T    getData () { return data ; }
      Node getNext () { return next ; }

      void setData (T    val ) { data = val  ; return ; }
      void setNext (Node node) { next = node ; return ; }
   }

   public Queue ()
   {
      theQueue = null ;

      return ;
   }

   public boolean isEmpty () { return null == theQueue ; }

   public void enqueue (T pData)
   {
      Node curr = theQueue
         , prev = null
         ;

      // find the end of the list
      while (null != curr) {
         prev = curr      ;
         curr = curr.next ;
         }

      if (null == prev) {
         theQueue = new Node(pData,theQueue) ;
         }
      else {
         prev.next = new Node(pData,curr) ;
         }

      return ;
   }

   public T dequeue ()
   {
      T retVal = null ;  // empty queue

      if (null != theQueue) {
         retVal = theQueue.data ;

         theQueue = theQueue.next ;
         }

      return retVal ;
   }

   @Override
   public String toString ()
   {
      StringBuilder sb = new StringBuilder() ;

      sb.append("theQueue: ") ;

      if (null == theQueue) {
         sb.append("<<null>>") ;
      }
      else {
         for (Node n = theQueue ; null != n ; n = n.next) {
            sb.append(n.data + " ") ;
         }
      }

   return sb.toString() ;
   }
}


