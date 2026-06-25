package com.example.store.services;

import com.example.store.entities.Address;
import com.example.store.entities.Product;
import com.example.store.entities.User;
import com.example.store.repositories.*;
import com.example.store.repositories.specifications.ProductSpec;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository ProfileRepository;
    private final EntityManager entityManager;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;

    public void showEntityStates(){
        var user = User.builder()
                .name("John Doe")
                .email("John.doe@example.com")
                .password("password")
                .build();

        if (entityManager.contains(user)){
            System.out.println("Persistent");
        }else System.out.println("Transisent / Detached");

        userRepository.save(user);

        if (entityManager.contains(user)){
            System.out.println("Persistent");
        }else{
            System.out.println("Transisent / Detached");
        }

    }
    @Transactional
        public void showRelatedEntities(){
            var profile = ProfileRepository.findById(2L).orElseThrow();
            System.out.println(profile.getUser().getEmail());
        }

        public void fetchAddress(){
           var address = addressRepository.findById(1L).orElseThrow();
        }

        public void presistRelated(){
            var user = User.builder()
                    .name("John Doe")
                    .email("john.doe@example.com")
                    .password("password")
                    .build();
            var address = Address.builder()
                    .street("street")
                    .city("city")
                    .state("state")
                    .zip("zip")
                    .build();

            user.addAddress(address);

            userRepository.save(user);
        }

        public void deleteRelated(){
        userRepository.deleteById(1L);
        }
        @Transactional
        public void manageProducts(){
        }

        public void updateProductPrices(){
        productRepository.updatePriceByCategory(BigDecimal.valueOf(20), (byte)1);
        }

        @Transactional
        public void fetchProducts(){
            var product = new Product();
            product.setName("product");

            var matcher = ExampleMatcher.matching()
                    .withIncludeNullValues()
                    .withIgnorePaths("id", "description")
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            var example = Example.of(product,matcher);
            var products = productRepository.findAll(example);
            products.forEach(System.out::println);
        }

        public void fetchProductByCriteria(){
            var products = productRepository.findProductsByCriteria("prod", BigDecimal.valueOf(1), null);
            products.forEach(System.out::println);
        }

        public void fetchProductsBySpecification(String name, BigDecimal minPrice, BigDecimal maxPrice){
            Specification<Product> spec = Specification
                    .where(name != null ? ProductSpec.hasName(name) : null)
                    .and(minPrice != null ? ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice) : null)
                    .and(maxPrice != null ? ProductSpec.hasPriceLessThanOrEqualTo(maxPrice) : null);

            productRepository.findAll(spec).forEach(System.out::println);
        }
        public void fetchSortedProducts(){
            var sort= Sort.by("name").and(
                    Sort.by("price").descending()
            );
            productRepository.findAll(sort).forEach(System.out::println);
        }

        public void fetchPaginatedProducts(int pageNumber, int pageSize){
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            Page<Product> page = productRepository.findAll(pageRequest);

            var products = page.getContent();
            products.forEach(System.out::println);

            var totalPages = page.getTotalPages();
            var totalElements = page.getTotalElements();

            System.out.println("Page");
        }
        @Transactional
        public void fetchUsers(){
            var users = userRepository.findAllWithAddresses();
            users.forEach(user -> {
                System.out.println(user);
                user.getAddresses().forEach(System.out::println);
            });
        }

        @Transactional
        public void printLoyalProfile(){
            var users = userRepository.findLoyalUsers(2);
            users.forEach(u->System.out.println(u.getId() + ": " + u.getEmail() ));

        }



}
