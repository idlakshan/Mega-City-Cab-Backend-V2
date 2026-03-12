package lk.icbt.megacity.seeder;

import lk.icbt.megacity.entity.Category;
import lk.icbt.megacity.entity.Role;
import lk.icbt.megacity.entity.User;
import lk.icbt.megacity.repo.CategoryRepo;
import lk.icbt.megacity.repo.RoleRepo;
import lk.icbt.megacity.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DatabaseSeeder implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        seedRoles();
        seedAdminUser();
        seedCategories();
    }

    // ---------------- ROLES ----------------
    private void seedRoles() {

        if (roleRepo.count() == 0) {

            Role admin = Role.builder()
                    .name("ADMIN")
                    .build();

            Role customer = Role.builder()
                    .name("CUSTOMER")
                    .build();

            roleRepo.save(admin);
            roleRepo.save(customer);

            System.out.println("Roles seeded");
        }
    }

    // ---------------- ADMIN USER ----------------
    private void seedAdminUser() {
        if (userRepo.count() == 0) {

            Role adminRole = roleRepo.findByName("ADMIN").orElseThrow();

            User admin = User.builder()
                    .name("Dimuthu")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("123"))
                    .roles(Set.of(adminRole))
                    .nic("998987676V")
                    .phone("0787676789")
                    .build();

            userRepo.save(admin);

            System.out.println("Admin user seeded");
        }
    }

    // ---------------- CATEGORIES ----------------
    private void seedCategories() {

        if (categoryRepo.count() == 0) {

            Category c1 = Category.builder()
                    .name("Budget")
                    .icon("mini.png")
                    .title("Budget")
                    .features("[\"Flexible price\",\"Air Condition\",\"2 Passengers\",\"Small Luggage\"]")
                    .price(50.00)
                    .build();

            Category c2 = Category.builder()
                    .name("City")
                    .icon("miniVan.png")
                    .title("City")
                    .features("[\"Comfortable\",\"Air Condition\",\"4 Passengers\",\"Medium Luggage\"]")
                    .price(80.00)
                    .build();

            Category c3 = Category.builder()
                    .name("Semi")
                    .icon("economy.png")
                    .title("Semi")
                    .features("[\"Spacious\",\"Air Condition\",\"5 Passengers\",\"Large Luggage\"]")
                    .price(120.00)
                    .build();

            Category c4 = Category.builder()
                    .name("Luxury")
                    .icon("Luxury.png")
                    .title("Luxury")
                    .features("[\"Luxury\",\"Air Condition\",\"4 Passengers\",\"Extra Luggage\",\"Music System\"]")
                    .price(150.00)
                    .build();

            Category c5 = Category.builder()
                    .name("Van")
                    .icon("van.png")
                    .title("Van")
                    .features("[\"Extra Spacious\",\"Air Condition\",\"6 Passengers\",\"Extra Luggage\"]")
                    .price(180.00)
                    .build();

            categoryRepo.save(c1);
            categoryRepo.save(c2);
            categoryRepo.save(c3);
            categoryRepo.save(c4);
            categoryRepo.save(c5);

            System.out.println("Categories seeded");
        }
    }
}