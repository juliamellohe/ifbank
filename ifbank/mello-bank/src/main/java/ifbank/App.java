package ifbank;

import static spark.Spark.*;

import ifbank.Registro.RegistroController;
import ifbank.User.UserController;
import ifbank.auth.authController;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class App 
{
    public static void main( String[] args )
    {
        int port = 4567; //padrao
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) { //verifica qual é a variavel que heroku configurou como porta
            port = Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        port(port);

        staticFiles.location("/public");

        // LISTAR USERS
        get("/usersList", (req, res) -> UserController.list(req, res), new HandlebarsTemplateEngine());
        
        //VALIDAÇÃO DE LOGIN
        post("/logar", authController.validar);

        //USUÁRIO CADASTRADO
        post("/newUser", (req, res) -> UserController.create(req, res), new HandlebarsTemplateEngine());
        
        //PAGINA DO USUARIO
        get("/userHome", (req, res) -> RegistroController.list(req, res), new HandlebarsTemplateEngine());
        
        //PAGINA DO USUARIO - REGISTROS
        get("/registros/:id/details", (req, res) -> RegistroController.detail(req, res), new HandlebarsTemplateEngine());
        post("/registros/:id/edit", RegistroController.edit);
        get("/registros/:id/delete", RegistroController.delete);
        
        //PAGINA DO USUARIO - FILTRAR BUSCA
        post("/filtroTipo", (req, res) -> RegistroController.listTipo(req, res), new HandlebarsTemplateEngine());
        post("/filtroData", (req, res) -> RegistroController.listDate(req, res), new HandlebarsTemplateEngine());


        //EXEMPLO DE VALIDAÇÃO DE LOGIN NECESSÁRIA PARA ENTRAR EM UMA ROTA
        get("/userPage", (req, res) -> {
            //null associável a Boolean
            Boolean login = req.session().attribute("login");

            if (login != null && login == true)
                return "Logado";

            res.header("Location", "/"); //header, value
            res.redirect("/");
            return null;
        });

        // FAZER LOGOUT
        get("/logout", (req, res) -> {
            req.session().invalidate();
            res.redirect("/");
            return null;
        });

        //ADICIONAR REGISTRO SOMENTE SE ESTIVER COM LOGIN VÁLIDO
        post("/adicionarRegistro", (req, res) -> {

            Boolean login = req.session().attribute("login");

            if (login == null || login == false) {
                res.redirect("/"); // caso o login nao esteja ativo o usuario retorna a rota da página inicial
                return "";
            }
            ModelAndView retorno = RegistroController.create(req, res); // a intenção era retornar o model and view
            res.redirect("/userHome");
            return "";


        });

    }
}
