public class Test {

    public static void main(String[] args) {
        User tester = new User(7,"Developer");
        System.out.println(tester.getId());
        System.out.println(tester.getVacancy());
    }

    static class User {

        private long id;
        private Vacancy vacancy;

        User(long id, String vacancy){
            this.id = id;
            this.vacancy = new Vacancy(vacancy);
        }

        String getVacancy() {
            return vacancy.getVacancy();
        }

        long getId() {
            return id;
        }
    }

    static class Vacancy {
        String vacancy;

        Vacancy(String vacancy){
            this.vacancy = vacancy;
        }

        String getVacancy() {
            return vacancy;
        }
    }

}
