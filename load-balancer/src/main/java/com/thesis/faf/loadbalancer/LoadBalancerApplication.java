package com.thesis.faf.loadbalancer;

import com.thesis.faf.loadbalancer.views.CompanyListWrapper;
import com.thesis.faf.loadbalancer.views.CompanyView;
import com.thesis.faf.loadbalancer.views.CustomerListWrapper;
import com.thesis.faf.loadbalancer.views.CustomerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;


@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableFeignClients
@EnableCaching
public class LoadBalancerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadBalancerApplication.class, args);
	}

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CustomerService customerService;

	@Cacheable(value = "companies")
	@RequestMapping("/api/companies")
	public CompanyListWrapper getList() {
		return companyService.getList();
	}

	@RequestMapping(path = "/api/companies/company/{id}", method = RequestMethod.GET)
	public CompanyView getById(@PathVariable(value = "id") Long id, @QueryParam("fields") String fields) {
		return companyService.getById(id, fields);
	}


	@Cacheable(value = "company-post")
	@RequestMapping(value = "/api/companies", method = RequestMethod.POST)
	public CustomerView createCompany(@Valid @RequestBody CustomerView customerView) {
		return customerService.createCustomer(customerView);
	}

	@Cacheable(value = "customer-list")
	@RequestMapping(value = "/api/customers")
	public CustomerListWrapper getListCustomers() {
		return customerService.getList();
	}

	@RequestMapping(path = "/api/customers/customer/{id}", method = RequestMethod.GET)
	public CustomerView getByIdCustomer(@PathVariable(value = "id") Long id, @QueryParam("fields") String fields) {
		return customerService.getById(id, fields);
	}


	@CacheEvict(value = "customer-list", key = "#result.email")
	@RequestMapping(value = "/api/customers", method = RequestMethod.POST)
	public CustomerView createCustomer(@Valid @RequestBody CustomerView customerView) {
		return customerService.createCustomer(customerView);
	}


	@FeignClient(name = "node")
	interface CompanyService {
		@RequestMapping(value = "/api/companies/company/{id}", method = RequestMethod.GET)
		CompanyView getById(@PathVariable(value = "id") Long id, @QueryParam("fields") String fields);

		@RequestMapping(value = "/api/companies", method = RequestMethod.GET)
		CompanyListWrapper getList();

		@RequestMapping(value = "/api/companies", method = RequestMethod.POST)
		CompanyView createCompany(@Valid @RequestBody CompanyView companyView);
	}


	@FeignClient(name = "node")
	interface CustomerService {
		@RequestMapping(value = "/api/customers/customer/{id}", method = RequestMethod.GET)
		CustomerView getById(@PathVariable(value = "id") Long id, @QueryParam("fields") String fields);

		@RequestMapping(value = "/api/customers", method = RequestMethod.GET)
		CustomerListWrapper getList();

		@RequestMapping(value = "/api/customers", method = RequestMethod.POST)
		CustomerView createCustomer(@Valid @RequestBody CustomerView customerView);
	}
}
