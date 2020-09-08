package net.lingala.springdatanativequerypaginationissueh2;

import net.lingala.springdatanativequerypaginationissueh2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Transactional(readOnly = true)
	@Override
	public void run(String... args) {

		printPageResults(0);
		printPageResults(1);

		System.out.println("Done!");

		exit(0);
	}

	private void printPageResults(int pageNumber) {
		System.out.println("***********");
		System.out.println("Results for page: " + pageNumber);
		Page<BigInteger> contractsToBeDeleted = customerRepository.findContractsToBeDeletedNative(new Date(), PageRequest.of(pageNumber, 2));
		contractsToBeDeleted.stream()
				.mapToLong(e -> e.longValue())
				.forEach(System.out::println);
	}

}