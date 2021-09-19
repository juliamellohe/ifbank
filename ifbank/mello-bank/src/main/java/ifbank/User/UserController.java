package ifbank.User;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UserController {
    
    public static ModelAndView list(Request req, Response res) {

        UserDao userDao = new UserDao();
        
        Map<String, Object> model = new HashMap<>();
        model.put("users", userDao.getUsers()); // getUsers() retorna uma lista 
        return new ModelAndView(model,"listUsers.hbs");
    }

    public static ModelAndView create(Request req, Response res) { // tem que retornar uma rota

        String name = req.queryParams("name");
        String email = req.queryParams("email");
        String password = req.queryParams("password");

        User user = new User(name, email, password);

        UserDao userDao = new UserDao();
        userDao.add(user);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);

        return new ModelAndView(model, "newUser.hbs");

    };

}
