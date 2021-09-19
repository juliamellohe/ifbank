package ifbank.auth;

import ifbank.User.User;
import ifbank.User.UserDao;
import spark.Route;

public class authController {

    public static Route validar = (req, res) -> {

        String email = req.queryParams("email");
        String password = req.queryParams("password");

        UserDao userDao = new UserDao();

        try {

            User user = userDao.getByEmailAndPass(email, password);
            
            if (user == null) {
                throw new Exception();
            }

            req.session(true);
            req.session().attribute("name", user.getName());
            req.session().attribute("email", user.getEmail());
            req.session().attribute("user_id", user.getId());
            req.session().attribute("data_cadastro", user.getData_cadastro());
            req.session().attribute("password", user.getPassword());
            req.session().attribute("login", true);

            res.redirect("/userHome");
            return "";

        } catch (Exception e) {
            return "Email e senha não batem";
        }

    };
}
