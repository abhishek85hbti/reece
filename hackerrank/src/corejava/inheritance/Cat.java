package corejava.inheritance;

public class Cat extends Animal{
    public static void testClassMethod() {
        System.out.println("The static method in Cat");
    }
    public void testInstanceMethod() {
        System.out.println("The instance method in Cat");
    }

    public static void main(String[] args) {
        Cat myCat = new Cat();
        myCat.testInstanceMethod();
        Cat.testClassMethod();
        Cat.testClassMethod();
        
        Animal myAnimal = myCat;
        myAnimal.testClassMethod();
        myAnimal.testClassMethod();
        myAnimal.testInstanceMethod();
    }
}
