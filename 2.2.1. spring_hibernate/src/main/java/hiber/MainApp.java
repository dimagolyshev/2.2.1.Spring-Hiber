package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

public class MainApp {
    public static void main(String[] args) throws SQLException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);

        // Add users and cars
        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        carService.add(new Car("Fiat", 500));
        carService.add(new Car("Volkswagen Golf", 8));
        carService.add(new Car("BMW X", 5));
        carService.add(new Car("Kia Rio", 4));

        // Get lists and link cars
        List<User> users = userService.listUsers();
        List<Car> cars = carService.listCars();

        if (users.size() == cars.size()) {
            IntStream.range(0, users.size())
                    .forEach(i -> userService.addCar(users.get(i), cars.get(i)));
        } else {
            System.out.println("What a shame, not enough users or cars");
        }

        // Check
        List<User> usersToCheck = userService.listUsers();
        for (User user : usersToCheck) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Model = " + user.getCar().getModel());
                System.out.println("Series = " + user.getCar().getSeries());
            }
            System.out.println();
        }

        context.close();
    }
}
