package i604util;


public class List<T extends Comparable<T>>
{
   Node theList ;
   int  len = 0;

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

   public List ()
   {
      theList = null ;

      return ;
   }

   public boolean isEmpty () { return null == theList ; }

   public void insert (T pData)
   {
      // let's keep them sorted
      Node curr = theList
         , prev = null
         ;

      // find the insertion point
      while (null != curr && curr.data.compareTo(pData) < 0) {
         prev = curr      ;
         curr = curr.next ;
         }

      if (null == prev) {
         // insertion at head of the list
         theList = new Node(pData,theList) ;
         len ++;
         }
      else {
    	 // key part
         prev.next = new Node(pData,curr) ;
         len ++;
         }

      return ;
   }

   public int delete (T pData)
   {
      int retVal = 0 ;  // assume no deletion

      // let's keep them sorted
      Node curr = theList
         , prev = null
         ;

      // find the "insertion" point
      while (null != curr && curr.data.compareTo(pData) < 0) {
         prev = curr      ;
         curr = curr.next ;
         }

      if (null != curr && 0 == curr.data.compareTo(pData)) {
         // found it - delete
         if (null == prev) {
            // deletion from head of the list
            theList = theList.next ;
            len--;
            }
         else {
        	// key part
            prev.next = curr.next ;
            len --;
            }

         retVal++ ;
         }

      return retVal ;
   }
   
   // resort the list, used by the workAmount change
   public void resort(){
	   Node curr;
	   
	   for(int i = 0; i < len; i++){
		    curr = theList ;
		   
		   while(curr.next != null){
          if(curr.data.compareTo(curr.next.data) > 0){
        	  // switch the data
        	       T   temp2 = curr.data;
        	       curr.data = curr.next.data;
        	  curr.next.data = temp2    ;
          }
          curr = curr.next; 
	    }
		   
	   }  
   }

   public int deleteAll (T pData)
   {
	   int retVal = 0 ;

	   // inefficient, but works
	   while (0 != delete(pData)) retVal++ ;
       // the number of same element being deleted
	   return retVal ;
   }

   public void clear ()
   {
      while (null != theList)
         theList = theList.next ;

      return ;
   }

   public T find (T pData)
   {
      T    retVal = null    ;
      Node curr   = theList ;

      // find the "insertion" point
      while (null != curr && curr.data.compareTo(pData) < 0) {
         curr = curr.next ;
         }

      if (null != curr && 0 == curr.data.compareTo(pData)) {
         // found it - delete
         retVal = curr.data ;
         }

      return retVal ;
   }
   
   public T getAt(int n)
   {
	      T    retVal = null    ;
	      Node curr   = theList ;
	      for(int i = 0 ; i <= n ; i++){
	    	  if(null != curr) retVal = curr.data ;
	    	  curr = curr.next ;
	      }
	      
	      return retVal;
   }
   
   public int length(){
	   return len;
   }

   @Override
   public String toString ()
   {
      StringBuilder sb = new StringBuilder() ;

      sb.append("theList: ") ;

      if (null == theList) {
         sb.append("<<null>>") ;
      }
      else {
         for (Node n = theList ; null != n ; n = n.next) {
            sb.append(n.data + " ") ;
         }
      }

   return sb.toString() ;
   }
}

