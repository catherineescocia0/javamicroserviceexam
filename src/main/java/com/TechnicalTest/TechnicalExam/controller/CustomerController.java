package com.TechnicalTest.TechnicalExam.controller;

import com.TechnicalTest.TechnicalExam.exception.AccountExceptionHandler;
import com.TechnicalTest.TechnicalExam.model.AccountModel;
import com.TechnicalTest.TechnicalExam.model.CustomerModel;
import com.TechnicalTest.TechnicalExam.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/account")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> save(@RequestBody Map<String,String> reqBody) {

        String accountType;

        if (isBlank(reqBody.get("customerName")))        return error(400, "customerName is a required field");
        if (isBlank(reqBody.get("customerMobile")))      return error(400, "customerMobile is a required field");
        if (isBlank(reqBody.get("customerEmail")))       return error(400, "customerEmail is a required field");
        if (!reqBody.get("customerEmail").contains("@")) return error(400, "customerEmail must be a valid email address");
        if (isBlank(reqBody.get("address1")))            return error(400, "address1 is a required field");

        accountType = reqBody.get("accountType");
        if (accountType == null) return error(400, "accountType is a required field");

        String accountTypeLabel;
        if      (accountType.equalsIgnoreCase("S") || accountType.equalsIgnoreCase("Savings"))  accountTypeLabel = "Savings";
        else if (accountType.equalsIgnoreCase("C") || accountType.equalsIgnoreCase("Checking")) accountTypeLabel = "Checking";
        else return error(400, "Invalid accountType: '" + accountType + "'. Accepted values: S (Savings), C (Checking)");

        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerName(reqBody.get("customerName"));
        customerModel.setCustomerMobile(reqBody.get("customerMobile"));
        customerModel.setCustomerEmail(reqBody.get("customerEmail"));
        customerModel.setAddress1(reqBody.get("address1"));
        customerModel.setAddress2(reqBody.get("address2"));

        AccountModel  accountModel = new AccountModel();
        accountModel.setAccountType(accountTypeLabel);
        accountModel.setCustomer( customerModel);
        customerModel.getAccounts().add(accountModel);

        CustomerModel savedCustomerInfo = customerRepository.save(customerModel);

        Map<String, Object> response = new HashMap<>();
        response.put("customerNumber", savedCustomerInfo.getCustomerNumber());
        response.put("transactionStatusCode", 201);
        response.put("transactionStatusDescription", "Customer account created");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

@GetMapping("/{customerNumber}")
public ResponseEntity<Map<String,Object>> getCustomerByCustomerNumber(@PathVariable Long customerNumber){
    CustomerModel customer = customerRepository.findById(customerNumber)
            .orElseThrow(() -> new AccountExceptionHandler("Customer not found"));

    List<Map<String,Object>> savingsAccount = customer.getAccounts().stream()
            .filter(accountModel ->  accountModel.getAccountType().equalsIgnoreCase("Savings"))
            .map(a -> Map.<String, Object>of(
                    "accountNumber", a.getAccountNumber(),
                    "accountType", a.getAccountType(),
                    "availableBalance", a.getAvailableBalance()))
            .toList();
    List<Map<String, Object>> checkingAccounts = customer.getAccounts().stream()
            .filter(a -> a.getAccountType().equals("Checking"))
            .map(a -> Map.<String, Object>of(
                    "accountNumber", a.getAccountNumber(),
                    "accountType", a.getAccountType(),
                    "availableBalance", a.getAvailableBalance()))
            .toList();

    Map<String, Object> response = new HashMap<>();
    response.put("customerNumber", String.valueOf(customer.getCustomerNumber()));
    response.put("customerName", customer.getCustomerName());
    response.put("customerMobile", customer.getCustomerMobile());
    response.put("customerEmail", customer.getCustomerEmail());
    response.put("address1", customer.getAddress1());
    response.put("address2", customer.getAddress2());
    if (!savingsAccount.isEmpty())  response.put("savings", savingsAccount);
    if (!checkingAccounts.isEmpty()) response.put("checking", checkingAccounts);
    response.put("transactionStatusCode", 302);
    response.put("transactionStatusDescription", "Customer Account found");

    return ResponseEntity.status(HttpStatus.FOUND).body(response);

}

@ExceptionHandler(AccountExceptionHandler.class)
public ResponseEntity<Map<String, Object>> handleNotFound(AccountExceptionHandler ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "transactionStatusCode", 401,
                "transactionStatusDescription", ex.getMessage()
        ));
    }

private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
private ResponseEntity<Map<String, Object>> error(int code, String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "transactionStatusCode", code,
                "transactionStatusDescription", message
        ));
    }
}
