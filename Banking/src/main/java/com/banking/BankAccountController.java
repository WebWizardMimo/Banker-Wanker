package com.banking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    // Web page endpoints
    @GetMapping("/")
    public String home(Model model) {
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "index";
    }

    @GetMapping("/create")
    public String createAccountPage() {
        return "create-account";
    }

    @PostMapping("/create")
    public String createAccount(@RequestParam String accNo, 
                               @RequestParam String name, 
                               @RequestParam double balance,
                               RedirectAttributes redirectAttributes) {
        try {
            bankAccountService.createAccount(accNo, name, balance);
            redirectAttributes.addFlashAttribute("success", "Account created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create account. Account number may already exist.");
        }
        return "redirect:/";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String accNo, 
                         @RequestParam double amount,
                         RedirectAttributes redirectAttributes) {
        if (bankAccountService.deposit(accNo, amount)) {
            redirectAttributes.addFlashAttribute("success", "Deposit successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Deposit failed!");
        }
        return "redirect:/";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accNo, 
                          @RequestParam double amount,
                          RedirectAttributes redirectAttributes) {
        if (bankAccountService.withdraw(accNo, amount)) {
            redirectAttributes.addFlashAttribute("success", "Withdrawal successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Withdrawal failed! Insufficient balance.");
        }
        return "redirect:/";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccNo, 
                          @RequestParam String toAccNo, 
                          @RequestParam double amount,
                          RedirectAttributes redirectAttributes) {
        if (bankAccountService.transfer(fromAccNo, toAccNo, amount)) {
            redirectAttributes.addFlashAttribute("success", "Transfer successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Transfer failed! Check account numbers and balance.");
        }
        return "redirect:/";
    }

    // REST API endpoints for external access
    @GetMapping("/api/accounts")
    @ResponseBody
    public List<BankAccount> getAllAccounts() {
        return bankAccountService.getAllAccounts();
    }

    @GetMapping("/api/accounts/{accNo}")
    @ResponseBody
    public BankAccount getAccount(@PathVariable String accNo) {
        return bankAccountService.getAccount(accNo).orElse(null);
    }
}
