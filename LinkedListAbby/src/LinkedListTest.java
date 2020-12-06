public class LinkedListTest {
    public static void main(String[] args) {
        LinkedList myList = new LinkedList();
        myList.insertFirst(0, 100);
        myList.insertFirst(1, 101);
        myList.insertFirst(2, 102);
        myList.insertFirst(3, 103);
        myList.insertFirst(4, 104);
        myList.insertFirst(5, 105);
        myList.displayList("Исходный ");
        myList.invertList();
        myList.displayList("Инвертированный ");
    }

//    public static LinkedList invertList2(LinkedList list) {
//        LinkedList invertedList = new LinkedList();
//
//        Link current = list.first;
//        while (current != null) {
//            invertedList.insertFirst(current.key, current.Data);
//            current = current.next;
//        }
//        return invertedList;
//    }


}

class Link {
    public int key;
    public double Data;
    public Link next;

    public Link(int i, double d) {
        key = i;
        Data = d;
    }

    public void displayLink() {
        System.out.print("{" + key + ", " + Data + "} ");
    }
}

class LinkedList {

    public Link first;

    public LinkedList() {
        first = null;
    }

    public void insertFirst(int i, double d) {
        Link newLink = new Link(i, d);
        newLink.next = first;
        first = newLink;
    }

    public void displayList(String message) {
        System.out.print(message + "список от первого к последнему: ");
        Link current = first;
        while (current != null) {
            current.displayLink();
            current = current.next;
        }
        System.out.println("\n");
    }

    public void invertList(){
        Link current = first;
        Link a;
        Link b = first.next;
        first.next = null;
        while (b != null){
            a = current;
            current = b;
            b = current.next;
            current.next = a;
        }
        first = current;
    }



}