package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("hungnoten@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("erick", "test");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "/admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("email", id);
        System.out.println(id);
        model.addAttribute("user", user);
        return "/admin/user/detail";
    }

    @RequestMapping(value = "/admin/user/create")
    public String createUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String saveUserPage(Model model, @ModelAttribute("newUser") User hoiTao) {
        System.out.println("run here " + hoiTao);
        this.userService.handleSaveUser(hoiTao);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public String updateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("id", id);
        model.addAttribute("newUser", currentUser);
        return "/admin/user/update";
    }

    @PostMapping(value = "/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User hoiTao) {
        User currentUser = this.userService.getUserById(hoiTao.getId());
        if (currentUser != null) {
            currentUser.setAddress(hoiTao.getAddress());
            currentUser.setFullName(hoiTao.getFullName());
            currentUser.setPhone(hoiTao.getPhone());
        }
        this.userService.handleSaveUser(currentUser);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/delete/{id}")
    public String deleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User());
        return "/admin/user/delete";
    }

    @PostMapping(value = "/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User hoiTao) {
        this.userService.deleteById(hoiTao.getId());
        return "redirect:/admin/user";
    }
}
